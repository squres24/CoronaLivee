package com.example.coronaupdate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity  {

    private static final String TAG = "MainActivity";

    SearchView searchView;
    Button btn;
    TextView cases;
    TextView deaths;
    TextView critical;
    TextView recovered;
    TextView cases_total;
    TextView deaths_total;
    TextView critical_total;
    TextView recovered_total;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        searchView= findViewById(R.id.search);
        btn= findViewById(R.id.country);

        cases= findViewById(R.id.cases);
        deaths= findViewById(R.id.deaths);
        critical= findViewById(R.id.critical);
        recovered= findViewById(R.id.recovered);
        cases_total= findViewById(R.id.cases_total);
        deaths_total= findViewById(R.id.deaths_total);
        critical_total= findViewById(R.id.critical_total);
        recovered_total= findViewById(R.id.recovered_total);

        class Covid extends AsyncTask<Void, Void, Void> {



            public Covid() {
                super();

            }

            @Override
            protected Void doInBackground(Void... params) {

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://coronavirus-19-api.herokuapp.com/all")
                        .get()
                        .addHeader("x-rapidapi-host", "covid-19-data.p.rapidapi.com")
                        .addHeader("x-rapidapi-key", "8a90888ca7msh4b502ed5c7e2e05p1dca06jsnc7d7ad973eb5")
                        .build();


                try {
                    final Response response = client.newCall(request).execute();



                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                //Toast.makeText(getApplicationContext(),response.body().string(),Toast.LENGTH_LONG).show();

                                Gson gson = new Gson();
                                CovidDetails covidDetails=gson.fromJson(response.body().string(), CovidDetails.class);

                                cases_total.setText(covidDetails.getCases().toString());
                                deaths_total.setText(covidDetails.getDeaths().toString());
                                critical_total.setText("51907");
                                recovered_total.setText(covidDetails.getRecovered().toString());




                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w(TAG, "doInBackground: ", e );
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
            }

            @Override
            protected void onPreExecute() {}

            @Override
            protected void onProgressUpdate(Void... values) {}
        }

        new Covid().execute();

        class Corona extends AsyncTask<Void, Void, Void> {

            String country;


            public Corona(String temp) {
                super();
                country= temp;

            }

            @Override
            protected Void doInBackground(Void... params) {

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://coronavirus-19-api.herokuapp.com/countries/"+ country)
                        .get()
                        .addHeader("x-rapidapi-host", "covid-193.p.rapidapi.com")
                        .addHeader("x-rapidapi-key", "8a90888ca7msh4b502ed5c7e2e05p1dca06jsnc7d7ad973eb5")
                        .build();

                Log.d(TAG, "doInBackground: What the hell");

                try {
                    final Response response = client.newCall(request).execute();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {


                                Gson gson = new Gson();
                                CoronaDetails coronaDetails=gson.fromJson(response.body().string(), CoronaDetails.class);

                                cases.setText(coronaDetails.getCases().toString());
                                deaths.setText(coronaDetails.getDeaths().toString());
                                critical.setText(coronaDetails.getCritical().toString());
                                recovered.setText(coronaDetails.getRecovered().toString());




                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w(TAG, "doInBackground: ", e );
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
            }

            @Override
            protected void onPreExecute() {}

            @Override
            protected void onProgressUpdate(Void... values) {}
        }







        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: I am here at button");



                new Corona(searchView.getQuery().toString()).execute();



            }
        });










    }
}
