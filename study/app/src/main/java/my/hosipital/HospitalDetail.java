package my.hosipital;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import android.os.Handler;
import android.os.Message;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;


public class HospitalDetail extends AppCompatActivity  implements OnMapReadyCallback {
    private String hospitalSeq;
    private static NaverMap naverMap;
    private MapView mapView;
    double lat, lon;
    private TextView addr1, addr2, hospitalName, phone, section, etc;
    Double valX, valY;
    String name;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Bundle data = msg.getData();

            String addr1Value = data.getString("addr1");
            String addr2Value = data.getString("addr2");
            String hospitalNameValue = data.getString("hospitalName");
            String phoneValue = data.getString("phone");
            String sectionValue = data.getString("section");
            String etcValue = data.getString("etc");

            name = hospitalNameValue;
            addr1.setText(addr1Value);
            addr2.setText(addr2Value);
            hospitalName.setText(hospitalNameValue);
            phone.setText(phoneValue);
            section.setText(sectionValue!=null?sectionValue:"-");
            etc.setText("응급실: " + (etcValue.equals("N") ? "X" : "O"));
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                getPoint();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }, 100);
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    Log.d("DETAILTAIALITLAIT", ""+valX+"\t"+valY+"\t");
                }
            }, 600);
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hospital_detail);


        mapView = (MapView) findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this::onMapReady);

        GpsTracker gpsTracker = new GpsTracker(this);
        lat = gpsTracker.getLatitude();
        lon = gpsTracker.getLongitude();

        addr1 = findViewById(R.id.addr1);
        addr2 = findViewById(R.id.addr2);
        hospitalName = findViewById(R.id.hospitalName);
        phone = findViewById(R.id.phone);
        section = findViewById(R.id.section);
        etc = findViewById(R.id.etc);

        Intent intent = getIntent();
        hospitalSeq = intent.getStringExtra("HospitalSeq");
        Double x = intent.getDoubleExtra("valX",0);
        Double y = intent.getDoubleExtra("valY",0);
        String n = intent.getStringExtra("name");
        Log.d("DETAILTAIALITLAIT", ""+x+"\t"+y+"\t"+n);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    fetchData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void getPoint() {
        String val = (String) addr1.getText();
        getPoint(
                val
        );
    }
    public void call(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:"+String.valueOf(phone.getText())));
        startActivity(intent);
    }
    public void map(View v){
        Intent intent = new Intent(this, MapsActivity.class);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                //딜레이 후 시작할 코드 작성
                intent.putExtra("valX", valX);
                intent.putExtra("valY", valY);
                intent.putExtra("name", name);
                intent.putExtra("lat", lat);
                intent.putExtra("lon", lon);
                Log.d("HospitalDetail", "좌표: "+valX+valY);
                Log.d("HospitalDetail", "좌표: "+lat+lon);
                startActivityForResult(intent,100);
            }
        }, 100);// 0.6초 정도 딜레이를 준 후 시작

    }
    public void search(View view){
        String srchString = hospitalName.getText().toString();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.search.naver.com/search.naver?query="+srchString));
        startActivity(browserIntent);
    }
    private void getPoint(String... addr) {
        GeoPointer geoPointer = new GeoPointer(this, listener);
        geoPointer.execute(addr);
    }

    private GeoPointer.OnGeoPointListener listener = new GeoPointer.OnGeoPointListener() {
        @Override
        public void onPoint(GeoPointer.Point[] p) {
            int sCnt = 0, fCnt = 0;
            for (GeoPointer.Point point : p) {
                if (point.havePoint) sCnt++;
                else fCnt++;

                Log.d("TEST_CODE", point.toString());
                valX = point.x;
                valY = point.y;
            }
            Log.d("TEST_CODE", String.format("성공 : %s, 실패 : %s", sCnt, fCnt));
        }

        @Override
        public void onProgress(int progress, int max) {
            Log.d("TEST_CODE", String.format("좌표를 얻어오는중 %s / %s", progress, max));
        }
    };




    private void fetchData() {
        try {
            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6300000/hospitalDataService/hospitalDataItem");
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=6JK5Vw8AoXCHDXM%2FwCG%2BECrgrz9jiAKpOc%2FpB5mDO13tBShyaANTBP1s03x53eSbVDd6dj8Xk%2FQwTOoPcvBjVw%3D%3D");
            urlBuilder.append("&" + URLEncoder.encode("hospitalSeq", "UTF-8") + "=" + hospitalSeq);

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/xml");
            Log.d("HospitalDetail", "Response code: " + conn.getResponseCode());

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();
                XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = parserFactory.newPullParser();
                parser.setInput(new InputStreamReader(is));

                parseXml(parser);

                is.close();
            } else {
                Log.d("HospitalDetail", "HTTP request failed with response code: " + conn.getResponseCode());
            }

            conn.disconnect();
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

    private void parseXml(XmlPullParser parser) throws XmlPullParserException, IOException {
        String addr1Value = null;
        String addr2Value = null;
        String hospitalNameValue = null;
        String phoneValue = null;
        String sectionValue = null;
        String etcValue = null;

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagName = parser.getName();

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (tagName.equalsIgnoreCase("addr1")) {
                        addr1Value = parser.nextText();
                        Log.d("HospitalDetail", "addr1: " + addr1Value);
                    } else if (tagName.equalsIgnoreCase("addr2")) {
                        addr2Value = parser.nextText();
                        Log.d("HospitalDetail", "addr2: " + addr2Value);
                    } else if (tagName.equalsIgnoreCase("nm")) {
                        hospitalNameValue = parser.nextText();
                        Log.d("HospitalDetail", "nm: " + hospitalNameValue);
                    } else if (tagName.equalsIgnoreCase("phone")) {
                        phoneValue = parser.nextText();
                        Log.d("HospitalDetail", "phone: " + phoneValue);
                    } else if (tagName.equalsIgnoreCase("section")) {
                        sectionValue = parser.nextText();
                        Log.d("HospitalDetail", "section: " + sectionValue);
                    } else if (tagName.equalsIgnoreCase("etc")) {
                        etcValue = parser.nextText();
                        Log.d("HospitalDetail", "etc: " + etcValue);
                    }
                    break;

                case XmlPullParser.END_TAG:
                    if (tagName.equalsIgnoreCase("msgBody")) {
                        sendMessageToHandler(addr1Value, addr2Value, hospitalNameValue, phoneValue, sectionValue, etcValue);
                    }
                    break;
            }

            eventType = parser.next();
        }
    }

    private void sendMessageToHandler(String addr1, String addr2, String hospitalName, String phone, String section, String etc) {
        Message msg = handler.obtainMessage();
        Bundle data = new Bundle();
        data.putString("addr1", addr1);
        data.putString("addr2", addr2);
        data.putString("hospitalName", hospitalName);
        data.putString("phone", phone);
        data.putString("section", section);
        data.putString("etc", etc);
        msg.setData(data);
        handler.sendMessage(msg);
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
                LatLng location = new LatLng(valY, valX);
//        //위치 및 각도 조정
                CameraPosition cameraPosition = new CameraPosition(location, 16);
                naverMap.setCameraPosition(cameraPosition);
                Marker marker = new Marker();
                marker.setPosition(new LatLng(valY, valX));
                marker.setIcon(OverlayImage.fromResource(R.drawable.baseline_place_24));
                marker.setMap(naverMap);
            }
        }, 805);

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
        }, 815);

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
        }, 815);

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
        }, 815);

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
        }, 815);

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
        }, 815);

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
        }, 815);

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
        }, 815);


    }
}






