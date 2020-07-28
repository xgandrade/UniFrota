package br.com.unifrota;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import br.com.unifrota.pojo.Carro;
import br.com.unifrota.pojo.FABRICANTE;
import br.com.unifrota.utils.PostAPI;

public class EditCarro extends AppCompatActivity {
    EditText edFabricante, edModelo, edCor, edAno, edPlaca;
    CheckBox checkAlugado;
    Button btSalvar;
    Carro carro = new Carro();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_carro);

        edFabricante = findViewById(R.id.edMarca);
        edModelo = findViewById(R.id.edModelo);
        edCor = findViewById(R.id.ediCor);
        edAno = findViewById(R.id.editAno);
        edPlaca = findViewById(R.id.editPlaca);
        checkAlugado = findViewById(R.id.checkAlugadoCadastro);

        ArrayAdapter<FABRICANTE> adaptadorMarcas = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, FABRICANTE.values());
        AutoCompleteTextView editCampoMarca = findViewById(R.id.edMarca);
        editCampoMarca.setAdapter(adaptadorMarcas);
        editCampoMarca.setKeyListener(null);
    }

    public void btSalvar(View v){
        if(validaDados()){
            //validou os campos e pode salvar...
            carro.setIdCarro(-1);
            carro.setFabricante(edFabricante.getText().toString());
            carro.setModelo(edModelo.getText().toString());
            carro.setPlaca(edPlaca.getText().toString());
            carro.setCor(edCor.getText().toString());
            carro.setAlugado(checkAlugado.isChecked());
            carro.setAno(Integer.parseInt(edAno.getText().toString()));

            try {
                boolean executou = new PostAPI(carro).execute().get();
            }catch (Exception e){
                e.printStackTrace();
            }

            Toast.makeText(this, R.string.carroCadastrado, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private boolean validaDados(){
        if(isEmpty(edFabricante)){
            mostraToastCampoVazio();
            edFabricante.requestFocus();
            return false;
        }else if (isEmpty(edModelo)){
            mostraToastCampoVazio();
            edModelo.requestFocus();
            return false;
        }else if(isEmpty(edPlaca)){
            mostraToastCampoVazio();
            edPlaca.requestFocus();
            return false;
        } else if(isEmpty(edAno)){
            mostraToastCampoVazio();
            edAno.requestFocus();
            return false;
        } else if(isEmpty(edCor)){
            mostraToastCampoVazio();
            edCor.requestFocus();
            return false;
        }
        return true;
    }

    private void mostraToastCampoVazio(){
        Toast.makeText(this, R.string.campoVazio, Toast.LENGTH_SHORT).show();
    }

    private boolean isEmpty(EditText edText){
        return edText.getText().toString().trim().length() == 0;
    }
}








