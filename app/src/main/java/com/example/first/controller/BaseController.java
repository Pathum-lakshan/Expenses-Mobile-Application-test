package com.example.newexpenseapplication.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.newexpenseapplication.model.CustomNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.zip.GZIPInputStream;

public class BaseController {




    private static final String LOG_TAG = BaseController.class.getSimpleName();
    //public static String domain = "http://192.168.0.168:8000";

     public static String domain = "http://nbc3.salespad.lk/nbc3test";

  //  public static String domain = "http://nbc3.salespad.lk/nbc3";
    public static String baseURL = domain + "/api/sales/v1/";


    public static String postToServerGzip(String url, List<CustomNameValuePair> params) throws IOException {
        Log.d("<> URL <>", url);
        String response = "";

        URL postURL = new URL(url);
        HttpURLConnection con = (HttpURLConnection) postURL.openConnection();
        con.setRequestProperty("Accept-Encoding", "gzip");
        con.setConnectTimeout(100 * 1000);
        con.setReadTimeout(300 * 1000);
        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);

        OutputStream os = con.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(generatePOSTParams(params));
        writer.flush();
        writer.close();
        os.close();

        con.connect();

        int status = con.getResponseCode();
        switch (status) {
            case 200:
            case 201:

                Reader reader = null;
                if ("gzip".equals(con.getContentEncoding())) {
                    reader = new InputStreamReader(new GZIPInputStream(con.getInputStream()));

                } else {
                    reader = new InputStreamReader(con.getInputStream());
                }

                Log.e("type", con.getContentEncoding() + "");

                BufferedReader br = new BufferedReader(reader);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();

                response = sb.toString();
                Log.e(LOG_TAG, "Server Response : \n" + response);
        }
        Log.e("<><><>", response);

        return response;
    }

    private static String generatePOSTParams(List<CustomNameValuePair> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (CustomNameValuePair pair : params) {
            if (pair != null) {
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
            }
        }

        Log.d(LOG_TAG, "Server REQUEST : " + result.toString());
        Log.d(LOG_TAG, "Upload size : " + result.toString().getBytes().length + " bytes");

        return result.toString();
    }





    public static String postToServerGzipWithToken(String token, String url, List<CustomNameValuePair> params) throws IOException {
        Log.d("<> URL <>", url);
        String response = "";

        URL postURL = new URL(url);
        HttpURLConnection con = (HttpURLConnection) postURL.openConnection();
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("ContentType", "application/json");
        con.setRequestProperty("Authorization", "Bearer " + token);
        con.setRequestProperty("Accept-Encoding", "gzip");
        con.setConnectTimeout(20 * 1000);
        con.setReadTimeout(30 * 1000);
        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);

        OutputStream os = con.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(generatePOSTParams(params));
        writer.flush();
        writer.close();
        os.close();

        con.connect();

        int status = con.getResponseCode();
        switch (status) {
            case 200:
            case 201:

                Reader reader = null;
                if ("gzip".equals(con.getContentEncoding())) {
                    reader = new InputStreamReader(new GZIPInputStream(con.getInputStream()));

                } else {
                    reader = new InputStreamReader(con.getInputStream());
                }

                Log.e("type", con.getContentEncoding() + "");

                BufferedReader br = new BufferedReader(reader);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();

                response = sb.toString();
                Log.e(LOG_TAG, "Server Response : \n" + response);
        }
        Log.e("<><><>", response);

        return response;
    }

    public static String getToServerGzipWithToken(String token, String url, List<CustomNameValuePair> params) throws IOException {
        Log.d("<> URL <>", url);
        String response = "";

        URL postURL = new URL(url);
        HttpURLConnection con = (HttpURLConnection) postURL.openConnection();
     /*   con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("ContentType", "application/json");*/
        con.setRequestProperty("Authorization", "Bearer " + token);
        con.setRequestProperty("Accept-Encoding", "gzip");
        con.setConnectTimeout(20 * 1000);
        con.setReadTimeout(30 * 1000);
        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);

        OutputStream os = con.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(generatePOSTParams(params));
        writer.flush();
        writer.close();
        os.close();

        con.connect();

        int status = con.getResponseCode();
        switch (status) {
            case 200:
            case 201:

                Reader reader = null;
                if ("gzip".equals(con.getContentEncoding())) {
                    reader = new InputStreamReader(new GZIPInputStream(con.getInputStream()));

                } else {
                    reader = new InputStreamReader(con.getInputStream());
                }

                Log.e("type", con.getContentEncoding() + "");

                BufferedReader br = new BufferedReader(reader);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();

                response = sb.toString();
                Log.e(LOG_TAG, "Server Response : \n" + response);
        }
        Log.e("<><><>", response);

        return response;
    }

}
