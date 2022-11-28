package mx.cdhidalgo.tecnm.shoppit

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import mx.cdhidalgo.tecnm.shoppit.DataClass.Producto


class Agregar : AppCompatActivity() {
    //variables globales
    private lateinit var qr:ImageView
    lateinit var tvEscaneado : MaterialTextView
    lateinit var btnAgregar : RelativeLayout
    lateinit var btnValidar : RelativeLayout
    private lateinit var auth: FirebaseAuth
    lateinit var NombreP : TextInputLayout
    lateinit var Precio : TextInputLayout
    lateinit var tvprecio: MaterialTextView
    lateinit var tvnombre:MaterialTextView

     var vNombre = ""
     var vPrecio = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar)
        //firebase
        auth = Firebase.auth
        val db = FirebaseFirestore.getInstance()
        //findview
        qr =findViewById(R.id.qrScan)
        //tv
        tvEscaneado =findViewById(R.id.tvscan)
        tvprecio = findViewById(R.id.tvprecio)
        tvnombre = findViewById(R.id.tvproducto)
        //
        btnAgregar = findViewById(R.id.btnAgregar)
        btnValidar = findViewById(R.id.btnValidar)
        NombreP = findViewById(R.id.txtNombre)
        Precio = findViewById(R.id.txtPrecio)
        //

        vNombre = NombreP.editText?.text.toString()
        vPrecio = Precio.editText?.text.toString()



       //Poner Datos
        intent.putExtra("prod", vNombre)
        intent.putExtra("precio", vPrecio)



        //Recuperar Datos
        val bundle=intent.extras
        val np = bundle?.getString("prod")
        val valor = bundle?.getString("codigo")



        //scanner
        tvEscaneado.text = valor

        val product = Producto(
           tvnombre.toString(),tvprecio.toString(),valor.toString()

        )

        qr.setOnClickListener{
            intent = Intent(this,Scanner::class.java)
            startActivity(intent)
        }

        btnValidar.setOnClickListener{
            tvprecio.text = Precio.editText?.text.toString()
            tvnombre.text = NombreP.editText?.text.toString()
        }

        btnAgregar.setOnClickListener{
        db.collection("productos").document(tvEscaneado.text.toString()).set(
            hashMapOf("Precio" to tvprecio.text, "Nombre" to tvnombre.text,"Codigo" to tvEscaneado.text)
        )
            ShowAlert()
        }


    }

    private fun ShowAlert(){
        val toast = Toast.makeText(this, "Success", Toast.LENGTH_LONG)
        toast.show()
    }
}