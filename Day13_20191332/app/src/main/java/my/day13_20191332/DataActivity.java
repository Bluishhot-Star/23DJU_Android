package my.day13_20191332;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import my.day13_20191360.R;


//class DBHelper extends SQLiteOpenHelper{
//
//
//    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, "Test.db", factory, 1);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL("CREATE TABLE contacts (_id INTEGER PRIMARY KEY" + " AUTOINCREMENT, name TEXT,tel TEXT);");
////        sqLiteDatabase.execSQL("CREATE TABLE login (_id INTEGER PRIMARY KEY" + " AUTOINCREMENT, uid TEXT, pwd TEXT);");
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS contacts");
//        onCreate(sqLiteDatabase);
//    }
//}



public class DataActivity extends AppCompatActivity {

    SQLiteDatabase db;
    EditText edit_name, edit_tel;
    TextView edit_result;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_data);
        dbHelper=new DBHelper(this,null,null,1);

        try{
            db=dbHelper.getWritableDatabase();
        }catch(SQLException e){
            db=dbHelper.getReadableDatabase();

        }
        edit_name = (EditText) findViewById(R.id.name);
        edit_tel = (EditText) findViewById(R.id.tel);
        edit_result = (TextView) findViewById(R.id.textView);
        //chap12 파일과 데이터베이스 P.27
//        db.execSQL("INSERT INTO contact VALUES(null,'"+edit_name+"',''"+edit_tel+"');'");

    }


    public void insert(View target) {
        String name = edit_name.getText().toString();
        String tel = edit_tel.getText().toString();

        db.execSQL("INSERT INTO contacts VALUES (null, '" + name + "', '" + tel + "');");


        Toast.makeText(getApplicationContext(), "성공적으로 추가됨", Toast.LENGTH_SHORT).show();
        edit_name.setText("");
        edit_tel.setText("");
    }

    public void search(View target) {
        String name = edit_name.getText().toString();
        Cursor cursor;

        cursor = db.rawQuery("Select name, tel FROM contacts WHERE name='"+name+"';", null);

        while(cursor.moveToNext()){
            String tel = cursor.getString(1);
            edit_tel.setText(tel);
        }
    }
    public void select_all(View target) {
        Cursor cursor;
        cursor = db.rawQuery("SELECT * FROM contacts", null);
        String s= "Id       Name        Tel \r\n";
        while(cursor.moveToNext()){
            s += cursor.getString(0) + "    ";
            s += cursor.getString(1) + "    ";
            s += cursor.getString(2) + "    \r\n";
        }
        edit_result.setText(s);
    }

}

