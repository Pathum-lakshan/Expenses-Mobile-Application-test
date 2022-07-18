package com.example.first;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import com.example.first.util.DbBitmapUtility;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DashBoard extends AppCompatActivity {
    ActivityResultLauncher<Intent> activityResultLauncher;
    private Bitmap customer_photo;
    private Spinner spinner;
    private String gender;
    private TextInputEditText address,email,contact,name;
    private RadioButton male,feMale;
    private Button saveCustomer,UpdateCustomer,deleteCustomer;
    private ArrayList<Customers> AllCustomers;
    private ImageView addCustomer,addOrTakeImage;
    DBHandler dbHandler ;
    private ArrayList<Customers> customers = new ArrayList<>();
    private ImageView loadImage;
    private Customers availableNowCustomer;
    private RadioGroup radioGroup;
    private LocationManager locationManager;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        customers= new ArrayList<>();
        dbHandler = new DBHandler(DashBoard.this);
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                if (result.getResultCode() == RESULT_OK && result.getData() != null){
                    Bundle bundle = result.getData().getExtras();
                    customer_photo = (Bitmap) bundle.get("data");
                    loadImage.setImageBitmap(customer_photo);
                }
            }
        });


        /*drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        actionBarDrawerToggle= new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        */










       getLocationPermission();


        loadImage=findViewById(R.id.load_image);
        radioGroup=findViewById(R.id.genderRadioGroup);
        name=findViewById(R.id.customer_Name);
        spinner= findViewById(R.id.customers_spinner);
        address= findViewById(R.id.customer_Address);
        email= findViewById(R.id.customer_Email);
        contact= findViewById(R.id.customer_Contact);
        male= findViewById(R.id.radio_male);
        feMale= findViewById(R.id.radio_female);
        saveCustomer=findViewById(R.id.save_customer);
        UpdateCustomer=findViewById(R.id.delete_customer);
        deleteCustomer=findViewById(R.id.update_customer);

        male.setOnClickListener(view -> {


            gender="Male";

        });

        feMale.setOnClickListener(view -> {

            gender="FeMale";

        });

        addCustomer = findViewById(R.id.add_image);
        addOrTakeImage = findViewById(R.id.add_phto_or_take_image);
        saveCustomer.setEnabled(false);






        saveCustomer.setOnClickListener(view -> {


            saveCustomer.setEnabled(false);
            UpdateCustomer.setEnabled(true);
            deleteCustomer.setEnabled(true);


            byte[] bytes = new byte[0];
            if (customer_photo!=null){
                        bytes    = DbBitmapUtility.getBytes(customer_photo);

            }


            dbHandler.setCustomerDetail(new Customers( name.getText().toString(),address.getText().toString(),
                    email.getText().toString(),contact.getText().toString(),gender,bytes,saveToInternalStorage(customer_photo, name.getText().toString())
                    ));

            saveToInternalStorage(customer_photo,name.getText().toString());

            getData();

            allFieldsClear();
            Toast.makeText(DashBoard.this, "Customer Saved", Toast.LENGTH_SHORT).show();
        });
        addOrTakeImage.setOnClickListener(view -> {
            takeImage();
        });

        UpdateCustomer.setOnClickListener(view -> {

            Customers update = new Customers();

            update=availableNowCustomer;
            byte[] bytes = new byte[0];
            if (customer_photo!=null){
                bytes    = DbBitmapUtility.getBytes(customer_photo);

            }

            update.setAddress(address.getText().toString());
            update.setName(name.getText().toString());
            update.setEmail(email.getText().toString());
            update.setContact(contact.getText().toString());
            update.setGender(gender);
            update.setPhto(bytes);
            update.setPath(saveToInternalStorage(customer_photo, name.getText().toString()));




            if (dbHandler.updateCustomer(update)){
                Toast.makeText(DashBoard.this, "Customer Updated", Toast.LENGTH_SHORT).show();



                allFieldsClear();
            }else {

                Toast.makeText(DashBoard.this, "Customer Not Updated", Toast.LENGTH_SHORT).show();
            }

getData();

            saveCustomer.setEnabled(false);
            UpdateCustomer.setEnabled(true);
            deleteCustomer.setEnabled(true);
        });





        deleteCustomer.setOnClickListener(view -> {


            if (dbHandler.deleteCustomer(availableNowCustomer.getId())){

                Toast.makeText(DashBoard.this, "Deleted Customer", Toast.LENGTH_SHORT).show();

                allFieldsClear();
                getData();
            }else {

                Toast.makeText(DashBoard.this, "Non Deleted Customer", Toast.LENGTH_SHORT).show();
            }
            saveCustomer.setEnabled(false);
            UpdateCustomer.setEnabled(true);
            deleteCustomer.setEnabled(true);
        });
        addCustomer.setOnClickListener(view -> {
            allFieldsClear();
            saveCustomer.setEnabled(true);
            UpdateCustomer.setEnabled(false);
            deleteCustomer.setEnabled(false);
        });
        getData();
        getCurrentLocation();
    }

    private void getLocationPermission() {
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

  private void getCurrentLocation() {
            getGps getGps = new getGps(DashBoard.this);
                double latitude = getGps.getLatitude();
                double longitude = getGps.getLongitude();
                System.out.println(latitude+"hgggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg");
                System.out.println(longitude+"LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLllllllllllllllllllllllllllllllLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");

    }

    private boolean getGpsStatus() {
      LocationManager  locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

      if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
          return true;
      }else {
          return false;
      }
    }

    private String saveToInternalStorage(Bitmap bitmapImage,String name){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("customer_images", Context.MODE_PRIVATE);
        File mypath=new File(directory,""+name+".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add:
                addCustomer();

                return true;
            case R.id.update:
                updateCustomer();

                return true;
            case R.id.delete:
                deleteCustomer();

                return true;
            case R.id.search:
                searchCustomer();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void takeImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        activityResultLauncher.launch(takePictureIntent);

    }
    private void searchCustomer() {
        System.out.println("searchCustomer");
    }

    private void deleteCustomer() {
        System.out.println("deleteCustomer");
    }

    private void updateCustomer() {
        System.out.println("updateCustomer");
    }

    private void addCustomer() {
        System.out.println("addCustomer");
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
                    RequestQueue queue = Volley.newRequestQueue(DashBoard.this);

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonObject = new JSONArray(response);






                                for (int i = 0; i < jsonObject.length(); i++) {

                                    JSONObject objec = jsonObject.getJSONObject(i);



                                    Customers customer = new Customers(objec.getString("name"),null, objec.getString("email"),objec.getString("phone"));

                                    customers.add(customer);

                                    dbHandler.setCustomerDetail(customer);

                                }
                                loadSpinner(dbHandler.getAllCustomer());


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // method to handle errors.

                            Toast.makeText(DashBoard.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();

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

           loadSpinner(dbHandler.getAllCustomer());


        }


    }

    private void loadSpinner(ArrayList<Customers> allCustomers) {
        AllCustomers = allCustomers;

       SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this,allCustomers);

       spinner.setAdapter(spinnerAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Customers itemAtPosition = (Customers) parent.getItemAtPosition(position);
                availableNowCustomer = itemAtPosition;
                loadSpinersDataToFieald(itemAtPosition);
                System.out.println(itemAtPosition.getPath());

            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
                allFieldsClear();
            }
        });

    }

    private void loadSpinersDataToFieald(Customers spinnerCustomer) {

        name.setText(spinnerCustomer.getName());
        address.setText(spinnerCustomer.getAddress());
        email.setText(spinnerCustomer.getEmail());
        contact.setText(spinnerCustomer.getContact());

        customer_photo=DbBitmapUtility.getImage(spinnerCustomer.getPhto());
        loadImage.setImageBitmap(customer_photo);
        if ( spinnerCustomer.getGender()!=null){
            if (spinnerCustomer.getGender()=="Male" || spinnerCustomer.getGender()=="male" ){
                male.setChecked(true);
            }else {
                feMale.setChecked(true);
            }

        }else {
            male.setChecked(true);
        }



    }


    private void allFieldsClear() {
        name.setText("");
        address.setText("");
        email.setText("");
        contact.setText("");
        male.setChecked(false);
        feMale.setChecked(false);
        customer_photo = null;
        loadImage.setImageBitmap(customer_photo);
    }
}

