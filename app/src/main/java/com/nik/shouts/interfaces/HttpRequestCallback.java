package com.nik.shouts.interfaces;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.nik.shouts.utils.MapUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class HttpRequestCallback extends AsyncTask<String, Void, String>
{
    private String requestType = "";
    private RequestCallback requestCallback;
    private String postJsonObj;

    public HttpRequestCallback(RequestCallback requestCallback, String requestType) {
        this.requestCallback = requestCallback;
        this.requestType = requestType;
    }

    public HttpRequestCallback(RequestCallback requestCallback, String requestType, String postJsonObj) {
        this.requestCallback = requestCallback;
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
            String inputLine = "";
            // connection for http request
            HttpURLConnection connection = null;
            // buffer reader to read output from server
            BufferedReader br = null;

            switch (requestType) {
                case "GET RAW" :
                    connection =  (HttpURLConnection)url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                    Bitmap bitmap = BitmapFactory.decodeStream(bufferedInputStream);
                    result = MapUtils.bitmapToString(bitmap);
                    break;
                case "GET":
                    connection =  (HttpURLConnection)url.openConnection();
                    br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

//                  buffer json data here
                    while ((inputLine = br.readLine()) != null) {
                        System.out.println("line " + inputLine);
                        result += inputLine;
                    }
                    br.close();
                    connection.disconnect();
                    break;
                case "POST":
                    connection =  (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("POST");
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
                    // print response status code
//                    if(connection.getResponseCode() == 200)
//                        return Configurations.SUCCESS_STATUS_CODE;

                    // debugging output
                     br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    // buffer the response from server side
                     while ((inputLine = br.readLine()) != null) {
                        System.out.println(inputLine);
                        result += inputLine;
                    }

                    br.close();
                    connection.disconnect();
                    break;
            }
        }
        catch (MalformedURLException e){
                e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        } finally {

        }
        return result;
    }

    @Override
    protected void onPostExecute(String result)
    {
        System.out.println("RESULT API " + result);
        requestCallback.onRequestComplete(result);
    }
}