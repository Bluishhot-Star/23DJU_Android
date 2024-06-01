package my.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    Button button1, button2;
    TextView text1;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        button1 = (Button) findViewById(R.id.upButton);
        button2 = (Button) findViewById(R.id.downButton);
        text1 = (TextView) findViewById(R.id.countText);
        if(savedInstanceState != null){
            count = savedInstanceState.getInt("count");
            text1.setText("현재 개수 = " + count);
        }
    }
    public void nextPage(View v){
        Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
        startActivity(intent);
    }
    public void countClicked(View view){
        switch (view.getId()){
            case R.id.upButton:
                count++;
                break;
            case R.id.downButton:
                count--;
                break;
        }
        text1.setText("현재 개수 = "+count);
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("count", count);
    }
}