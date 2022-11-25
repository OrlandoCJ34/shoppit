package mx.cdhidalgo.tecnm.shoppit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import com.google.android.material.textfield.TextInputLayout

class Registro : AppCompatActivity() {

    //vartables globales

    private lateinit var btnbck: ImageView
    private lateinit var btnRegistro: RelativeLayout

    //campos
    private lateinit var nombre: TextInputLayout
    private lateinit var apaterno: TextInputLayout
    private lateinit var correo: TextInputLayout
    private lateinit var pass: TextInputLayout

    //Confirmar Registro
    private lateinit var btnConfirmar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        //findview
        nombre = findViewById(R.id.txtNombre)
        apaterno = findViewById(R.id.txtApellido)
        correo = findViewById(R.id.txtCorreo)
        pass = findViewById(R.id.txtPassword)
        btnRegistro = findViewById(R.id.btnRegistro)
        btnbck = findViewById(R.id.btnBack)


        btnRegistro.setOnClickListener{
            val intent = Intent(this, CRegistro::class.java)
            intent.putExtra("nombre", nombre.editText?.text.toString())
            intent.putExtra("apaterno", apaterno.editText?.text.toString())
            intent.putExtra("correo", correo.editText?.text.toString())
            intent.putExtra("password", pass.editText?.text.toString())
            startActivity(intent)
        }
        btnbck.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
}