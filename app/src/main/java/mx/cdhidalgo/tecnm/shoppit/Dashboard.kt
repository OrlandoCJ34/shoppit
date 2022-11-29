package mx.cdhidalgo.tecnm.shoppit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import mx.cdhidalgo.tecnm.shoppit.CRUD.Agregar
import mx.cdhidalgo.tecnm.shoppit.CRUD.Eliminar

class Dashboard : AppCompatActivity() {
    lateinit var btnLogout:ImageView
    private lateinit var auth: FirebaseAuth

    //Crud
    lateinit var agregar: ImageView
    lateinit var eliminar: ImageView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        //findview
        btnLogout = findViewById(R.id.Logout)
        agregar = findViewById(R.id.Agregar)
        eliminar = findViewById(R.id.Eliminar)

        agregar.setOnClickListener {
            intent = Intent(this, Agregar::class.java)
            startActivity(intent)
        }

        eliminar.setOnClickListener{
            intent = Intent(this, Eliminar::class.java)
            startActivity(intent)
        }


        btnLogout.setOnClickListener{
             Firebase.auth.signOut()
             intent = Intent(this,Login::class.java)
             startActivity(intent)
         }

    }
}