package com.example.first;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private JSONObject respObj;
    private TextInputEditText textUsername,textPassword;
    private Button btnSignInOrRegister;
    private TextView signUp;

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


        postDataUsingVolley("Janppppppppppt", "lakshppppppppppanherath11@gmail.com");
    }


    private void postDataUsingVolley(String name, String job) {
        // url to post our data
        String url = "https://reqres.in/api/users/2";


        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);

        StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    respObj = new JSONObject(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(LoginActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(LoginActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();


                params.put("name", name);
                params.put("job", job);
                return params;
            }
        };

        queue.add(request);

        System.out.println(respObj);
    }
}