package my.hosipital;

import static com.gun0912.tedpermission.provider.TedPermissionProvider.context;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback
{
    private MapView mapView;
    private static NaverMap naverMap;
    //좌표
    public Double valX, valY;
    public Double lat, lon;
    public String name;
    GpsTracker gpsTracker;
    Button Btn;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        lat = intent.getDoubleExtra("lat", 0);
        lon = intent.getDoubleExtra("lon", 0);
        valX = intent.getDoubleExtra("valX",0);
        valY = intent.getDoubleExtra("valY",0);
        name = intent.getStringExtra("name");
        Log.d("MAPPPP", ""+valX+"ddfjlkdjfvljk"+valY);
        InfoWindow infoWindow = new InfoWindow();

        new Handler().postDelayed(new Runnable()
          {
              @Override
              public void run()
              {
                  //딜레이 후 시작할 코드 작성
                  mapView = (MapView) findViewById(R.id.map_view);
                  mapView.onCreate(savedInstanceState);
                  mapView.getMapAsync(MapsActivity.this::onMapReady);
                  Marker marker1 = new Marker();
                  marker1.setPosition(new LatLng(lat, lon));
                  marker1.setIcon(OverlayImage.fromResource(R.drawable.baseline_place_25));


                  Marker marker = new Marker();
                  marker.setPosition(new LatLng(valY, valX));
                  marker.setIcon(OverlayImage.fromResource(R.drawable.baseline_place_24));

                  infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(context) {
                      @NonNull
                      @Override
                      public CharSequence getText(@NonNull InfoWindow infoWindow) {
                          return name;
                      }
                  });
                  marker1.setMap(naverMap);
                  marker.setMap(naverMap);

                  infoWindow.open(marker);
              }
          }, 200);
        //네이버 지도

    }
    public void gpsCall(View view){

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                LatLng location = new LatLng(lat, lon);
//        //위치 및 각도 조정
                CameraPosition cameraPosition = new CameraPosition(location, 16);
                naverMap.setCameraPosition(cameraPosition);
                Toast.makeText(getApplicationContext(),"내 위치",Toast.LENGTH_SHORT);
            }
        }, 215);

    }
    @Override
    public void onMapReady(@NonNull NaverMap naverMap)
    {
        this.naverMap = naverMap;
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Log.d("MAPPAPAPAPPA",""+valX+valY);
                Log.d("MAPPAPAPAPPA",""+lon+"  "+lat);
                LatLng location = new LatLng(valY, valX);
//        //위치 및 각도 조정
                CameraPosition cameraPosition = new CameraPosition(location, 16);
                naverMap.setCameraPosition(cameraPosition);
            }
        }, 205);

    }

    @Override
    public void onStart()
    {
        super.onStart();
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                mapView.onStart();
            }
        }, 215);

    }

    @Override
    public void onResume()
    {
        super.onResume();
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                mapView.onResume();
            }
        }, 215);

    }

    @Override
    public void onPause()
    {
        super.onPause();
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                mapView.onPause();
            }
        }, 215);

    }

    @Override
    public void onStop()
    {
        super.onStop();
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                mapView.onStop();
            }
        }, 215);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                mapView.onDestroy();
            }
        }, 215);

    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                mapView.onSaveInstanceState(outState);
            }
        }, 215);

    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                mapView.onLowMemory();
            }
        }, 215);


    }
}