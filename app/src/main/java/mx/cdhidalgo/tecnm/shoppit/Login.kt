package mx.cdhidalgo.tecnm.shoppit

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Login : AppCompatActivity() {
    //variables globales
    private lateinit var UserF: FirebaseUser
    private lateinit var auth: FirebaseAuth
    private lateinit var btnLg: RelativeLayout

    private lateinit var btnRg: TextView
    private lateinit var LogGoogle : ImageView
    private lateinit var LogGit : ImageView
    // Constante
    private val GSignIn = 100
    val provider = OAuthProvider.newBuilder("github.com")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //github

        auth = FirebaseAuth.getInstance()

        auth = Firebase.auth
        //Acceso a la base de datos CloudFirestore
        val db = Firebase.firestore

        //findview
        btnLg = findViewById(R.id.btnLogin)
        btnRg = findViewById(R.id.btnRegistro)
        LogGoogle = findViewById(R.id.btnG)
        LogGit = findViewById(R.id.btnGit)


        //btn inicio de sesion Google
        LogGoogle.setOnClickListener{
            val googlecon = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val googleClient = GoogleSignIn.getClient(this, googlecon)
            googleClient.signOut()
            startActivityForResult(googleClient.signInIntent, GSignIn)
        }

        //btn Inicio de sesion Git
        LogGit.setOnClickListener {
            val intent = Intent(this, GithubAuth::class.java)
            startActivity(intent)

        }


        btnRg.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }

    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error")

        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GSignIn){

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null){
                    val credential = GoogleAuthProvider.getCredential(account.idToken,null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful){
                            val intent = Intent(this, Dashboard::class.java)
                            startActivity(intent)
                            val toast = Toast.makeText(this, "Inicio de Sesion Correcto", Toast.LENGTH_LONG)
                            toast.show()
                        }else{
                            showAlert()
                        }
                    }
                }

            }catch(e: ApiException){
                showAlert()

            }

        }

    }


}
