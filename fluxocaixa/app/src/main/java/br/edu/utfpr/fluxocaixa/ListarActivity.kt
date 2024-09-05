package br.edu.utfpr.fluxocaixa

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.fluxocaixa.adapter.LancamentoAdapter
import br.edu.utfpr.fluxocaixa.database.DatabaseHandler
import br.edu.utfpr.fluxocaixa.databinding.ActivityListarBinding

class ListarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListarBinding
    private lateinit var banco: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        banco = DatabaseHandler(this)
    }

    override fun onStart() {
        super.onStart()
        val data = banco.getAll()
        if (data.isEmpty()) {
            binding.emptyView.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        } else {
            binding.emptyView.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            binding.recyclerView.adapter = LancamentoAdapter(this, data)
        }
    }
}