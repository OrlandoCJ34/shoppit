package mx.cdhidalgo.tecnm.shoppit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    //variables globales

    private lateinit var auth: FirebaseAuth
    private lateinit var btnLg: RelativeLayout

    private lateinit var btnRg: TextView
    private lateinit var LogGoogle : ImageView
    private lateinit var LogTwitter : ImageView
    // Constante
    private val GSignIn = 100



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth
        //Acceso a la base de datos CloudFirestore
        val db = Firebase.firestore

        //findview
        btnLg = findViewById(R.id.btnLogin)
        btnRg = findViewById(R.id.btnRegistro)
        LogGoogle = findViewById(R.id.btnG)


        LogGoogle.setOnClickListener{
            val googlecon = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val googleClient = GoogleSignIn.getClient(this, googlecon)
            googleClient.signOut()
            startActivityForResult(googleClient.signInIntent, GSignIn)
        }




        btnLg.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
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