package mx.cdhidalgo.tecnm.shoppit.CRUD

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
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
import mx.cdhidalgo.tecnm.shoppit.Dashboard
import mx.cdhidalgo.tecnm.shoppit.DataClass.Producto
import mx.cdhidalgo.tecnm.shoppit.R
import mx.cdhidalgo.tecnm.shoppit.Scanners.Scanner


class Agregar : AppCompatActivity() {
    //variables globales
    private lateinit var qr:ImageView
    private lateinit var btnBack:ImageView
    lateinit var tvEscaneado : MaterialTextView
    lateinit var btnAgregar : RelativeLayout
    lateinit var btnValidar : RelativeLayout
    private lateinit var auth: FirebaseAuth
    lateinit var NombreP : TextInputLayout
    lateinit var Precio : TextInputLayout
    lateinit var Stock : TextInputLayout
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
        btnBack = findViewById(R.id.backbtna)
        //tv
        tvEscaneado =findViewById(R.id.tvscan)
        tvprecio = findViewById(R.id.tvprecio)
        tvnombre = findViewById(R.id.tvproducto)
        //
        btnAgregar = findViewById(R.id.btnAgregar)
        btnValidar = findViewById(R.id.btnValidar)
        NombreP = findViewById(R.id.txtNombre)
        Precio = findViewById(R.id.txtPrecio)
        Stock = findViewById(R.id.txtstock)
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




        qr.setOnClickListener{
            intent = Intent(this, Scanner::class.java)
            startActivity(intent)
        }

        btnBack.setOnClickListener{
            intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }

        btnValidar.setOnClickListener{
            tvprecio.text = Precio.editText?.text.toString()
            tvnombre.text = NombreP.editText?.text.toString()
        }

        btnAgregar.setOnClickListener{
        db.collection("productos").document(tvEscaneado.text.toString()).set(
            hashMapOf("precio" to tvprecio.text, "nombre" to tvnombre.text,"codigo" to tvEscaneado.text,
            "stock" to Stock.editText?.text.toString())
            )


            ShowAlert()
        }
        fun Leer(){
            val docRef = db.collection("productos").document(tvEscaneado.text.toString())
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null){
                        Log.d("TAG","DocumentSnapshot data: ${document.data}")
                        Log.d("TAG"," ${document.get("nombre")as String}")
                    }

                }
                .addOnFailureListener{
                    exception ->
                    Log.d("TAG","Error al obtener ${exception}")
                }
        }

        }




    private fun ShowAlert(){
        val toast = Toast.makeText(this, "Success", Toast.LENGTH_LONG)
        toast.show()
    }



    }


