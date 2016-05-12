package joseantonio.com.br.sqliterlsystem;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditarActivity extends AppCompatActivity {

    EditText editTextEditarNome;
    EditText editTextEditarEmail;
    private Intent it = null;
    private SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        it = getIntent();
        db = openOrCreateDatabase("crud.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);

        editTextEditarNome = (EditText)findViewById(R.id.editTextEditarNome);
        editTextEditarEmail = (EditText)findViewById(R.id.editTextEditarEmail);


        editTextEditarNome.setText(it.getStringExtra("nome"));
        editTextEditarEmail.setText(it.getStringExtra("email"));

    }

    public void atualizar_onClick(View view){

        ContentValues values = new ContentValues();
        values.put("nome", editTextEditarNome.getText().toString());
        values.put("email", editTextEditarEmail.getText().toString());

        if(db.update("clientes",values, "_id=?", new String[]{String.valueOf(it.getIntExtra("codigo", 0))}) > 0){
            Toast.makeText(getBaseContext(), "Sucesso", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
    public void apagar_onClick(View view){

        if(db.delete("clientes", "_id=?", new String[]{String.valueOf(it.getIntExtra("codigo", 0))}) > 0){
            Toast.makeText(getBaseContext(), "Sucesso", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}
