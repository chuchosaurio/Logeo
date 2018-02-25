package mx.unam.fes.logeo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    public static final String TAG ="CambioEstado";
    private Spinner spinner;
    private String escuelas[] = new String[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        escuelas = new String[]{"UNAM", "IPN", "UVM", "UAM"};
        spinner = (Spinner) findViewById(R.id.spinner);
        addEscuelas(escuelas);
        startNewActivity();
    }

    private void addEscuelas(String[] escuelas){
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                escuelas);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
    }

    private void startNewActivity(){
        Button boton =  (Button) findViewById(R.id.button);
        final String admin = "admin";
        final String master= "contraseña";
        boton.setOnClickListener(new View.OnClickListener() {
            EditText editText = (EditText) findViewById(R.id.editText);
            EditText editText2 = (EditText) findViewById(R.id.editText2);

            @Override
            public void onClick(View v) {
                String user = editText.getText().toString();
                String psw = editText2.getText().toString();
                if (user.equals(admin)  && psw.equals(master)){
                    Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
                    String nombre = editText.getText().toString();
                    String escuela = spinner.getSelectedItem().toString();
                    String mensaje = ("Hola "+nombre + "\n bienvenido a "+escuela);
                    intent.putExtra("MESSAGE", mensaje);
                    startNewActivity();
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
                    String mensaje = ("Acceso no autorizado");
                    intent.putExtra("MESSAGE", mensaje);
                    startNewActivity();
                    startActivity(intent);
                }

            }
        });
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstance");
        EditText textBox = (EditText) findViewById(R.id.editText);
        CharSequence userText = savedInstanceState.getCharSequence("savedText");
        textBox.setText(userText);
        if(savedInstanceState!=null){
            escuelas = savedInstanceState.getStringArray("spinnerData");
            spinner.setSelection(savedInstanceState.getInt("spinnerItem", 0));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstance");
        EditText textBox = (EditText) findViewById(R.id.editText);
        CharSequence userText = textBox.getText();
        outState.putCharSequence("savedText", userText);
        outState.putStringArray("spinnerData", escuelas);
        outState.putInt("spinnerItem", spinner.getSelectedItemPosition());
    }

}
