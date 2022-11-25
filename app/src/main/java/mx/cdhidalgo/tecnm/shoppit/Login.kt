package mx.cdhidalgo.tecnm.shoppit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    //variables globales

    private lateinit var auth: FirebaseAuth
    private lateinit var btnLg: RelativeLayout

    private lateinit var btnRg: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth
        //Acceso a la base de datos CloudFirestore
        val db = Firebase.firestore

        //findview
        btnLg = findViewById(R.id.btnLogin)
        btnRg = findViewById(R.id.btnRegistro)


        btnLg.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnRg.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }

    }
}