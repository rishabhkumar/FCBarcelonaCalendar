package com.example.rishabh.fcbarcelonacalendar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView t1, t2;
    static String schedule[];
    Button b1, b2;
    int counter = 0;

    protected static void allSchedule(String str[])
    {
        schedule = str;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = (TextView) findViewById(R.id.textView3);
        ImageView i = (ImageView) findViewById(R.id.imageView);
        i.setImageResource(R.drawable.fcb);
        t2 = (TextView) findViewById(R.id.textView4);
        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        new Work().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(this, About.class);
        startActivity(i);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v == b1 && counter!=(schedule.length-1))
        {
            counter++;
            t1.setText("Next Match: " + schedule[counter]);
        }
        if(v == b2 && counter != 0)
        {
            counter--;
            t1.setText("Next Match: " + schedule[counter]);
        }
    }

    private class Work extends AsyncTask<Void, Void, Void>{

        Document document;
        String url = "http://www.goal.com/en-india/fixtures/team/barcelona/2017";
        String schedule[];
        Elements elements;
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Fetching fixtures. . .");
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                document = Jsoup.connect(url).get();
                elements = document.select(".match-table");
                schedule = elements.text().split("ODDS");
                MainActivity.allSchedule(schedule);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pd.setMessage("Data fetched. Rearranging. . .");
            String text = "Next Match: " + schedule[0];
            try {
                t1.setText(text);
                t2.setText(schedule.length + " Matches Remaining");
            }
            catch (Exception eee)
            {
                Toast.makeText(MainActivity.this, eee.toString(), Toast.LENGTH_SHORT).show();
            }
            pd.dismiss();
        }
    }
}
