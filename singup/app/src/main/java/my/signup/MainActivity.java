package my.signup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText id, pwd, pwdAgain, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = (EditText) findViewById(R.id.idInput);
        pwd = (EditText) findViewById(R.id.pwdInput);
        pwdAgain = (EditText) findViewById(R.id.pwdInputAgain);
        name = (EditText) findViewById(R.id.nameInput);
    }
    public void submit (View view){
        String text1 = id.getText().toString();
        String text2 = pwd.getText().toString();
        String text3 = pwdAgain.getText().toString();
        String text4 = name.getText().toString();
        if (text2.length()<8){
            Toast.makeText(this, "비밀번호는 8자리 이상 숫자로 입력해주세요" , Toast.LENGTH_SHORT).show();
            return;
        }
        if (Integer.parseInt(text2) != Integer.parseInt(text3)){
            System.out.println(text2);
            System.out.println(text3);
            Toast.makeText(this, "비밀번호가 일치하지 않습니다" , Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, SubActivity.class);
        intent.putExtra("id", text1);
        intent.putExtra("pwd", text2);
        intent.putExtra("name", text4);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            String result = data.getStringExtra("Result");
            Toast.makeText(this, result , Toast.LENGTH_SHORT).show();
        }
    }
}