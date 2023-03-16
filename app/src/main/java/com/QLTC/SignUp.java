package com.QLTC;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.QLTC.Module.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class SignUp extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    public Button mDate;
    private EditText mEmail,mName;
    private EditText mPass;
    private EditText mCPass;
    private Button mRegister;
    String EmailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    User user;
    int maxid = 0;

    DatabaseReference reference;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mEmail = findViewById(R.id.etxtName);
        mPass = findViewById(R.id.etxtPassword);
        mCPass = findViewById(R.id.etxtCPassword);
        mRegister = findViewById(R.id.btnRegister);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        progressDialog = new ProgressDialog(this);
        mName = findViewById(R.id.etxtPName2);

        user = new User();
        reference = FirebaseDatabase.getInstance("https://qltc-53f51-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("User");

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


//        mDate.setOnClickListener(v -> {
//            datePickerDialog.show();
//        });

//        ArrayList<String> arrayRole = new ArrayList<String>();
//
//        arrayRole.add("Student");
//        arrayRole.add("Teacher");
//        arrayRole.add("Parent");
//
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_dropdown_item_1line,arrayRole);
//
//        mRole.setAdapter(arrayAdapter);

//        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.Spnner_Role,R.layout.color_spinner_playout);
//        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
//        mRole.setAdapter(adapter);
//        mRole.setOnItemSelectedListener(this);

        mRegister.setOnClickListener(v -> {

            PerforAuth();
            AddInfo();
        });
    }
    private void AddInfo() {
        String[] mail = mEmail.getText().toString().split("\\.");
        String IDmail = mail[0];

//        user.setmEmail(mEmail.getText().toString());
//        user.setmPassword(mPass.getText().toString());
        user.setmName(mName.getText().toString());
        user.setmIDmail(IDmail);

        reference.child(IDmail).setValue(user);
    }

    private void PerforAuth() {
        String Email = mEmail.getText().toString().trim();
        String Password = mPass.getText().toString().trim();
        String ConfirmPassword = mCPass.getText().toString().trim();

        if(TextUtils.isEmpty(Email)){
            mEmail.setError("Email is empty!!!");
        }
        else if(TextUtils.isEmpty(Password)){
            mPass.setError("Password is empty!!!");
        }
        else if(TextUtils.isEmpty(ConfirmPassword)){
            mCPass.setError("Confirm Password is empty!!!");
        }
        else if(!Email.matches(EmailPattern)){
            mEmail.setError("Email is wrong!!!");
        }
        else if(!Password.equals(ConfirmPassword)){
            mCPass.setError("Password not match!!!");
        }
        else{
            mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    progressDialog.setMessage("Please wait Sign Up !!!");
                    progressDialog.setTitle("SignUp");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    sendUserToNextActivity();
                    Toast.makeText(SignUp.this, "SignUp Success!!!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SignUp.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(SignUp.this,Login.class);
        startActivity(intent);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                mDate.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + "/" + day + "/" + year;
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

        //default should never happen
        return "JAN";
    }
}