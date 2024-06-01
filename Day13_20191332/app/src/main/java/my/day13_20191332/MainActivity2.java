package my.day13_20191332;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import my.day13_20191360.R;

public class MainActivity2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void click1(View view){
        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
        startActivity(intent);

    }
    public void click2(View view){
        Intent intent = new Intent(MainActivity2.this, MemoActivity.class);
        startActivity(intent);

    }
    public void click3(View view){
        Intent intent =new Intent(MainActivity2.this, DataActivity.class);
        startActivity(intent);

    }
    public void click4(View view){
        Intent intent =new Intent(MainActivity2.this, DataActivity2.class);
        startActivity(intent);
    }
    public void click5(View view){
        Intent intent =new Intent(MainActivity2.this,TCPActivity.class);
        startActivity(intent);
    }
    public void click6(View view){
        Intent intent =new Intent(MainActivity2.this,MainActivity3.class);
        startActivity(intent);
    }
}