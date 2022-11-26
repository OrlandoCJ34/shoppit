package mx.cdhidalgo.tecnm.shoppit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Dashboard : AppCompatActivity() {
    lateinit var btnLogout:Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        //findview
        btnLogout = findViewById(R.id.Logout)
         btnLogout.setOnClickListener{
             Firebase.auth.signOut()
             intent = Intent(this,Login::class.java)
             startActivity(intent)
         }
    }
}