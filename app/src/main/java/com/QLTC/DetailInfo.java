package com.QLTC;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.QLTC.Module.Detail;
import com.QLTC.adapter.DetailListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DetailInfo extends AppCompatActivity implements OnItemSelectedListener {
    private RecyclerView mDetailList;
    private Spinner mType,mStype;
    private TextView mGetType;
    private Button mSearch, mBack;
    DatabaseReference reference;
    public String id_mail;
    public String user_name;
    private Button mDay;
    public List<Detail> mListDetail;
    public DetailListAdapter DetailListAdapter;
    private DatePickerDialog datePickerDayTime;
    public String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);
        mType = findViewById(R.id.spType);
        mStype = findViewById(R.id.spSType);
        mDetailList = findViewById(R.id.rcvDetail);
        mGetType = findViewById(R.id.tvGetType);
        mSearch = findViewById(R.id.btnSearch);
        mDay = findViewById(R.id.btnDay2);
        mDay.setText(getTodaysDate());
        mListDetail = new ArrayList<>();
        mBack = findViewById(R.id.btnBack);

        initDateDayTime();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Spnner_Role,R.layout.color_spinner_playout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        mType.setAdapter(adapter);
        mType.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.Search_Type,R.layout.color_spinner_playout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        mStype.setAdapter(adapter2);
        mStype.setOnItemSelectedListener(this);

        CheckUser();

        mDay.setOnClickListener(v -> {
            datePickerDayTime.show();
        });

        mSearch.setOnClickListener(v -> {
            mListDetail.clear();
            Search();
        });

        mBack.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Home.class);

            intent.putExtra("name",user_name);
            intent.putExtra("idmail",id_mail);

            startActivity(intent);
            finish();
        });

    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void Search(){
        if(mType.getSelectedItem().toString().equals("Thu")){
            reference = FirebaseDatabase.getInstance("https://qltc-53f51-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("User").child(id_mail);
            Query query = reference.child("Thu").orderByChild("mTime").equalTo(mDay.getText().toString());

            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        mListDetail.add(ds.getValue(Detail.class));

                        DetailListAdapter = new DetailListAdapter(mListDetail);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        mDetailList.setLayoutManager(linearLayoutManager);
                        mDetailList.setAdapter(DetailListAdapter);

                        Log.d("TAG", ds.getValue().toString());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("TAG", databaseError.getMessage());
                }
            };
            query.addListenerForSingleValueEvent(valueEventListener);

        }if(mType.getSelectedItem().toString().equals("Chi")){
            reference = FirebaseDatabase.getInstance("https://qltc-53f51-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("User").child(id_mail);
            Query query = reference.child("Chi").orderByChild("mTime").equalTo(mDay.getText().toString());

            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        mListDetail.add(ds.getValue(Detail.class));

                        DetailListAdapter = new DetailListAdapter(mListDetail);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        mDetailList.setLayoutManager(linearLayoutManager);
                        mDetailList.setAdapter(DetailListAdapter);

                        Log.d("TAG", ds.getValue().toString());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("TAG", databaseError.getMessage());
                }
            };
            query.addListenerForSingleValueEvent(valueEventListener);
        }
    }

    private void initDateDayTime() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            date = makeDateString(day, month, year );
            mDay.setText(date);
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int hh = cal.get(Calendar.HOUR);
        int mm = cal.get(Calendar.MINUTE);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDayTime = new DatePickerDialog(this, dateSetListener, year, month, day);
//        datePickerDayTime.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + "-" + day + "-" + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";

        return "JAN";
    }

    private void CheckUser() {
        Intent intent = getIntent();
        id_mail = intent.getStringExtra("idmail");
        user_name = intent.getStringExtra("name");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        mGetType.setText(mType.getSelectedItem().toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}