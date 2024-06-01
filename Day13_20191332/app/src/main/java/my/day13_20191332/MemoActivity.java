package my.day13_20191332;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import my.day13_20191360.R;

public class MemoActivity extends AppCompatActivity {
    String FILENAME = "";
    EditText filename, memo;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        filename = (EditText) findViewById(R.id.filename);
        memo = (EditText) findViewById(R.id.memo);
        Button readButton = (Button) findViewById(R.id.read);
        readButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    FILENAME=filename.getText().toString();
                    FileInputStream fis=openFileInput(FILENAME);
                    byte[] buffer=new byte[fis.available()];

                    fis.read(buffer);
                    memo.setText(new String(buffer));
                    fis.close();

                } catch (IOException e) {
                }
            }
        });

        Button writeButton = (Button) findViewById(R.id.write);
        writeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    FileOutputStream fos=openFileOutput(FILENAME,Context.MODE_PRIVATE);
                    fos.write(memo.getText().toString().getBytes());

                    fos.close();

                } catch (IOException e) {
                }
            }
        });
        Button deleteButton = (Button) findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
    }
}