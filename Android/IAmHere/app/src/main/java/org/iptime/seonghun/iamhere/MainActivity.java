package org.iptime.seonghun.iamhere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

//https://developers.google.com/maps/documentation/android-api/
public class MainActivity extends AppCompatActivity {

    static final LatLng SEOUL = new LatLng( 37.56, 126.97);
    private GoogleMap map;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        marker = map.addMarker(new MarkerOptions().position(SEOUL).title("I Am Here"));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 15));
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // GPS 프로바이더 사용가능여부
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 네트워크 프로바이더 사용가능여부
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Log.d("Main", "isGPSEnabled=" + isGPSEnabled);
        Log.d("Main", "isNetworkEnabled=" + isNetworkEnabled);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                double lat = location.getLatitude();
                double lng = location.getLongitude();

                LatLng curPos = new LatLng(lat, lng);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(curPos, 15));
                marker.setPosition(curPos);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        // Register the listener with the Location Manager to receive location update
        if (isGPSEnabled)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
        if (isNetworkEnabled)
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);

        // 수동으로 위치 구하기
        String locationProvider = LocationManager.GPS_PROVIDER;
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);

        if (lastKnownLocation != null) {
            double lng = lastKnownLocation.getLatitude();
            double lat = lastKnownLocation.getLatitude();
            Log.d("Main", "longtitude=" + lng + ", latitude=" + lat);
        } else {
            Log.d("Main", "lastKnowLocation is null");
        }
    }
}