package my.day12_20191332networkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebDownloadActivity2 extends AppCompatActivity {
    public String URL = "";
    EditText editText;
    TextView textView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_download2);
        textView = findViewById(R.id.text);
        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                URL = editText.getText().toString();
                DownloadTask downloadTask = new DownloadTask();
                downloadTask.execute(URL);
            }
        });
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> { //URL, 중간진행상태, 결과
        String s = null;
        @Override
        protected String doInBackground(String... url){
            String result = null;
            try {
                s = downloadUrl(url[0]);
            }catch (Exception e){
                Log.d("Background Task",e.toString());
            }
            return s;
        }
        public String downloadUrl(String strUrl) throws IOException{
            String s = null;
            byte[] buffer = new byte[1000];
            InputStream iStream = null;
            try {
                URL url = new URL(strUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                iStream = urlConnection.getInputStream();
                iStream.read(buffer);
                s = new String(buffer);
            } catch (Exception e){
                Log.d("Exception Download", e.toString());
            }finally {
                iStream.close();
            }
            return s;
        }
        @Override
        protected void onPostExecute(String result){
            textView.setText(result);
            Toast.makeText(getBaseContext(), "성공", Toast.LENGTH_SHORT).show();
        }

    }
}