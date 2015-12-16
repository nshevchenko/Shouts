package com.nik.shouts.interfaces;


import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.nik.shouts.interfaces.ApiRequestCallback;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class HttpRequestCallback extends AsyncTask<String, Void, String>
{
    private String requestType = "";
    private ApiRequestCallback apiRequestCallback;
    private JSONObject postJsonObj;

    public HttpRequestCallback(ApiRequestCallback apiRequestCallback, String requestType) {
        this.apiRequestCallback = apiRequestCallback;
        this.requestType = requestType;
    }

    public HttpRequestCallback(ApiRequestCallback apiRequestCallback, String requestType, JSONObject postJsonObj) {
        this.apiRequestCallback = apiRequestCallback;
        this.requestType = requestType;
        this.postJsonObj = postJsonObj;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... urls)
    {
        String result = "";

        try {
            URL url = new URL(urls[0]);

            switch (requestType) {
                case "GET":
                    // enter your url here which to download
                    URLConnection conn = url.openConnection();
//                    // open the stream and put it into BufferedReader
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine = "";
                    while ((inputLine = br.readLine()) != null) {
                        System.out.println(inputLine);
                        result += inputLine;
                    }
                    br.close();
                    break;
                case "POST":
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("parametros", postJsonObj.toString());

                    String query = builder.build().getEncodedQuery();
                    connection.setFixedLengthStreamingMode(query.getBytes().length);

                    OutputStream os = connection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(query);
                    writer.flush();
                    writer.close();
                    os.close();

                    connection.connect();

                    break;
            }
        }
        catch (MalformedURLException e){
                e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result)
    {
        System.out.println("RESULT API " + result);
        apiRequestCallback.onRequestComplete(result);
    }
}