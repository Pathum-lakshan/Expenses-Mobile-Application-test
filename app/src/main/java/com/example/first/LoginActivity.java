package com.example.first;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.first.controller.BaseController;
import com.example.first.model.CustomNameValuePair;
import com.example.first.model.User;
import com.example.first.util.SharedPref;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private JSONArray respObj;
    private TextInputEditText textUsername,textPassword;
    private Button btnSignInOrRegister;
    private TextView signUp;
    private SharedPref sharedPref;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textUsername=findViewById(R.id.textUsername);
        textPassword=findViewById(R.id.textPassword);
        btnSignInOrRegister=findViewById(R.id.btnSignIn);
        signUp=findViewById(R.id.signUp);
        btnSignInOrRegister.setOnClickListener(view -> {
            try {
                signIn();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void signIn() throws IOException {

/*

        if (textUsername.getText().equals("") || textPassword.getText().equals("")) {

        } else {

            new Thread(new Runnable() {
                @Override
                public void run() {


                    try {
                        ArrayList<CustomNameValuePair> customNameValuePairs = new ArrayList<>();
                        customNameValuePairs.add(new CustomNameValuePair("username", textUsername.getText().toString()));
                        customNameValuePairs.add(new CustomNameValuePair("name", textPassword.getText().toString()));


                        String response = BaseController.postToServerGzip("https://jsonplaceholder.typicode.com/users", customNameValuePairs);


                        postDataUsingVolley();

                        JSONObject jsonObject = new JSONObject(response);

                        if (jsonObject.getBoolean("result"))
                        {


                            User user = User.getUserFromJson(jsonObject);

                            if (user != null)
                            {
                                sharedPref.saveUser(user);

                            }

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(intent);

                                }
                            });

                        }



                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
*/
        Intent intent = new Intent(LoginActivity.this,DashBoard.class);
        startActivity(intent);
    }

/*
    private void postDataUsingVolley() {
        // url to post our data
        String url = "https://jsonplaceholder.typicode.com/users";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);

        StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    respObj = new JSONArray(response);

                    System.out.println(respObj);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(LoginActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                System.out.println(error);
                Toast.makeText(LoginActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();



                return params;
            }
        };

        queue.add(request);

    }*/
/*
    public static JSONObject getJsonObject(String url, HashMap<String, String> params) {

        StringBuilder sbParams = new StringBuilder();
        StringBuilder result = new StringBuilder();
        String charset = "UTF-8";
        HttpURLConnection conn = null;
        JSONObject jObj = null;
        URL urlObj = null;
        DataOutputStream wr = null;

        int i = 0;
        for (String key : params.keySet()) {
            try {
                if (i != 0) {
                    sbParams.append("&");
                }
                sbParams.append(key).append("=")
                        .append(URLEncoder.encode(params.get(key), charset));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i++;
        }

        Log.d("HTTP Request", "params: " + sbParams.toString());

        try {
            urlObj = new URL(url);

            conn = (HttpURLConnection) urlObj.openConnection();

            conn.setDoOutput(true);

            conn.setRequestMethod("POST");

            conn.setRequestProperty("Accept-Charset", charset);

            conn.setReadTimeout(600000);
            conn.setConnectTimeout(600000);

            conn.connect();

            String paramsString = sbParams.toString();

            wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(paramsString);
            wr.flush();
            wr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            InputStream inn = null;
            try {
                inn = conn.getInputStream();
            } catch (Exception e) {
                Log.wtf("Error", "server not found" + e.toString());
            }
            if (inn != null) {
                InputStream in = new BufferedInputStream(inn);
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            } else {

                try {
                    String line = "{ \"response\" : -2 }";

                    result.append(line);


                } catch (Exception e) {

                }


            }

            Log.d("HTTP Request", "result: " + result.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        conn.disconnect();

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(result.toString());
        } catch (JSONException e) {
            Log.e("HTTP Request", "Error parsing data " + e.toString());
            Log.w("HTTP Response", result.toString() + "");
        }

        // return JSON Object
        return jObj;
    }*/
/*
    public static JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {

        HttpURLConnection urlConnection = null;

        URL url = new URL(urlString);

        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 *//* milliseconds *//*);
        urlConnection.setConnectTimeout(15000 *//* milliseconds *//*);

        urlConnection.setDoOutput(true);

        urlConnection.connect();

        BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));

        char[] buffer = new char[1024];

        String jsonString = new String();

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line+"\n");
        }
        br.close();

        jsonString = sb.toString();

        System.out.println("JSON: " + jsonString);
        urlConnection.disconnect();

        return new JSONObject(jsonString);
    }*/
}