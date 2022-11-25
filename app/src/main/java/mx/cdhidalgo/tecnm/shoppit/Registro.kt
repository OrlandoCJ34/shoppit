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
    private lateinit var btnLogin: RelativeLayout

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
        nombre = findViewById(R.id.LName)
        apaterno = findViewById(R.id.LApellidoP)
        correo = findViewById(R.id.LCorreo)
        pass = findViewById(R.id.LPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnbck = findViewById(R.id.btnBack)

        btnbck.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
}