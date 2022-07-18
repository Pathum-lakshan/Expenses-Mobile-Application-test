package com.example.first;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.first.db.DBHandler;
import com.example.first.model.Customers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DBHandler dbHandler ;
    ListView listView;
    private ArrayList<Customers> customers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list_view);
        dbHandler = new DBHandler(MainActivity.this);
        getData();


    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


    public boolean isInternetAvailable() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            connected = true;
        }
        else{
            connected = false;}

        return connected;
    }
    private void getData() {
        if (isInternetAvailable()&& dbHandler.getRowCount()==0){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // url to post our data
                    String url = "https://jsonplaceholder.typicode.com/users";

                    // creating a new variable for our request queue
                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonObject = new JSONArray(response);






                                for (int i = 0; i < jsonObject.length(); i++) {

                                    JSONObject objec = jsonObject.getJSONObject(i);

                                    Customers customer = new Customers(objec.getString("name"), (String) objec.getJSONArray("address").get(1), objec.getString("email"),objec.getString("phone"));

                                    System.out.println(customer);
                                    customers.add(customer);

                                   dbHandler.setCustomerDetail(customer);

                                }

                                loadListView(customers);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(MainActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // method to handle errors.

                            Toast.makeText(MainActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();

                            new Thread(new Runnable() {
                                @Override
                                public void run() {


                                }
                            }).start();
                        }
                    });

                    queue.add(request);
                }
            }).start();
        }else {


            loadListView(dbHandler.getAllCustomer());

        }


    }

    private void loadListView(ArrayList<Customers> arrayList) {

      TestAdapter testAdapter = new TestAdapter(this,arrayList);

        listView.setAdapter(testAdapter);
    }
}


class TestAdapter extends BaseAdapter{

    private ArrayList<Customers> customers;
    private LayoutInflater inflater;

    TestAdapter(Context context,ArrayList<Customers> customers){

        inflater= (LayoutInflater) context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));

        this.customers=customers;

    }

    @Override
    public int getCount() {
        return customers.size();
    }

    @Override
    public Customers getItem(int i) {
        return customers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Holder holder = null;

        if (view == null){
            holder = new Holder();
            view = inflater.inflate(R.layout.customer_view, null);
            holder.contact = view.findViewById(R.id.contact);
            holder.name = view.findViewById(R.id.name);
            holder.email = view.findViewById(R.id.email);
        }else {
            holder=(Holder) view.getTag();
        }

        Customers customer = getItem(i);

        holder.contact.setText(customer.getContact());
        holder.email.setText(customer.getEmail());
        holder.name.setText(customer.getName());

        view.setTag(holder);
        return view;
    }
   public class Holder{
        TextView name;
        TextView contact;
        TextView email;

    }
}