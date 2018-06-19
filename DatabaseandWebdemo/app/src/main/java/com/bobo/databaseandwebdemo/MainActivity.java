package com.bobo.databaseandwebdemo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
            DATABASE
         */

        try
        {
            SQLiteDatabase database = this.openOrCreateDatabase("mydatabase", Context.MODE_PRIVATE, null);

            //Creo il database
            database.execSQL("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, name VARCHAR, age INTEGER(3))");

            //Aggiungo qualche utente
            database.execSQL("INSERT INTO users (name, age) VALUES ('Rosina', 25)");
            database.execSQL("INSERT INTO users (name, age) VALUES ('Alfredo', 21)");
            database.execSQL("INSERT INTO users (name, age) VALUES ('Roberto', 45)");

            //Recupero i dati dal DB e preparo l'HTML da mostrare
            Cursor c = database.rawQuery("SELECT * FROM users", null);

            int nameIndex = c.getColumnIndex("name");
            int ageIndex = c.getColumnIndex("age");

            //c.moveToFirst();

            String html = "<html><head><title></title></head><body><table>";
            html += "<tr><td>Name</td><td>Age</td></tr>";

            while (c.moveToNext())
            {
                String nome = c.getString(nameIndex);
                int age = c.getInt(ageIndex);

                Log.i("Result: ", nome + "  " + age);

                html += "<tr><td>" + nome + "</td><td>" + age + "</td></tr>";
            }

            c.close();

            html += "</table></body></html>";

            Log.i("Html", html);

            /*
                Riempio la WebView
             */

            WebView webView = (WebView) findViewById(R.id.webView);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebChromeClient(new WebChromeClient());

            //Se volessi caricare da un URL
            //webView.loadUrl("http://www.google.it");

            //Se invece voglio caricare direttamente un html
            webView.loadData(html, "text/html", "UTF-8");

        }catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this, "Errore: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }
}
