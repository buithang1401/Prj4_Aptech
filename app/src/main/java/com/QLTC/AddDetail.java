package com.QLTC;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.QLTC.Module.Detail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AddDetail extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText mDetailMoney,mMoney;
    private DatePickerDialog datePickerDayTime;
    public String date;
    Button mDay;
    private Spinner mType2,mCategory1,mCategory2,mCategory3;
    private TextView mGetType2,mGetCategory;
    private Button mSave, mBack;
    public String id_mail;
    public String user_name;
    DatabaseReference reference;
    int maxid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_detail);
        mDetailMoney = findViewById(R.id.etxtDetail);
        mMoney = findViewById(R.id.etxtMoney);
        initDateDayTime();
        mSave = findViewById(R.id.btnSave);
        mDay = findViewById(R.id.btnDay);
        mDay.setText(getTodaysDate());
        mBack = findViewById(R.id.btnBack2);

        mType2 = findViewById(R.id.spType2);
        mCategory1 = findViewById(R.id.spCategory1);
        mCategory2 = findViewById(R.id.spCategory2);
        mCategory3 = findViewById(R.id.spCategory3);
        mGetType2 = findViewById(R.id.tvGetType2);
        mGetCategory = findViewById(R.id.tvGetCategory);

        CheckUser();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Spnner_Role,R.layout.color_spinner_playout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        mType2.setAdapter(adapter);
        mType2.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Income_Role,R.layout.color_spinner_playout);
        adapter1.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        mCategory1.setAdapter(adapter1);
        mCategory1.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.Spend_Role,R.layout.color_spinner_playout);
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        mCategory2.setAdapter(adapter2);
        mCategory2.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.Other_Role,R.layout.color_spinner_playout);
        adapter3.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        mCategory3.setAdapter(adapter3);
        mCategory3.setOnItemSelectedListener(this);



        mDay.setOnClickListener(v -> {
            datePickerDayTime.show();
        });

        mSave.setOnClickListener(view -> {
            if(mDetailMoney.getText().toString().equals("")){
                mDetailMoney.setError("Chưa điền thông tin Thu/Chi");
            }if(mMoney.getText().toString().equals("")){
                mMoney.setError("Chưa điền số tiền Thu/Chi");
            }else{

                reference = FirebaseDatabase.getInstance("https://qltc-53f51-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("User").child(id_mail).child(mType2.getSelectedItem().toString());

                if(mType2.getSelectedItem().toString().equals("Thu")){
                    reference = FirebaseDatabase.getInstance("https://qltc-53f51-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("User").child(id_mail).child("Thu");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                maxid = (int) dataSnapshot.getChildrenCount();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    Detail detailinfo = new Detail(
                            mType2.getSelectedItem().toString(),
                            mCategory1.getSelectedItem().toString(),
                            mDetailMoney.getText().toString(),
                            mDay.getText().toString(),
                            mMoney.getText().toString()
                    );
//                    String[] month = mDay.getText().toString().split("-");
//                    String Month = month[0];

                    reference.child(String.valueOf(maxid+1)).setValue(detailinfo);

                    Toast.makeText(AddDetail.this, "Success Add !!!", Toast.LENGTH_SHORT).show();

                    Back();

                }if(mType2.getSelectedItem().toString().equals("Chi")){
                    reference = FirebaseDatabase.getInstance("https://qltc-53f51-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("User").child(id_mail).child("Chi");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                maxid = (int) dataSnapshot.getChildrenCount();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    Detail detailinfo = new Detail(
                            mType2.getSelectedItem().toString(),
                            mCategory2.getSelectedItem().toString(),
                            mDetailMoney.getText().toString(),
                            mDay.getText().toString(),
                            mMoney.getText().toString()
                    );
//                    String[] month = mDay.getText().toString().split("-");
//                    String Month = month[0];

                    reference.child(String.valueOf(maxid+1)).setValue(detailinfo);

                    Toast.makeText(AddDetail.this, "Success Add !!!", Toast.LENGTH_SHORT).show();

                    Back();
                }
            }
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

    private void Back(){
        Intent intent = new Intent(getApplicationContext(), DetailInfo.class);

        intent.putExtra("name",user_name);
        intent.putExtra("idmail",id_mail);

        startActivity(intent);

        finish();
    }

//    private void setcategory(){
//        if(mGetType2.getText().toString().equals("Thu")){
//            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Income_Role,R.layout.color_spinner_playout);
//            adapter1.setDropDownViewResource(R.layout.spinner_dropdown_layout);
//            mCategory.setAdapter(adapter1);
//            mCategory.setOnItemSelectedListener(this);
//        }
//        if(mGetType2.getText().toString().equals("Chi")){
//            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.Spend_Role,R.layout.color_spinner_playout);
//            adapter2.setDropDownViewResource(R.layout.spinner_dropdown_layout);
//            mCategory.setAdapter(adapter2);
//            mCategory.setOnItemSelectedListener(this);
//        }
//        if(mGetType2.getText().toString().equals("Khác")){
//            ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.Other_Role,R.layout.color_spinner_playout);
//            adapter3.setDropDownViewResource(R.layout.spinner_dropdown_layout);
//            mCategory.setAdapter(adapter3);
//            mCategory.setOnItemSelectedListener(this);
//        }
//    }

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

    private void CheckUser() {
        Intent intent = getIntent();
        id_mail = intent.getStringExtra("idmail");
        user_name = intent.getStringExtra("name");
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mGetType2.setText(mType2.getSelectedItem().toString());
        if(mType2.getSelectedItem().toString().equals("Thu")){
            mCategory1.setVisibility(View.VISIBLE);
            mCategory2.setVisibility(View.GONE);
            mCategory3.setVisibility(View.GONE);
        }
        if(mType2.getSelectedItem().toString().equals("Chi")){
            mCategory1.setVisibility(View.GONE);
            mCategory2.setVisibility(View.VISIBLE);
            mCategory3.setVisibility(View.GONE);
        }
        if(mType2.getSelectedItem().toString().equals("Khác")){
            mCategory1.setVisibility(View.GONE);
            mCategory2.setVisibility(View.GONE);
            mCategory3.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}