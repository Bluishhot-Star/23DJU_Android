package my.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity {
    TextView text1, text2;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        intent = getIntent();

        text1.setText("아이디\t\t: "+intent.getStringExtra("text1"));
        text2.setText("비밀번호\t: "+intent.getStringExtra("text2"));
    }
    public void returnClicked(View view){
        Intent intent = new Intent(SubActivity.this, MainActivity.class);
        intent.putExtra("Result","로그인 성공!");
        setResult(RESULT_OK, intent);
        finish();
    }
}