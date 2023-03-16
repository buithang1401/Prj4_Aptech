package com.QLTC;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.OAuthProvider;

public class Login extends Activity {
    String EmailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseUser mUser;
    private EditText mEmail;
    private EditText mPass;
    ProgressDialog progressDialog;
    DatabaseReference reference;
    private static final String TAG = "EmailPassword";
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button mLogin = findViewById(R.id.btnLogin);
        Button mSignUp = findViewById(R.id.btnSignUp);
        mEmail = findViewById(R.id.txtEmail);
        mPass = findViewById(R.id.txtPass);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        progressDialog = new ProgressDialog(this);

        reference = FirebaseDatabase.getInstance("https://qltc-53f51-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        mSignUp.setOnClickListener(v -> SignUp());

        mLogin.setOnClickListener(v -> {
            LoginAuth();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){

        }
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        FirebaseAuth.getInstance().signOut();
//    }

    private void LoginAuth() {
        String Email = mEmail.getText().toString().trim();
        String Password = mPass.getText().toString().trim();


        if(TextUtils.isEmpty(Email)){
            mEmail.setError("Email is empty!!!");
        }
        else if(TextUtils.isEmpty(Password)){
            mPass.setError("Password is empty!!!");
        }
        else if(!Email.matches(EmailPattern)){
            mEmail.setError("Email is wrong!!!");
        }
        else{
            progressDialog.setMessage("Loging in!!!");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

//            AuthCredential credential = OAuthProvider
//                    .newCredentialBuilder("oidc.example-provider")  // As registered in Firebase console.
//                    .setIdToken(idToken)  // ID token from OpenID Connect flow.
//                    .build();

            mAuth.signInWithEmailAndPassword(Email, Password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            String[] mail = mEmail.getText().toString().split("\\.");
                            String IDmail = mail[0];

                            Query CheckUser = reference.child("User").orderByChild("mIDmail");

                            CheckUser.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
//                                        Intent intent = new Intent(getApplicationContext(), Home.class);
//
////                                        intent.putExtra("name",Name);
//
//                                        startActivity(intent);
//                                        Toast.makeText(Login.this, "Login Success!!!", Toast.LENGTH_SHORT).show();
//                                    }
                                    if(snapshot.exists()){
                                        String  Name = snapshot.child(IDmail).child("mName").getValue(String.class);

                                        Intent intent = new Intent(getApplicationContext(), Home.class);

                                        intent.putExtra("name",Name);
                                        intent.putExtra("idmail",IDmail);

                                        startActivity(intent);
                                        Toast.makeText(Login.this, "Login Success!!!", Toast.LENGTH_SHORT).show();

                                    }

                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(Login.this, "Login Fail!!!", Toast.LENGTH_SHORT).show();
                                    Reload();
                                }
                            });
//                        else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
//                            Toast.makeText(Login.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
                        };
                    });
                    }
        }


    private void Reload() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }


    public void SignUp(){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}