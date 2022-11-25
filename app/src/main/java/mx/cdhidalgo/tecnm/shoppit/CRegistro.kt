package mx.cdhidalgo.tecnm.shoppit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mx.cdhidalgo.tecnm.shoppit.DataClass.Usuario

class CRegistro : AppCompatActivity() {
    //variables globales
    private lateinit var texto1: TextView
    private lateinit var texto2: TextView
    private lateinit var btnConfirmar: RelativeLayout
    private lateinit var btnRgr: RelativeLayout
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cregistro)

        //Inicializar la autenticación
        auth = Firebase.auth

        //Acceso a la base de datos CloudFirestore
        val db = Firebase.firestore

        //findview
        texto1 = findViewById(R.id.etiqueta1)
        texto2 = findViewById(R.id.etiqueta2)

        btnRgr = findViewById(R.id.Regresar)
        btnConfirmar = findViewById(R.id.ConfirmarD)

        //Datos
        val nombre = intent.getStringExtra("nombre")
        val apaterno = intent.getStringExtra("apaterno")
        val correo = intent.getStringExtra("correo")
        val password = intent.getStringExtra("password")

        texto1.text = "$nombre $apaterno"

        texto2.text = "Tus datos proporcionados son:\n" +
                "Correo Electrónico: $correo\n" +
                "Contraseña: $password\n"

        val usuario = Usuario(
            correo.toString(), nombre.toString(),
            apaterno.toString()
        )

        btnConfirmar.setOnClickListener{
            if (correo.toString().isNotEmpty() &&
                password.toString().isNotEmpty()
            ) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    correo.toString(), password.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, Login::class.java)
                        db.collection("usuarios")
                            .document(correo.toString())
                            .set(usuario) //UID
                        startActivity(intent)

                    } else {
                        showAlert()
                    }
                }
            }


        }
        btnRgr.setOnClickListener {
            finish()
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
    }
