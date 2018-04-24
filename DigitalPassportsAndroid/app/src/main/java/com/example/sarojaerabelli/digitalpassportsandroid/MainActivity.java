package com.example.sarojaerabelli.digitalpassportsandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void connectToWeb(View view){
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... arg0) {
                String msg = "";
                InputStream inputStream = null;

                try {

                    // 1. create HttpClient
                    HttpClient httpclient = new DefaultHttpClient();

                    // 2. make POST request to the given URL
                    HttpGet httpGet = new HttpGet("http://0.0.0.0:1025/android");

                    String json = "";

                    // 5. set json to StringEntity
                    StringEntity se = new StringEntity(json);


                    HttpParams httpParams = httpclient.getParams();
                    HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
                    HttpConnectionParams.setSoTimeout(httpParams, 5000);
                    httpGet.setParams(httpParams);

                    // 8. Execute POST request to the given URL
                    System.out.println("executing"+json);
                    HttpResponse httpResponse = httpclient.execute(httpGet);
                    // 9. receive response as inputStream
                    inputStream = httpResponse.getEntity().getContent();

                    // 10. convert inputstream to string
                    if(inputStream != null) {
                        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
                        String result = "";
                        while (s.hasNext()) {
                            result += s.next();
                        }
                        msg = result;
                    }else
                        msg = "Did not work!";

                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(msg);
                return msg;
            }
        }.execute(null, null, null);
    }
}
