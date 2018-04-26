package com.example.sarojaerabelli.digitalpassportsandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.os.AsyncTask;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
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

                    String json = "";

                    // 5. set json to StringEntity
                    StringEntity se = new StringEntity(json);

                    EditText editText = (EditText) findViewById(R.id.firstName);
                    String firstName = editText.getText().toString();
                    editText = (EditText) findViewById(R.id.lastName);
                    String lastName = editText.getText().toString();
                    editText = (EditText) findViewById(R.id.dob);
                    String dob = editText.getText().toString();
                    editText = (EditText) findViewById(R.id.dob);
                    String country = editText.getText().toString();
                    editText = (EditText) findViewById(R.id.country);
                    String address = editText.getText().toString();
                    editText = (EditText) findViewById(R.id.address);

                    HttpParams httpParams = httpclient.getParams();
                    HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
                    HttpConnectionParams.setSoTimeout(httpParams, 5000);

                    String url = "http://18.111.23.9:1025/android";
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add( new BasicNameValuePair("inputFirstName", firstName));
                    params.add( new BasicNameValuePair("inputLastName", lastName));
                    params.add( new BasicNameValuePair("inputDOB", dob));
                    params.add( new BasicNameValuePair("inputCountry", country));
                    params.add( new BasicNameValuePair("inputAddress", address));
                    URI uri = new URI(url + "?" + URLEncodedUtils.format(params, "utf-8" ));

                    // 2. make POST request to the given URL
                    //Change address to your computer's public IP address.
                    HttpGet httpGet = new HttpGet(uri);


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
