package com.caiogallo.realmtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.caiogallo.realmtest.data.User;

import io.realm.Realm;

public class EditActivity extends Activity {
    private EditText editNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editNome = (EditText) findViewById(R.id.nome);
    }

    public void salvar(View view) {
        Realm realm = Realm.getInstance(this);
        try {
            String nome = editNome.getText().toString();


            realm.beginTransaction();
            User user = realm.createObject(User.class);
            user.setName(nome);
            realm.commitTransaction();

            Toast.makeText(this, R.string.salvo_com_sucesso, Toast.LENGTH_SHORT).show();
            editNome.setText("");
        } catch (Exception e) {
            if(realm.isInTransaction()) {
                realm.cancelTransaction();
            }
            Toast.makeText(this, R.string.erro_ao_salvar, Toast.LENGTH_SHORT).show();
        }

    }

    public void listar(View view){
        startActivity(new Intent(this, ListActivity.class));
    }
}
