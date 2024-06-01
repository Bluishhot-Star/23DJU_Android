package my.hosipital;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class XmlDataAdapter extends ArrayAdapter<XmlData> {
    public XmlDataAdapter(Context context, List<XmlData> data) {
        super(context, R.layout.list_item, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        XmlData item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item, parent, false);
        }
        TextView num = convertView.findViewById(R.id.num);
        TextView titleView = convertView.findViewById(R.id.title);
        TextView subtitleView = convertView.findViewById(R.id.subtitle);

        titleView.setText(item.getHName());
        subtitleView.setText((item.getSection() == null ? "-" : item.getSection()) + " / " + "응급실 : "+(item.getEtc() == "N" ? "O" : "X"));
        num.setText(item.getHospitalSeq());
        return convertView;
    }
}
