package br.edu.utfpr.flexcalculator

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.flexcalculator.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText
import java.text.NumberFormat

private enum class Fuel(val label: String, val consumptionPerLiter: Double) {
    GASOLINE("Gasolina", 4.69),
    ALCOHOL("Álcool", 3.59),
    DIESEL("Diesel", 3.89),
    NATURAL_GAS("Gás Natural", 3.69),
    FLEX("Flex", 4.29);

    companion object {
        fun getConsumptionPerLiterByLabel(fuel: String?) =
            entries.find { it.label == fuel }?.consumptionPerLiter ?: 0.0
    }
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var fuel1 = "Combustível 1"
    private var fuel2 = "Combustível 2"
    private val templateMessage = "Abasteça com %s\n\n%s: %s/km\n%s: %s/km"
    private val numberFormat = NumberFormat.getCurrencyInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fuelSearch1.setOnClickListener { searchFuel1() }
        binding.fuelSearch2.setOnClickListener { searchFuel2() }
        binding.calculate.setOnClickListener { calculate() }

        updateFuelConsumptionHint()
    }

    private fun updateFuelConsumptionHint() {
        binding.fuelConsumption1TextInput.hint = "Consumo $fuel1"
        binding.fuelConsumption2TextInput.hint = "Consumo $fuel2"
        binding.literFuelValue1TextInput.hint = "Valor Litro $fuel1"
        binding.literFuelValue2TextInput.hint = "Valor Litro $fuel2"
    }

    private fun onResult(result: ActivityResult, editText: TextInputEditText): String? {
        if (result.resultCode == Activity.RESULT_OK) {
            val fuelString = result.data?.getStringExtra("fuel")
            val consumption = Fuel.getConsumptionPerLiterByLabel(fuelString)
            editText.setText(consumption.toString())
            return fuelString
        }
        return null
    }

    private val register =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val fuelString = onResult(result, binding.fuelConsumption1)
            fuel1 = fuelString ?: fuel1
            binding.fuelConsumption1TextInput.error = null
            updateFuelConsumptionHint()
        }

    private val register1 =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val fuelString = onResult(result, binding.fuelConsumption2)
            fuel2 = fuelString ?: fuel2
            binding.fuelConsumption2TextInput.error = null
            updateFuelConsumptionHint()
        }

    private fun searchFuel1() {
        Intent(this, FuelListActivity::class.java).let { register.launch(it) }
    }

    private fun searchFuel2() {
        Intent(this, FuelListActivity::class.java).let { register1.launch(it) }
    }

    private fun calculate() {
        if (!allValid()) return

        closeKeyboard()

        val (autonomy1, autonomy2) = calculateAutonomies()

        val formattedAutonomy1 = numberFormat.format(autonomy1)
        val formattedAutonomy2 = numberFormat.format(autonomy2)

        val message = if (autonomy1 < autonomy2) {
            templateMessage.format(fuel1, fuel1, formattedAutonomy1, fuel2, formattedAutonomy2)
        } else if (autonomy1 > autonomy2) {
            templateMessage.format(fuel2, fuel2, formattedAutonomy2, fuel1, formattedAutonomy1)
        } else {
            "Tanto faz, abasteça com qualquer um dos combustíveis"
        }

        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun closeKeyboard() {
        this.currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun allValid(): Boolean {
        if (binding.fuelConsumption1.text?.isBlank() == true) {
            binding.fuelConsumption1TextInput.error = "Selecione o combustível 1"
            binding.fuelSearch1.requestFocus()
            return false
        }

        if (binding.fuelConsumption2.text?.isBlank() == true) {
            binding.fuelConsumption2TextInput.error = "Selecione o combustível 2"
            binding.fuelSearch2.requestFocus()
            return false
        }

        if (binding.literFuelValue1.text?.isBlank() == true) {
            binding.literFuelValue1.error = "Informe o valor do combustível 1"
            binding.literFuelValue1.requestFocus()
            return false
        }

        if (binding.literFuelValue2.text?.isBlank() == true) {
            binding.literFuelValue2.error = "Informe o valor do combustível 2"
            binding.literFuelValue2.requestFocus()
            return false
        }

        if (fuel1 == fuel2) {
            Toast.makeText(this, "Selecione combustíveis diferentes", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    private fun calculateAutonomies(): Pair<Double, Double> {
        val consumptionPerLiter1 = binding.fuelConsumption1.text.toString().toDouble()
        val consumptionPerLiter2 = binding.fuelConsumption2.text.toString().toDouble()

        val price1 = binding.literFuelValue1.text.toString().toDouble()
        val price2 = binding.literFuelValue2.text.toString().toDouble()

        val autonomy1 = price1 / consumptionPerLiter1
        val autonomy2 = price2 / consumptionPerLiter2

        return Pair(autonomy1, autonomy2)
    }
}