package mx.cdhidalgo.tecnm.shoppit

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.TextUtils


class GithubAuth : AppCompatActivity() {
    //variables globales
    private lateinit var UserF: FirebaseUser
    private lateinit var auth: FirebaseAuth
    lateinit var Correo: TextInputEditText
    lateinit var btnLoginGit: ImageView
    val provider = OAuthProvider.newBuilder("github.com")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_auth)
        //findviewbyid
        Correo = findViewById(R.id.CorreoGit)
        btnLoginGit = findViewById(R.id.btnIniciarGit)

        auth = FirebaseAuth.getInstance()


        btnLoginGit.setOnClickListener {
            if (TextUtils.isEmpty(Correo.text.toString())) {
                Toast.makeText(this, "Pon tu Correo", Toast.LENGTH_LONG).show()
            } else {
                authWithGithub()
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