package com.example.android.tflitecamerademo;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.os.*;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ExchangeRate extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_rate);
        Downloads task=new Downloads();
        task.execute("https://api.exchangeratesapi.io/latest?base=INR");

    }
    public class Downloads extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {

            String result="";
            URL url;
            HttpURLConnection urlConnection= null;


            try {
                url =new URL(urls[0]);
                try {
                    urlConnection=(HttpURLConnection) url.openConnection();

                    InputStream in=urlConnection.getInputStream();
                    InputStreamReader reader =new InputStreamReader(in);
                   int  data=reader.read();

                   while(data !=-1){
                       char c =(char) data;

                       result += c;
                       data=reader.read();

                   }
                    return  result;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            String message="";
            Log.i("website content",result);

            try {

                JSONObject arr =new JSONObject(result);
                String rate =arr.getString("rates");
                JSONObject rates=new JSONObject(rate);
                Float usd =Float.parseFloat(rates.getString("USD"));
                Float euro =Float.parseFloat(rates.getString("EUR"));
                Float php=Float.parseFloat(rates.getString("PHP"));
                Float jpy=Float.parseFloat(rates.getString("JPY"));




                Bundle extra=getIntent().getExtras();
                String s=extra.getString("result");
                CameraActivity ob =new CameraActivity();
                ob.speak2(s+"Indian Rupees");
               Float res= Float.parseFloat(s);
                TextView v1= (TextView)findViewById(R.id.rate);
                v1.setText(s+" INR");
                TextView v2= (TextView)findViewById(R.id.dollers);
                v2.setText(Float.toString(res*usd)+" USD");

                TextView v3= (TextView)findViewById(R.id.euro);
                v3.setText(Float.toString(res*euro)+" EURO");

                TextView v4= (TextView)findViewById(R.id.php);
                v4.setText(Float.toString(res*php)+" PHP");

                TextView v5= (TextView)findViewById(R.id.jpy);
                v5.setText(Float.toString(res*jpy)+" JPY");



                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent cam= new Intent(ExchangeRate.this, CameraActivity.class);
                        startActivity(cam);
                    }
                }, 5000);

                //result.putExtra("result",textToShow);




            } catch (JSONException e) {
                e.printStackTrace();
            }
//            TextView resultTextview=(TextView)findViewById(R.id.weather);
//            resultTextview.setText(result);
            Log.i("website content",result);
        }
    }



}




