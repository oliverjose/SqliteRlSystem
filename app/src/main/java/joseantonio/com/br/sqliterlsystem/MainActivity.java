package joseantonio.com.br.sqliterlsystem;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db = null;
    private SimpleCursorAdapter adapter = null;

    ListView listViewClientes;
    EditText editTextBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        listViewClientes = (ListView)findViewById(R.id.listViewClientes);
        editTextBuscar = (EditText)findViewById(R.id.editTextBuscar);

        //Criar Banco
        db = openOrCreateDatabase("crud.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        String clientes = "CREATE TABLE IF NOT EXISTS clientes (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome VARCHAR(50), email VARCHAR(65))";

        db.execSQL(clientes);



        editTextBuscar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String[] busca = new String[]{"%" + editTextBuscar.getText().toString() + "%"};
                Cursor cursor = db.query("clientes", new String[]{"_id", "nome", "email"}, "nome LIKE ?", busca, null, null, null, null);
                adapter.changeCursor(cursor);
                return false;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //preencher Listview
        //Cursor cursor = db.query("clientes", new String[]{"_id", "nome", "email"}, null, null, null, null, null, "1"); //Limit
        //Cursor cursor = db.query("clientes", new String[]{"_id", "nome", "email"}, null, null, null, null, "_id DESC", null); //ORDER BY
        //Cursor cursor = db.query("clientes", new String[]{"_id", "nome", "email"}, "_id=?", new String[]{"2"}, null, null, null, null); //Selection tras um unico registro

        Cursor cursor = db.query("clientes", new String[]{"_id", "nome", "email"}, null, null, null, null, null, null);
        // ou
        //Cursor cursor = db.rawQuery("select _id, nome, email from clientes", null);


        String[] campos = {"_id", "nome"};
        int[] ids = {R.id.textViewModelId, R.id.textViewModelNome};

        adapter = new SimpleCursorAdapter(getBaseContext(), R.layout.layout_model_clientes, cursor, campos, ids, 0);

        listViewClientes.setAdapter(adapter);


        listViewClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor retornoCursor = (Cursor) adapter.getItem(position);
                //Toast.makeText(getBaseContext(), String.valueOf(retornoCursor.getInt(0)), Toast.LENGTH_SHORT).show();
                Intent intentEditar = new Intent(getBaseContext(), EditarActivity.class);
                intentEditar.putExtra("codigo", retornoCursor.getInt(0));
                intentEditar.putExtra("nome", retornoCursor.getString(1));
                intentEditar.putExtra("email", retornoCursor.getString(retornoCursor.getColumnIndex("email")));
                startActivity(intentEditar);

            }
        });
    }

    public void cadastro_onClick(View view){
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }
}
