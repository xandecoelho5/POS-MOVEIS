package br.edu.utfpr.flexcalculator

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.flexcalculator.databinding.ActivityFuelListBinding

class FuelListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFuelListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fuel_list)

        binding = ActivityFuelListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fuelList.setOnItemClickListener { _, _, pos, _ ->
            val fuel = binding.fuelList.getItemAtPosition(pos).toString()
            Intent().apply {
                putExtra("fuel", fuel)
                setResult(RESULT_OK, this)
            }
            finish()
        }
    }
}