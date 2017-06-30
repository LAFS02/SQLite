package com.lmntrx.android.sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText dataET;

    SQLiteDatabase database;


    String TABLE_CREATE_STATEMENT = "CREATE TABLE IF NOT EXISTS NAMES_TABLE (" +
            "NAME VARCHAR" +
            ");";

    String INSERT_STATEMENT = "INSERT INTO NAMES_TABLE VALUES('"; //Complete statement during usage

    String SELECT_QUERY = "SELECT * FROM NAMES_TABLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataET = (EditText) findViewById(R.id.dataET);

        database = openOrCreateDatabase("db",MODE_PRIVATE,null);

        database.execSQL(TABLE_CREATE_STATEMENT);

    }

    public void insert(View view) {

        String data = dataET.getText().toString();

        String completeInsertStatement = INSERT_STATEMENT + data + "');";

        database.execSQL(completeInsertStatement);

        Toast.makeText(this, "Added to database", Toast.LENGTH_SHORT).show();

    }

    public void view(View view) {

        String[] names;

        Cursor cursor = database.rawQuery(SELECT_QUERY,null);

        names = new String[cursor.getCount()];

        int i = 0;

        while (cursor.moveToNext()){
            names[i++] = cursor.getString(0);
        }

        cursor.close();

        Toast.makeText(this, names[0], Toast.LENGTH_SHORT).show();

        Log.d("DATA",names[0]);

        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("DATA", names);
        startActivity(intent);

    }
}
