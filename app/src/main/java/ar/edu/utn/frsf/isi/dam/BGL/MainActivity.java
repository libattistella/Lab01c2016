package ar.edu.utn.frsf.isi.dam.BGL;

import android.graphics.Color;
import android.graphics.Interpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView Resultado1;
    private TextView Resultado2;
    private Button btnHacerPlazo;
    private TextView Nrodias;
    SeekBar SEEKcantDias;
    float Interes;
    EditText CampoImporte;
    Integer Dias;
    float Capital;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Resultado1 = (TextView) findViewById(R.id.text_resultado);
        Resultado2 = (TextView) findViewById(R.id.text_resultado2);
        Resultado1.setText("$0.0");
        Resultado2.setText("");
        CampoImporte = (EditText) findViewById(R.id.editText_importe);
        CampoImporte.setText("0");

        Interes = (float) 0.0;

        Nrodias = (TextView) findViewById(R.id.text_nrodias);
        Nrodias.setText("..1");
        SEEKcantDias = (SeekBar) findViewById(R.id.seek_cantdias);

        SEEKcantDias.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ActualizarInteres();
                Nrodias.setText(".." + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                ActualizarInteres();
                Nrodias.setText(".." + seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                ActualizarInteres();
                Nrodias.setText(".." + seekBar.getProgress());
            }


        });

        btnHacerPlazo = (Button) findViewById(R.id.button_hacerpf);
        btnHacerPlazo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActualizarInteres();

                Resultado2.setText("Plazo fijo realizado. Recibirá " + Interes + " al vencimiento!");
                Resultado2.setTextColor(getResources().getColor(R.color.Correcto));
            }
        });
    }

    private void ActualizarInteres(){
        Capital=0;
        Resultado1.setText("$0.0");


        if(!CampoImporte.getText().toString().matches("[0-9]+\\.*[0-9]*")) return;
        Dias = SEEKcantDias.getProgress();
        Capital = Float.parseFloat(CampoImporte.getText().toString());
        Interes = CalculaInteres(Capital,Dias);
        Resultado1.setText("$"+Interes);
    }

    private float CalculaInteres(float Capital,int dias){

        if(Capital<0 ){
            Resultado2.setTextColor(getResources().getColor(R.color.Error));
            Resultado2.setText("Importe Incorrecto");
            return 0;
        }

        float tasa_anual = (float) 0.0;
        if(dias<30){
            if(Capital<=5000){
                tasa_anual = (float) 0.25;
            }
            else if(Capital<=99999){
                tasa_anual = (float) 0.3;
            }
            else{
                tasa_anual = (float) 0.35;
            }

        }
        else{
            if(Capital<=5000){
                tasa_anual = (float) 0.275;
            }
            else if(Capital<=99999){
                tasa_anual = (float) 0.323;
            }
            else{
                tasa_anual = (float) 0.385;
            }
        }
    return (float) (Capital * (Math.pow(1.0+tasa_anual,dias/360.0)-1));
    }
}
