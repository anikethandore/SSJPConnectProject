package com.anikethandore.ssjpconnect.ui.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anikethandore.ssjpconnect.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    EditText regUsername,regEmail,regPass,regPhone;
    Button register_btn;
    TextView login_btntxt;



    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        regUsername=findViewById(R.id.et_UserNamereg);
        regEmail=findViewById(R.id.et_emailreg);
        regPass=findViewById(R.id.et_passwordreg);
        regPhone=findViewById(R.id.et_phoneNoreg);
        register_btn=findViewById(R.id.btn_register);
        login_btntxt=findViewById(R.id.tv_login);

        //Login Here Clicked
        login_btntxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });



        //Register Button Clicked
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=regUsername.getText().toString();
                String email=regEmail.getText().toString();
                String pass=regPass.getText().toString();
                String phoneno=regPhone.getText().toString();

                if (name.isEmpty()){
                    regUsername.setError("Field cannot be empty");
                }else if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    regEmail.setError("Enter Valid email");
                }else if (pass.isEmpty()){
                    regPass.setError("Field cannot be empty");
                }else if (pass.length()<6){
                    regPass.setError("Must be 6 or greater");
                }else if (phoneno.length()!=10){
                    regPhone.setError("Phone No. Should be 10 digit");
                }
                else{
                    registerUser(email,pass,name,phoneno);
                }

            }
        });


    }

    private void registerUser(String email, String pass, String name, String phoneno) {

        ProgressDialog pd=new ProgressDialog(SignupActivity.this);
        pd.setMessage("please wait.....");
        pd.show();

        String userType="user";

        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid=user.getUid();
                            HashMap<Object,String> hashMap=new HashMap<>();
                            hashMap.put("email",email);
                            hashMap.put("name",name);
                            hashMap.put("phoneNo",phoneno);
                            hashMap.put("uid",uid);
                            hashMap.put("usertype",userType);

                            FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                            DatabaseReference reference=firebaseDatabase.getReference("user");
                            reference.child(uid).setValue(hashMap);
                            pd.dismiss();
                            emailVerifiction();


                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    }
                });
    }

    private void emailVerifiction() {
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        if (firebaseUser!= null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){

                        Toast.makeText(SignupActivity.this, "Registration Complete, Please Verify your Email",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                        finish();
                    }else {
                        Toast.makeText(SignupActivity.this, "Registration Complete, fail to send verification mail",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}