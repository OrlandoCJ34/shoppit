package mx.cdhidalgo.tecnm.shoppit.Scanners

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import mx.cdhidalgo.tecnm.shoppit.CRUD.Agregar
import mx.cdhidalgo.tecnm.shoppit.CRUD.Eliminar
import mx.cdhidalgo.tecnm.shoppit.R

class Scanner : AppCompatActivity() {
    //variables locales
    private lateinit var codeScanner: CodeScanner
    private lateinit var btnYa:Button
    private lateinit var btnBorrar:Button
    var code =""
    val MY_CAMERA_PERMISSION_REQUEST = 1111

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        btnYa=findViewById(R.id.btnYa)
        btnBorrar = findViewById(R.id.btnDelete)
        val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)

        codeScanner = CodeScanner(this, scannerView)

        // Parameters (default values)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        //ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.CONTINUOUS // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                Toast.makeText(this, "Resultado del escaneo: ${it.text}", Toast.LENGTH_LONG).show()
                intent.putExtra("codigo",it.text)
                code = it.text.toString()
            }

        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(
                    this, "Camera error: ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        checkPermission()
        btnYa.setOnClickListener {
            val intent = Intent(this, Agregar::class.java)
            intent.putExtra("codigo",code)
            startActivity(intent)
        }

        btnBorrar.setOnClickListener {
            val intent = Intent(this, Eliminar::class.java)
            intent.putExtra("codigo",code)
            startActivity(intent)
        }


    }
    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),MY_CAMERA_PERMISSION_REQUEST)
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode==MY_CAMERA_PERMISSION_REQUEST&&grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            codeScanner.startPreview()
        }else{
            Toast.makeText(this,"No se podra escanear hasta que nos des el permiso",Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
}