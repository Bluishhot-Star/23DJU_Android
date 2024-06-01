package my.hosipital;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
public class DateSelectionFragment extends Fragment {
    private ListView dateList;
    XmlDataAdapter adapter;

    private ArrayList<XmlData> list;
    XmlData hospital = null;
    private String date;
    private TextView dateText;

    public DateSelectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_date_selection, container, false);
        dateList = view.findViewById(R.id.dateList);
        dateText = view.findViewById(R.id.dateText);
        list = new ArrayList<XmlData>();
        date = null;
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        date = formatter.format(now);
        if(getArguments() != null){
            date = getArguments().getString("date");
        }
        dateText.setText(date);
        // TODO: 날짜 선택을 위한 로직 작성
        new Thread(new Runnable() {

            @Override
            public void run() {
                try{

                    data(date);
                    adapter = new XmlDataAdapter(requireContext(), list);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dateList.setAdapter(adapter);
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        dateList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                XmlData data = adapter.getItem(i);

                Intent intent = new Intent(getActivity(), HospitalDetail.class);
                intent.putExtra("HospitalSeq",data.getHospitalSeq());
                startActivityForResult(intent,100);
            }
        });
        return view;
    }
    void data(String date){
        try{


            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6300000/hospitalDataService/hospitalDataList"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=6JK5Vw8AoXCHDXM%2FwCG%2BECrgrz9jiAKpOc%2FpB5mDO13tBShyaANTBP1s03x53eSbVDd6dj8Xk%2FQwTOoPcvBjVw%3D%3D"); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("searchKeyword", "UTF-8") + "=" + date+"&numOfRows=100");

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());

            boolean b_hospitalSeq = false;  // hospitalSeq 태그 유무
            boolean b_name = false;         // nm 태그 유무
            boolean b_section = false;      // section 태그 유무
            boolean b_phone = false;        // phone 태그 유무
            boolean b_etc = false; //응급실 유무

            //xml 파싱
            InputStream is = url.openStream();
            XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            parser.setInput(new InputStreamReader(is,"UTF-8"));
            String tag;
            int eventType = parser.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){//xml이 끝날때 까지
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT: // xmp시작
                        list = new ArrayList<XmlData>();// arraylist 시작
                        break;
                    case XmlPullParser.END_DOCUMENT: // xml 끝
                        break;
                    case XmlPullParser.END_TAG:// xml 종료 태그
                        if (parser.getName().equals("items")) { // 병원의 정보가 끝나는 items 태그의 마지막
                            list.add(hospital);// list에 hospital의 정보를 저장
                        }else if(parser.getName().equals("etc")){
                            b_etc = false;
                        }
                        else if(parser.getName().equals("hospitalSeq")){ // hospitalSeq 시작 태그
                            b_hospitalSeq = false;
                        }
                        else if(parser.getName().equals("nm")){
                            b_name = false;
                        }
                        else if(parser.getName().equals("phone")){ //phone 시작 태그
                            b_phone = false;
                        }
                        else if(parser.getName().equals("section")){ // section 시작 태그
                            b_section = false;
                        }

                    case XmlPullParser.START_TAG://xml의 시작 태그
                        if(parser.getName().equals("items")){ //imtes 시작 태그
                            hospital = new XmlData();
                        }
                        else if(parser.getName().equals("etc") && hospital.getEtc() == null){
                            b_etc = true;
                        }
                        else if(parser.getName().equals("hospitalSeq") && hospital.getHospitalSeq() == null){ // hospitalSeq 시작 태그
                            b_hospitalSeq = true;
                        }
                        else if(parser.getName().equals("nm")&& hospital.getHName()==null){
                            b_name = true;
                        }

                        else if(parser.getName().equals("phone") && hospital.getPhone() == null){ //phone 시작 태그
                            b_phone = true;
                        }
                        else if(parser.getName().equals("section") && hospital.getSection() == null){ // section 시작 태그

                            b_section = true;
                        }


                        break;
                    case XmlPullParser.TEXT:// xml의 text
                        if(b_etc){
                            hospital.setEtc(parser.getText());

                        }
                        else if(b_hospitalSeq){// xml의 text에 접근했을 때 b_hospitalSeq가 true이면 hospital에 hospitalSeq값 세팅
                            hospital.setHospitalSeq(parser.getText());

                        }
                        else if(b_name){
                            hospital.setHName(parser.getText());

                        }
                        else if(b_phone){
                            hospital.setPhone(parser.getText());

                        }
                        else if (b_section) {
                            hospital.setSection(parser.getText()!=null?parser.getText():"-");

                        }


                        break;
                }
                eventType = parser.next();

            }


        }catch (XmlPullParserException e) {
            throw new RuntimeException(e);
        }catch (IOException e){
            e.printStackTrace();
        }

    }


}
