package com.QLTC;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.QLTC.Module.Detail;
import com.QLTC.adapter.DetailListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DecimalFormat;
import java.util.List;

public class Home extends AppCompatActivity {

    // Create the object of TextView
    // and PieChart class
    TextView tvIncome, tvSpend, tvAnother, tvTB, tvIDmail ;
    PieChart pieChart;
    Button mDetail , mAddDetail;
    public String user_name;
    public String id_mail;
    DatabaseReference reference;
    int sum1 = 0;
    int sum2 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Link those objects with their
        // respective id's that
        // we have given in .XML file
        tvIncome = findViewById(R.id.tvIncome);
        tvTB = findViewById(R.id.tvTB);
        tvIDmail = findViewById(R.id.tvIDmail);
        tvSpend = findViewById(R.id.tvSpend);
        pieChart = findViewById(R.id.piechart);
        mDetail = findViewById(R.id.btnDetail);
        mAddDetail = findViewById(R.id.btnAddDetail);

        // Creating a method setData()
        // to set the text in text view and pie chart
        showUserInfo();

        getDataIncome();
        getDataOutcome();

        setData();


        mDetail.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), DetailInfo.class);

            intent.putExtra("name",user_name);
            intent.putExtra("idmail",id_mail);

            startActivity(intent);
            finish();
        });

        mAddDetail.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AddDetail.class);

            intent.putExtra("name",user_name);
            intent.putExtra("idmail",id_mail);

            startActivity(intent);
            finish();
        });
    }

    private void showUserInfo() {
        Intent intent = getIntent();
        user_name = intent.getStringExtra("name");
        id_mail = intent.getStringExtra("idmail");

        tvTB.setText("Welcome " + user_name);
        tvIDmail.setText(id_mail);
    }

    public void getDataIncome(){
        reference = FirebaseDatabase.getInstance("https://qltc-53f51-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("User").child(id_mail);
//        Query query = reference.child("Thu").orderByChild("mMoney");

        reference.child("Thu").orderByChild("mMoney").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                int sum = 0;
                for(DataSnapshot data: dataSnapshot.getChildren()) {
                    String value = data.child("mMoney").getValue(String.class);
//                    assert value != null;
                    int total = Integer.parseInt(value);

                    sum1 = sum1 + total;

                    tvIncome.setText(formatCurrency(String.valueOf(sum1)));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAG", databaseError.getMessage()); //Don't ignore potential errors!
            }
        });
    }

    public void getDataOutcome(){
        reference = FirebaseDatabase.getInstance("https://qltc-53f51-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("User").child(id_mail);
//        Query query = reference.child("Thu").orderByChild("mMoney");

        reference.child("Chi").orderByChild("mMoney").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data: dataSnapshot.getChildren()) {
                    String value = data.child("mMoney").getValue(String.class);
//                    assert value != null;
                    int total = Integer.parseInt(value);

                    sum2 = sum2 + total;

                    tvSpend.setText(formatCurrency(String.valueOf(sum2)));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAG", databaseError.getMessage()); //Don't ignore potential errors!
            }
        });
    }

    public void setData() {


//        query.addListenerForSingleValueEvent(valueEventListener);

        // Set the percentage of language used
//        tvIncome.setText(Integer.toString(sum1));
//        tvSpend.setText(Integer.toString(sum2));
//        tvAnother.setText(Integer.toString(60000));

        // Set the data and color to the pie chart
        pieChart.addPieSlice(
                new PieModel(
                        "Thu nhập",
                        Integer.parseInt(tvIncome.getText().toString()),
                        Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(
                new PieModel(
                        "Chi tiêu",
                        Integer.parseInt(tvSpend.getText().toString()),
                        Color.parseColor("#EF5350")));
//        pieChart.addPieSlice(
//                new PieModel(
//                        "Khoản phí khác",
//                        Integer.parseInt(tvAnother.getText().toString()),
//                        Color.parseColor("#FFA726")));

        // To animate the pie chart
        pieChart.startAnimation();
    }

    private static String formatCurrency(String number){
        DecimalFormat formatter = new DecimalFormat("###,###,###,###,### VND");
        return formatter.format(Double.parseDouble(number));
    }
}