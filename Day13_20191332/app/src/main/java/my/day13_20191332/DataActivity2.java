package my.day13_20191332;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import my.day13_20191360.R;


class DBHelper extends SQLiteOpenHelper{


    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "Test2.db", factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE login (_id INTEGER PRIMARY KEY" + " AUTOINCREMENT, user_id TEXT, user_pwd TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS login");
        onCreate(sqLiteDatabase);
    }
}



public class DataActivity2 extends AppCompatActivity {

    SQLiteDatabase db;
    EditText edit_id, edit_pass;
    TextView edit_result;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_data2);
        dbHelper= new DBHelper(this,null,null,1);

        try{
            db=dbHelper.getWritableDatabase();
        }catch(SQLException e){
            db=dbHelper.getReadableDatabase();

        }
        edit_id = (EditText) findViewById(R.id.id);
        edit_pass = (EditText) findViewById(R.id.pass);
        edit_result = (TextView) findViewById(R.id.textView);
    }


    public void enroll(View target) {
        String id = edit_id.getText().toString();
        String pwd = edit_pass.getText().toString();

        db.execSQL("INSERT INTO login(_id,user_id, user_pwd) VALUES (null, '" + id + "', '" + pwd + "');");

        Toast.makeText(getApplicationContext(), "성공적으로 추가됨", Toast.LENGTH_SHORT).show();
        edit_id.setText("");
        edit_pass.setText("");
    }

    public void login(View target) {
        String id = edit_id.getText().toString();
        String pwd = edit_pass.getText().toString();
        Cursor cursor;
        try {
            cursor = db.rawQuery("Select user_id, user_pwd FROM login WHERE user_id='" + id + "' and user_pwd = '" + pwd + "';", null);
            while(cursor.moveToNext()){
                String uid = cursor.getString(0);
                String upwd = cursor.getString(1);
                edit_id.setText(uid);
                edit_pass.setText(upwd);
            }
        }catch (SQLException e){
            Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
    }



}

