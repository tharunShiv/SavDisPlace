package soon.domain.one.savdisplace;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    LocationManager locationManager;
    LocationListener locationListener;

    // creating a method that will set the position and title
    public void centerMapOnLocation(Location location, String title) {

        // If Location is Null, then it makes the App crash
        // on emulators, if we dont send the location manually,
        // it crashes
        if (location != null) {
            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
            Log.i("centerMapOnLocation", "Inside");
            // clear Maps
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(userLocation).title(title));
            // 10 is the zoom between 1 and 20, 20 being the most zoomed
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
        }
    }

    // if someone has provided yes or no answer to that question
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // checking which request code we are working with
        if(requestCode ==1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 10, locationListener);
                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    mMap.clear();
    //                // Add a marker in Sydney and move the camera
    //                LatLng userLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
    //                mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
    //                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
                    centerMapOnLocation(lastKnownLocation, "Your Last Known Location");

                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Intent intent = getIntent();
        //Integer location = intent.getIntExtra("placesNumber", 0);
        //Toast.makeText(getApplicationContext(), Integer.toString(location), Toast.LENGTH_SHORT).show();
        // we want all of this code here, because
        // we want to get the location only after the map is ready
        if (intent.getIntExtra("placesNumber", -1) == -1) {
            // come through button
            Toast.makeText(getApplicationContext(), "Inside", Toast.LENGTH_SHORT).show();
            locationManager =(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    centerMapOnLocation(location, "Your Location");

                    // getting the address
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    try {
                        // get the top result
                        List<Address> listAddresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        // check if the address is returned
                        if (listAddresses != null && listAddresses.size() > 0) {
                            String address = "";
                            // we have to now work with lot of sub parts to fill this up
    //                      // feature -> number // similarly we can access other methods or just one method like getAddressLine()
    //                      if (listAddresses.get(0).getFeatureName().toString() != null){
    //                            address += listAddresses.get(0).getFeatureName().toString();
    //                      }
                            // address line , containing the address
                            if (listAddresses.get(0).getAddressLine(0) != null) {
                                address += listAddresses.get(0).getAddressLine(0);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }

            };
        } else {
            // come here through click on the list item

            Integer place = intent.getIntExtra("placesNumber", -1);

        }





        // check whether the user has given us permission
        // if the user is not using minimum Marshmellow
        if (Build.VERSION.SDK_INT < 23) {
            // no need to ask for permission
            // we can directly fetch stuff

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, locationListener);
        } else {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                // we didnt get the permission
                // requesting it
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
//                // we got the permission
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 10, locationListener);
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                mMap.clear();
//                // Add a marker in Sydney and move the camera
//                LatLng userLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
//                mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
                centerMapOnLocation(lastKnownLocation, "Your Last Known Location");

                }
            }
    }
}
