package br.edu.utfpr.flexcalculator

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.flexcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat

private enum class Fuel(val label: String, val consumptionPerLiter: Double) {
    GASOLINE("Gasolina", 4.69),
    ALCOHOL("Álcool", 3.59),
    DIESEL("Diesel", 3.89),
    NATURAL_GAS("Gás Natural", 3.69),
    FLEX("Flex", 4.29);

    companion object {
        fun getConsumptionPerLiterByLabel(fuel: String) =
            entries.find { it.label == fuel }?.consumptionPerLiter ?: 0.0
    }
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fuelSearch1.setOnClickListener { searchFuel1() }
        binding.fuelSearch2.setOnClickListener { searchFuel2() }
        binding.calculate.setOnClickListener { calculate() }
    }

    private val register =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val fuel = data?.getStringExtra("fuel")
                binding.fuelConsumption1.setText(fuel)
            }
        }

    private val register1 =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val fuel = data?.getStringExtra("fuel")
                binding.fuelConsumption2.setText(fuel)
            }
        }

    private fun searchFuel1() {
        Intent(this, FuelListActivity::class.java).let { register.launch(it) }
    }

    private fun searchFuel2() {
        Intent(this, FuelListActivity::class.java).let { register1.launch(it) }
    }

    private fun calculate() {
        val fuel1 = binding.fuelConsumption1.text.toString()
        val fuel2 = binding.fuelConsumption2.text.toString()

        val consumptionPerLiter1 = Fuel.getConsumptionPerLiterByLabel(fuel1)
        val consumptionPerLiter2 = Fuel.getConsumptionPerLiterByLabel(fuel2)

        val price1 = binding.literFuelValue1.text.toString().toDouble()
        val price2 = binding.literFuelValue2.text.toString().toDouble()

        val autonomy1 = price1 / consumptionPerLiter1
        val autonomy2 = price2 / consumptionPerLiter2

        val numberFormat = NumberFormat.getCurrencyInstance()
        val formattedAutonomy1 = numberFormat.format(autonomy1)
        val formattedAutonomy2 = numberFormat.format(autonomy2)

        val message = if (autonomy1 < autonomy2) {
            "Abasteça com $fuel1 - ($formattedAutonomy1 x $formattedAutonomy2)/km"
        } else {
            "Abasteça com $fuel2 - ($formattedAutonomy2 x $formattedAutonomy1)/km"
        }

        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}