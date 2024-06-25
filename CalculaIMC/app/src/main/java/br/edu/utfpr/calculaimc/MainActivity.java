package br.edu.utfpr.calculaimc;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText etPeso;
    private EditText etAltura;
    private TextView tvResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPeso = findViewById(R.id.etPeso);
        etAltura = findViewById(R.id.etAltura);
        tvResultado = findViewById(R.id.tvResultado);

        findViewById(R.id.btCalcular).setOnClickListener(v -> calcularIMC());
        findViewById(R.id.btCalcular).setOnLongClickListener(v -> calcularIMCSecreto());
        findViewById(R.id.btLimpar).setOnClickListener(v -> limpar());
    }

    private void calcularIMC() {
        if (etPeso.getText().toString().isEmpty()) {
            etPeso.setError("Preencha o peso");
            etPeso.requestFocus();
            return;
        }

        if (etAltura.getText().toString().isEmpty()) {
            etAltura.setError("Preencha a altura");
            etAltura.requestFocus();
            return;
        }

        double peso = Double.parseDouble(etPeso.getText().toString());
        double altura = Double.parseDouble(etAltura.getText().toString());

        double imc = peso / (altura * altura);

        DecimalFormat df = new DecimalFormat("0.00");
        tvResultado.setText(df.format(imc));
    }

    private void limpar() {
        etPeso.setText("");
        etAltura.setText("");
        tvResultado.setText("");
    }

    private boolean calcularIMCSecreto() {
        double peso = Double.parseDouble(etPeso.getText().toString());
        double altura = Double.parseDouble(etAltura.getText().toString());

        double imc = peso / (altura * altura) * 2;

        DecimalFormat df = new DecimalFormat("0.00");
        tvResultado.setText(df.format(imc));
        return true;
    }
}