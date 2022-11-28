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
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mx.cdhidalgo.tecnm.shoppit.DataClass.Usuario


class Login : AppCompatActivity() {
    //variables globales
    private lateinit var UserF: FirebaseUser
    private lateinit var auth: FirebaseAuth
    private lateinit var btnLg: RelativeLayout

    private lateinit var btnRg: TextView
    private lateinit var LogGoogle : ImageView
    private lateinit var LogGit : ImageView

    lateinit var user: TextInputLayout
    lateinit var pass: TextInputLayout
    lateinit var Usuario:Usuario
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

        user = findViewById(R.id.usuario)
        pass = findViewById(R.id.pass)


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
            authWithGithub()

        }


        btnRg.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }

        btnLg.setOnClickListener{
            if (user.editText?.text.toString().isNotEmpty() &&
                pass.editText?.text.toString().isNotEmpty()
            )
                auth.signInWithEmailAndPassword(
                    user.editText?.text.toString(),
                    pass.editText?.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        db.collection("usuarios")
                            .whereEqualTo("correo", user.editText?.text.toString())
                            .get()
                            .addOnSuccessListener { documents ->
                                for (document in documents) {
                                    Usuario = Usuario(
                                        "${document.data.get("correo")}",
                                        "${document.data.get("nombre")}",
                                        "${document.data.get("apaterno")}"

                                    )
                                }
                                val intent = Intent(this, Dashboard::class.java)
                                startActivity(intent)
                            }
                    } else {
                        showAlert()
                    }
                }
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

    private fun authWithGithub() {

        // There's something already here! Finish the sign-in for your user.
        val pendingResultTask: Task<AuthResult>? = auth.pendingAuthResult
        if (pendingResultTask != null) {
            pendingResultTask
                .addOnSuccessListener {
                    // User is signed in.
                    Toast.makeText(this, "User exist", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    // Handle failure.
                    Toast.makeText(this, "Error : $it", Toast.LENGTH_LONG).show()
                }
        } else {

            auth.startActivityForSignInWithProvider( /* activity= */this, provider.build())
                .addOnSuccessListener(
                    OnSuccessListener<AuthResult?> {
                        // User is signed in.
                        // retrieve the current user
                        UserF = auth.currentUser!!

                        // navigate to HomePageActivity after successful login
                        val intent = Intent(this, Dashboard::class.java)

                        // send github user name from MainActivity to HomePageActivity
                        intent.putExtra("githubUserName", UserF.displayName)
                        this.startActivity(intent)
                        Toast.makeText(this, "Login Successfully", Toast.LENGTH_LONG).show()

                    })
                .addOnFailureListener(
                    OnFailureListener {
                        // Handle failure.
                        Toast.makeText(this, "Error : $it", Toast.LENGTH_LONG).show()
                    })
        }

        val firebaseUser: FirebaseUser? = auth.getCurrentUser()
        firebaseUser
            ?.startActivityForLinkWithProvider( /* activity= */this, provider.build())
            ?.addOnSuccessListener {
                // GitHub credential is linked to the current user.
                // IdP data available in
                // authResult.getAdditionalUserInfo().getProfile().
                // The OAuth access token can also be retrieved:
                // authResult.getCredential().getAccessToken().
            }
            ?.addOnFailureListener {
                // Handle failure.
            }


    }


}
