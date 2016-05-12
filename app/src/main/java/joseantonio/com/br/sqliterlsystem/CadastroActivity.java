package joseantonio.com.br.sqliterlsystem;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CadastroActivity extends AppCompatActivity {

    EditText editTextNome;
    EditText editTextEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
    }

    public void cadastrar_onClick(View view){
        editTextNome = (EditText)findViewById(R.id.editTextNome);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);

        /*
        String SQL = "INSERT INTO clientes (nome, email) VALUES ('"+ editTextNome.getText().toString() +"'," +
                "'"+ editTextEmail.getText().toString() +"')";

        SQLiteDatabase db = openOrCreateDatabase("crud.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        db.execSQL(SQL);
        */

        // Ou

        SQLiteDatabase db = openOrCreateDatabase("crud.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        ContentValues values = new ContentValues();
        values.put("nome", editTextNome.getText().toString());
        values.put("email", editTextEmail.getText().toString());

//                nome tabela, "Qual colunas está null, values
        db.insert("clientes", "_id", values);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
