package practice.com.drone;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Timer;
import java.util.TimerTask;

import practice.com.drone.data.Channel;
import practice.com.drone.data.Drone;
import practice.com.drone.data.Item;
import practice.com.drone.service.DroneCallback;
import practice.com.drone.service.DroneService;
import practice.com.drone.service.WeatherServiceCallback;
import practice.com.drone.service.YahooWeatherService;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, WeatherServiceCallback, DroneCallback {

    private GoogleMap mMap;
    private TextView temperatureTextView, conditionTextView, locationTextView, speedTextView, visibilityTextView;
    private YahooWeatherService weatherService;
    private DroneService droneService;
    private ProgressDialog dialog;
    private LatLng initialPos = new LatLng(0, 0);
    private Bitmap droneIcon;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        temperatureTextView = (TextView) findViewById(R.id.temperature);
        conditionTextView = (TextView) findViewById(R.id.condition);
        locationTextView = (TextView) findViewById(R.id.location);
        speedTextView = (TextView) findViewById(R.id.speed);
        visibilityTextView = (TextView) findViewById(R.id.visibility);

        droneIcon = BitmapFactory.decodeResource(MapsActivity.this.getResources(),
                R.drawable.drone);

        weatherService = new YahooWeatherService(this);
        droneService = new DroneService(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();
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

        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        // Check if we were successful in obtaining the map.
        if (mMap != null) {
            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                    // TODO Auto-generated method stub'
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
                    weatherService.refreshWeather(latitude + "," + longitude);
                }
            });

            Timer t = new Timer();

            t.scheduleAtFixedRate(
                    new TimerTask() {
                        public void run() {
                            droneService.refreshLocation("test");
                        }
                    },
                    0,
                    500);
        }
    }

    @Override
    public void weatherServiceSuccess(Channel channel) {
        dialog.hide();
        Item item = channel.getItem();
        locationTextView.setText(channel.getLocation().getCity() + ", " + channel.getLocation().getCountry());
        temperatureTextView.setText(item.getCondition().getTemperature() + "\u00B0" + channel.getUnits().getTemperature());
        conditionTextView.setText(item.getCondition().getDescription());
        speedTextView.setText(channel.getWind().getSpeed() + "km/h");
        visibilityTextView.setText(channel.getAtmosphere().getVisibility() + "km");
    }

    @Override
    public void weatherServiceFailure(Exception exception) {
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        dialog.hide();
    }

    @Override
    public void droneServiceSuccess(Drone drone) {
        if (mMap != null) {
            LatLng dronePos = new LatLng(drone.getLatitude(), drone.getLongitude());
            if (dronePos != initialPos) {
                if (marker != null) {
                    marker.remove();
                }
                marker = mMap.addMarker(new MarkerOptions().position(dronePos)
                        .icon(BitmapDescriptorFactory.fromBitmap(droneIcon))
                        .rotation((float) drone.getHeading()));
                initialPos = dronePos;
            }
            Log.e("newLoc", dronePos + "");
        }
    }

    @Override
    public void droneServiceFailure(Exception exception) {
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        dialog.hide();
    }
}
