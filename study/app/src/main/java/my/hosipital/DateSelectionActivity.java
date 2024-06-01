package my.hosipital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class DateSelectionActivity extends AppCompatActivity {
    Button dateBtn;
    View dateF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_date_selection);

        dateBtn = findViewById(R.id.dateButton);
        dateF = findViewById(R.id.fragmentDate);
        FragmentManager fragmentManager1 = getSupportFragmentManager();
        FragmentManager fragmentManager2 = getSupportFragmentManager();

        fragmentManager1.beginTransaction()
                .replace(R.id.fragmentDate, new DateFragment(),"one")
                .commit();
        fragmentManager2.beginTransaction()
                .replace(R.id.fragmentContainer, new DateSelectionFragment(),"two")
                .commit();
        
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateF.setVisibility(View.VISIBLE);
            }
        });



    }
}