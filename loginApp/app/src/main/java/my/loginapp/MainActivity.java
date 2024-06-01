package my.loginapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText id, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id = (EditText) findViewById(R.id.id);
        pwd = (EditText) findViewById(R.id.pwd);
    }
    public void nextPage(View v){
        Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
        startActivity(intent);
    }
    public void logIn(View v){
        String text1 = id.getText().toString();
        String text2 = pwd.getText().toString();
        Intent intent = new Intent(this, SubActivity.class);
        intent.putExtra("text1", text1);
        intent.putExtra("text2", text2);
        startActivityForResult(intent,100);
    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK){
            String result = data.getStringExtra("Result");
            Toast.makeText(this, result , Toast.LENGTH_SHORT).show();
        }
    }
}