package br.edu.utfpr.fluxocaixa

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.fluxocaixa.database.DatabaseHandler
import br.edu.utfpr.fluxocaixa.databinding.ActivityMainBinding
import br.edu.utfpr.fluxocaixa.entity.Lancamento
import br.edu.utfpr.fluxocaixa.fragment.DatePickerFragment

val tipos = listOf("Crédito", "Débito")
val detalhesCredito = listOf("Salário", "Extras")
val detalhesDebito = listOf("Alimentação", "Transporte", "Saúde", "Moradia")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var banco: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonListener()
        banco = DatabaseHandler(this)

        binding.spTipo.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            tipos
        )
    }

    private fun setButtonListener() {
        binding.btLancar.setOnClickListener { btLancarOnClick() }
        binding.btVerLancamentos.setOnClickListener { btVerLancamentosOnClick() }
        binding.btSaldo.setOnClickListener { btSaldoOnClick() }
        binding.btDatePicker.setOnClickListener { showDatePickerDialog() }
        binding.spTipo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val tipo = tipos[position]
                binding.spDetalhe.adapter = ArrayAdapter(
                    this@MainActivity,
                    android.R.layout.simple_spinner_dropdown_item,
                    if (tipo == "Crédito") detalhesCredito else detalhesDebito
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }
        }
    }

    private fun btLancarOnClick() {
        if (binding.etValor.text.isEmpty()) {
            binding.etValor.error = "Informe o valor"
            binding.etValor.requestFocus()
            return
        }
        if (binding.etDataLancamento.text.isEmpty()) {
            binding.etDataLancamento.error = "Informe a data"
            binding.etDataLancamento.requestFocus()
            return
        } else {
            val date = binding.etDataLancamento.text.toString()
            val regex = Regex("""\d{2}/\d{2}/\d{4}""")
            if (!regex.matches(date)) {
                binding.etDataLancamento.error = "Data inválida"
                binding.etDataLancamento.requestFocus()
                return
            }
        }

        banco.insert(
            Lancamento(
                0,
                binding.spTipo.selectedItem.toString(),
                binding.spDetalhe.selectedItem.toString(),
                binding.etValor.text.toString().toDouble(),
                binding.etDataLancamento.text.toString()
            )
        )
        Toast.makeText(this, "Sucesso!!!", Toast.LENGTH_LONG).show()
        hideKeyboard(binding.root)

        binding.etValor.text.clear()
        binding.etDataLancamento.text.clear()
        binding.etValor.requestFocus()
    }

    private fun btVerLancamentosOnClick() {
        val intent = Intent(this, ListarActivity::class.java)
        startActivity(intent)
    }

    private fun btSaldoOnClick() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Saldo")
        builder.setMessage("R$ %.2f".format(calculateSaldo()))
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

    private fun calculateSaldo(): Double {
        val lancamentos = banco.getAll()
        return lancamentos.groupBy { it.tipo }
            .mapValues { it.value.sumOf { lancamento -> lancamento.valor } }
            .let { (it["Crédito"] ?: 0.0) - (it["Débito"] ?: 0.0) }
    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment(dateSetted = ::onDateSet)
        newFragment.show(supportFragmentManager, "datePicker")
    }

    private fun onDateSet(year: Int, month: Int, day: Int) {
        val placeholder = "%02d/%02d/%04d"
        binding.etDataLancamento.setText(placeholder.format(day, month, year))
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}