package br.edu.utfpr.trocatelas

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ConfirmarActivity : AppCompatActivity() {

    private lateinit var tvCod: TextView
    private lateinit var tvQtd: TextView
    private lateinit var tvValor: TextView
    private lateinit var btEnviar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar)

        tvCod = findViewById(R.id.tvCod)
        tvQtd = findViewById(R.id.tvQtd)
        tvValor = findViewById(R.id.tvValor)
        btEnviar = findViewById(R.id.btEnviar)

        btEnviar.setOnClickListener { btEnviarOnClick() }

        tvCod.text = intent.getStringExtra("cod")
        tvQtd.text = intent.getStringExtra("qtd")
        tvValor.text = intent.getStringExtra("valor")
    }

    private fun btEnviarOnClick() {
        val smsBody = "Código: ${tvCod.text}, Quantidade: ${tvQtd.text}, Valor: ${tvValor.text}"
        val phoneNumber = "sms:123456789"

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(phoneNumber)
        intent.putExtra("sms_body", smsBody)

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}