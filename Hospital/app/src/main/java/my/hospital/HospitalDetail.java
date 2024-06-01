package my.hospital;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import org.xmlpull.v1.XmlPullParser;

public class HospitalDetail extends AppCompatActivity {
    XmlPullParser xpp;

    String key="czIfYWMH7zS3Ywu7DsvHNI%2BxcqomUkIny0nQsBNHqxqC4%2Fc7hhTp0i1sto1YFgSdzOvE0IVbYN1%2F2Z13a2s8uA%3D%3D";
    String data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hospital_detail);
    }

}