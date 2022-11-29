package mx.cdhidalgo.tecnm.shoppit.CRUD

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import mx.cdhidalgo.tecnm.shoppit.Dashboard
import mx.cdhidalgo.tecnm.shoppit.DataClass.Producto
import mx.cdhidalgo.tecnm.shoppit.R
import mx.cdhidalgo.tecnm.shoppit.Scanners.Scanner

class Eliminar : AppCompatActivity() {
    //variables globales
    //tv
    private lateinit var qr:ImageView
    private lateinit var back:ImageView
    lateinit var tvEscaneado : MaterialTextView
    private lateinit var auth: FirebaseAuth
    lateinit var btnEliminar : RelativeLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eliminar)
        qr = findViewById(R.id.qrScaneo)
        //firebase
        auth = Firebase.auth
        val db = FirebaseFirestore.getInstance()
        //findview
        back = findViewById(R.id.backbtn)
        //tv
        tvEscaneado =findViewById(R.id.tvcodigo)
        //
        btnEliminar = findViewById(R.id.btnEliminar)

        //Poner Dato

        //Recuperar Datos
        val bundle=intent.extras
        val valor = bundle?.getString("codigo")


        //scanner
        tvEscaneado.text = valor

        qr.setOnClickListener{
            intent = Intent(this, Scanner::class.java)
            startActivity(intent)
        }

        back.setOnClickListener{
            intent = Intent(this,Dashboard::class.java)
            startActivity(intent)
        }

        btnEliminar.setOnClickListener{

            db.collection("productos").document(tvEscaneado.text.toString()).delete()
        }

        }

    private fun ShowAlert(){
        val toast = Toast.makeText(this, "Eliminado", Toast.LENGTH_LONG)
        toast.show()
    }


    }



