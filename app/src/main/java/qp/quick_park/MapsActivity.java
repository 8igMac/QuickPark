package qp.quick_park;

import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

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
        LocationManager mgr;
        mgr = (LocationManager)getSystemService(LOCATION_SERVICE);


        // Set current location on start
        Criteria cri = new Criteria();
        String bbb = mgr.getBestProvider(cri, true);
        Location myLocation = mgr.getLastKnownLocation(bbb);

        double lat = myLocation.getLatitude();
        double lon = myLocation.getLongitude();
        LatLng ll = new LatLng(lat, lon);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll,14));


        // Show Current Location
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setMyLocationButtonEnabled(true);
        mMap.setMyLocationEnabled(true);
    }

    // Search button method
    public void onSearch(View view)
    {
        EditText location_tf = (EditText)findViewById(R.id.tf_address);
        String location = location_tf.getText().toString();
        List<Address> addressList = null;

        if(location != null || !location.equals(""))
        {
            Geocoder geocoder = new Geocoder(this);
            try {

                // store address in the list
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // get input address Lat and Lng
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

            // add marker
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            // move camera
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));

        }
    }

}
