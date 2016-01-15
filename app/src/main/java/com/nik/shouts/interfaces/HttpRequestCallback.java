package com.nik.shouts.interfaces;


import android.net.Uri;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class HttpRequestCallback extends AsyncTask<String, Void, String>
{
    private String requestType = "";
    private ApiRequestCallback apiRequestCallback;
    private String postJsonObj;

    public HttpRequestCallback(ApiRequestCallback apiRequestCallback, String requestType) {
        this.apiRequestCallback = apiRequestCallback;
        this.requestType = requestType;
    }

    public HttpRequestCallback(ApiRequestCallback apiRequestCallback, String requestType, String postJsonObj) {
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
        BufferedReader br = null;
        try {
            URL url = new URL(urls[0]);
            String inputLine = "";
            switch (requestType) {
                case "GET":
                    // enter your url here which to download
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//                    // open the stream and put it into BufferedReader
                    br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    // buffer json data here
                    while ((inputLine = br.readLine()) != null) {
                        System.out.println("line " + inputLine);
                        result += inputLine;
                    }
                    br.close();
                    break;
                case "POST":
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    // Open an Output stream from the connection in order to buffer post payload
                    OutputStream os = connection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));

                    writer.write(URLEncoder.encode(postJsonObj, "UTF-8"));
                    writer.flush();
                    writer.close();
                    os.close();
                    connection.connect();
                    // print response status code
                    if(connection.getResponseCode() == 200){
                        System.out.println("200");
                    }
                    // buffer the response from server side
                    while ((inputLine = br.readLine()) != null) {
                        System.out.println(inputLine);
                        result += inputLine;
                    }

                    connection.disconnect();
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