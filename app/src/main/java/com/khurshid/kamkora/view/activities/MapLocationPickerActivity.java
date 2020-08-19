package com.khurshid.kamkora.view.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.khurshid.kamkora.R;

import java.util.Arrays;
import java.util.List;

public class MapLocationPickerActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String MYTAG = MapLocationPickerActivity.class.getSimpleName();
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int M_MAX_ENTRIES = 5;
    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    ListView lstPlaces;
    private GoogleMap mMap;
    private PlacesClient mPlacesClient;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;
    private boolean mLocationPermissionGranted;
    //Used for selecting the final place
    private String[] mLikelyPlaceNames;
    private String[] mLikelyPlaceAddresses;
    private String[] mLikelyPlaceAttributions;
    private LatLng[] mLikelyPlaceLatLngs;
    private Context context;
    private Activity activity;

    private AdapterView.OnItemClickListener listClickedHandler =
            (parent, view, position, id) -> {
                LatLng markerLatLng = mLikelyPlaceLatLngs[position];
                String markerSnippet = mLikelyPlaceAddresses[position];
                if (mLikelyPlaceAttributions[position] != null) {
                    markerSnippet = markerSnippet + "\n" + mLikelyPlaceAttributions[position];
                }

                // Add a marker for the selected place, with an info window
                // showing information about that place.
                mMap.addMarker(new MarkerOptions()
                        .title(mLikelyPlaceNames[position])
                        .position(markerLatLng)
                        .snippet(markerSnippet));


                // Position the map's camera at the location of the marker.
                mMap.moveCamera(CameraUpdateFactory.newLatLng(markerLatLng));
                Intent intent = new Intent();
                intent.putExtra("getLocation", mLikelyPlaceAddresses[position]);
                activity.setResult(RESULT_OK, intent);
                activity.finish();
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        activity = this;
        context = this;

        initView();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

//        Intent intent = new Intent();
//        intent.putExtra("getLocation", "hello world");
//        setResult(RESULT_OK, intent);
    }

    private void initView() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Set up the action toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up the views
        lstPlaces = findViewById(R.id.lvListPlaces);

        // Initialize the Places client
//        String apiKey = getString(R.string.google_maps_key);
        String apiKey = "AIzaSyArEqk_s3JVN9NfHIlvx2rkpPCs8lnKxJA";
        Places.initialize(getApplicationContext(), apiKey);
        mPlacesClient = Places.createClient(this);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng guwahati = new LatLng(26.1445, 91.7362);
        mMap.addMarker(new MarkerOptions().position(guwahati).title("Marker in Guwahati"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(guwahati, DEFAULT_ZOOM));

        // Enable the zoom controls for the map
        mMap.getUiSettings().setZoomControlsEnabled(true);

        getLocationPermission();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.location_picker_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_geolocate:
                pickCurrentPlace();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void pickCurrentPlace() {
        if (mMap == null)
            return;

        if (mLocationPermissionGranted) {
            getDeviceLocation();
        } else {
            // The user has not granted permission.
            Log.i(MYTAG, "The user did not grant location permission.");

            // Add a default marker, because the user hasn't selected a place.
            mMap.addMarker(new MarkerOptions()
                    .title(getString(R.string.default_info_title))
                    .position(mDefaultLocation)
                    .snippet(getString(R.string.default_info_snippet)));

            // Prompt the user for permission.
            getLocationPermission();
        }
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        mLocationPermissionGranted = false;
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void fillPlacesList() {
        Log.d(MYTAG, "fillPlacesList() called");
        ArrayAdapter<String> placesAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mLikelyPlaceNames);
        lstPlaces.setAdapter(placesAdapter);
        lstPlaces.setOnItemClickListener(listClickedHandler);
    }

    private void getCurrentPlaceLikelihoods() {
        // Use fields to define the data types to return.
        List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);

        // Get the likely places - that is, the businesses and other points of interest that
        // are the best match for the device's current location.
        @SuppressWarnings("MissingPermission") final FindCurrentPlaceRequest request =
                FindCurrentPlaceRequest
                        .builder(placeFields)
                        .build();
        @SuppressLint("MissingPermission") Task<FindCurrentPlaceResponse> placeResponse =
                mPlacesClient.findCurrentPlace(request);

        placeResponse.addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                FindCurrentPlaceResponse response = task.getResult();

                // Set the count, handling cases where less than 5 entries are returned.
                int count;
                if (response.getPlaceLikelihoods().size() < M_MAX_ENTRIES) {
                    count = response.getPlaceLikelihoods().size();
                } else {
                    count = M_MAX_ENTRIES;
                }

                int i = 0;
                mLikelyPlaceNames = new String[count];
                mLikelyPlaceAddresses = new String[count];
                mLikelyPlaceAttributions = new String[count];
                mLikelyPlaceLatLngs = new LatLng[count];

                for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
                    Place currentPlace = placeLikelihood.getPlace();
                    mLikelyPlaceNames[i] = currentPlace.getName();
                    mLikelyPlaceAddresses[i] = currentPlace.getAddress();
                    mLikelyPlaceAttributions[i] =
                            (currentPlace.getAttributions() == null) ?
                                    null :
                                    TextUtils.join(" ", currentPlace.getAttributions());
                    mLikelyPlaceLatLngs[i] = currentPlace.getLatLng();

                    String currentLatLng = (mLikelyPlaceLatLngs[i] == null) ?
                            "" : mLikelyPlaceLatLngs[i].toString();


                    Log.i(MYTAG, String.format("Place " + currentPlace.getName()
                            + " has likelihood: " + placeLikelihood.getLikelihood()
                            + " at " + currentLatLng));

                    i++;
                    if (i > (count - 1)) {
                        break;
                    }
                }

                fillPlacesList();

            } else {
                Exception exception = task.getException();
                if (exception instanceof ApiException) {
                    ApiException apiException = (ApiException) exception;
                    Log.e(MYTAG, "Place not found: " + apiException.getStatusCode());
                    Log.e(MYTAG, "Place not found exception: " + exception.getMessage());

                }
            }
        });


    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */

        try {
            if (mLocationPermissionGranted) {
                @SuppressLint("MissingPermission")
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        mLastKnownLocation = task.getResult();
                        Log.d(MYTAG, "Latitude: " + mLastKnownLocation.getLatitude());
                        Log.d(MYTAG, "Longitude: " + mLastKnownLocation.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(mLastKnownLocation.getLatitude(),
                                        mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        mMap.setMyLocationEnabled(true);
                    } else {
                        Log.d(MYTAG, "Current location is null. Using defaults.");
                        Log.e(MYTAG, "Exception: %s", task.getException());
                        mMap.moveCamera(CameraUpdateFactory
                                .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                    }
                    getCurrentPlaceLikelihoods();
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }

    }

    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }
}