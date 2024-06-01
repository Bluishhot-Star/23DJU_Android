package my.day12_20191332networkapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;

public class WebDownloadActivity extends AppCompatActivity {
    public String URL = "";
    EditText edittext;
    ImageView imageview;
    Button button;
    ProgressDialog mProgressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_download);

        edittext = (EditText) findViewById(R.id.editText);
        imageview = (ImageView) findViewById(R.id.imageview);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                URL = edittext.getText().toString();
                new DownloadImage().execute(URL);
            }
        });
    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected void onPreExecute(){
            mProgressDialog = new ProgressDialog(WebDownloadActivity.this);
            mProgressDialog.setTitle("이미지 다운로드 예제");
            mProgressDialog.setMessage("이미지 다운로드 중입니다.");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }
        @Override
        protected Bitmap doInBackground(String... url){
            String imageURL = url[0];
            Bitmap bitmap = null;
            try {
                InputStream input = new java.net.URL(imageURL).openStream();
                bitmap = BitmapFactory.decodeStream(input);
            }catch (Exception e){
                e.printStackTrace();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap result){
            imageview.setImageBitmap(result);
            mProgressDialog.dismiss();
        }

    }
}