class SpinnerAdapter extends BaseAdapter{

    private ArrayList<Customers> customers;
    private LayoutInflater inflater;

    SpinnerAdapter (Context context,ArrayList<Customers> customers){
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

     Holders holder = null;


        if (view == null){
            holder = new Holders();
            view = inflater.inflate(R.layout.spinner_text_view, null);

            holder.name = view.findViewById(R.id.name_text_view);
            holder.id = view.findViewById(R.id.id_text_view);
            holder.image = view.findViewById(R.id.customer_image_view);
        }else {
            holder=(Holders) view.getTag();
        }

        Customers customer = getItem(i);

        holder.name.setText(customer.getName());
        holder.id.setText(String.valueOf(customer.getId()));
        holder.image.setImageBitmap(DbBitmapUtility.getImage(customer.getPhto()));

        view.setTag(holder);
        return view;

    }
}
class Holders{
    TextView name;
    TextView id;
    ImageView image;

}
class getGps extends Service implements LocationListener {
    private Context mContext;

    boolean isGPSEnabled = false;

    boolean isNetworkEnabled = false;

    boolean canGetLocation = false;

    Location location;
    double latitude;
    double longitude;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    protected LocationManager locationManager;

    public getGps(DashBoard dashBoard) {
        this.mContext = dashBoard;
        getLocation();
    }

    public void GpsTracker(Context context) {
        this.mContext = context;
        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    //check the network permission
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) mContext, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                    }
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }

                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        //check the network permission
                        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions((Activity) mContext, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                        }
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }

        return latitude;
    }

    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }

        return longitude;
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}