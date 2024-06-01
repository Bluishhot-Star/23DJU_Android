package my.hosipital;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

public class DateFragment extends Fragment {

    private DatePicker datePicker;

    private Button select;
    String date;
    String year;
    String month;
    String day;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_date, container, false);
        select = view.findViewById(R.id.select);
        datePicker = view.findViewById(R.id.datePicker);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();

                year = String.valueOf(datePicker.getYear());
                month = String.valueOf(datePicker.getMonth()+1);
                day = String.valueOf(datePicker.getDayOfMonth());
                if(Integer.parseInt(month) < 10){
                    month = "0"+month;
                }
                if(Integer.parseInt(day) < 10){
                    day = "0"+day;
                }
                date = year + "-" + month + "-"+ day;
                bundle.putString("date",date);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                DateSelectionFragment dateSelectionFragment = new DateSelectionFragment();
                dateSelectionFragment.setArguments(bundle);
                transaction.replace(R.id.fragmentContainer,dateSelectionFragment);
                transaction.commit();
                Toast.makeText(getContext(), date, Toast.LENGTH_SHORT).show();
                container.setVisibility(View.GONE);
            }
        });

        return view;
    }
}