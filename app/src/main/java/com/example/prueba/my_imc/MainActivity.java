package com.example.prueba.my_imc;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Toolbar mToolbar;
    private Button mButton;
    private EditText mETPeso;
    private EditText mETEstatura;
    private TextView mTVClasificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Mi IMC");

        mETPeso = findViewById(R.id.et_peso);
        mETEstatura = findViewById(R.id.et_estatura);
        mTVClasificacion = findViewById(R.id.tv_clasificacion);

        mButton = findViewById(R.id.btn_calcular);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double peso = Double.parseDouble(mETPeso.getText().toString());
                double estatura = Double.parseDouble(mETEstatura.getText().toString());

                double imc = (peso / (estatura*estatura));

                Log.d(TAG, "onClick: peso, estatura = " + peso + ", " + estatura);

                if(peso < 0 || estatura < 0) { //en caso de que el edit text no cape los datos por XML
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Oops")
                            .setMessage("Por favor introduzca un peso y una estatura válidos (números decimales mayores a cero).")
                            .setCancelable(false)
                            .setPositiveButton("Ok",null)
                            .show();
                } else {
                    calcular(imc);
                }

            }
        });
    }


    private void calcular(final double imc){
        String clasificacion = "";

        if (imc >= 0D && imc < 16.00D) {
            clasificacion = "Delgadez severa";
        } else if (imc >= 16.00D && imc < 17.50D) {
            clasificacion = "Delgadez moderada";
        } else if (imc >= 17.50D && imc < 18.50D) {
            clasificacion = "Delgadez leve";
        } else if (imc >= 18.50D && imc < 25.00) {
            clasificacion = "Normal";
        } else if (imc >= 25.00 && imc < 30.00D) {
            clasificacion = "Preobeso";
        } else if (imc >= 30.00D && imc < 35.00) {
            clasificacion = "Obesidad leve";
        } else if (imc >= 35.00 && imc < 40.00) {
            clasificacion = "Obesidad media";
        } else if (imc >= 40.00D) {
            clasificacion = "Obesidad mórbida";
        }else {
            clasificacion = "Sin clasificación";
        }

        final String finalClasificacion = clasificacion;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTVClasificacion.setText("Tu IMC es de " + new DecimalFormat("#.##").format(imc) + " kg/m². Tu clasificación es: " + finalClasificacion + ".");
            }
        });
    }


}
