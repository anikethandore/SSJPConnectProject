package com.anikethandore.ssjpconnect.ui.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anikethandore.ssjpconnect.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    EditText emailId,passwordtxt;
    Button loginBtn;
    TextView forgetPassBtn,registerBtn;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();


        emailId=findViewById(R.id.et_email);
        passwordtxt=findViewById(R.id.et_password);
        loginBtn=findViewById(R.id.btn_login);
        forgetPassBtn=findViewById(R.id.tv_forgotPassword);
        registerBtn=findViewById(R.id.tv_register);




        forgetPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recoverPassword();
            }
        });

        //go to signup activity
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });


        //login button clicked
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=emailId.getText().toString().trim();
                String password=passwordtxt.getText().toString().trim();

                if (email.isEmpty()){
                    emailId.setError("Required..");
                }else {
                    if (password.isEmpty()){
                        passwordtxt.setError("Required..");
                    }else {
                        loginUser(email,password);
                    }
                }

            }
        });
    }

            private void recoverPassword() {
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("Forget Password");
                LinearLayout linearLayout=new LinearLayout(this);
                EditText mail=new EditText(this);
                mail.setHint("Enter your email");
                mail.setMinEms(16);

                linearLayout.addView(mail);
                linearLayout.setPadding(10,10,10,10);
                builder.setView(linearLayout);

                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String txt=mail.getText().toString().trim();
                        if (txt.isEmpty()){
                            mail.setError("Required");
                        }
                        else if(!Patterns.EMAIL_ADDRESS.matcher(txt).matches()){
                            mail.setError("Please enter valid email");
                        }
                        else {
                            startRecover(txt);
                        }
                    }
                });


                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }

            private void startRecover(String txt) {
                ProgressDialog pd=new ProgressDialog(this);
                pd.setMessage("Please wait..");
                pd.show();
                mAuth.sendPasswordResetEmail(txt).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"Recover email sent to your mail",Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }else {
                            Toast.makeText(LoginActivity.this,"Failed to Recover.Try again",Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    }
                });
            }

            void loginUser(String email, String password) {

               ProgressDialog pd=new ProgressDialog(LoginActivity.this);
               pd.setMessage("please wait.....");
               pd.show();

               mAuth.signInWithEmailAndPassword(email, password)
                       .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               if (task.isSuccessful()) {
                                   // Sign in success, update UI with the signed-in user's information
                                   FirebaseUser user = mAuth.getCurrentUser();
                                   String uid=user.getUid();
                                   pd.dismiss();
                                   isVerify(email,uid);


                               } else {
                                   // If sign in fails, display a message to the user.
                                   Toast.makeText(LoginActivity.this, "failed to login! please check login details",
                                           Toast.LENGTH_SHORT).show();
                                   pd.dismiss();
                               }
                           }
                       });
            }

            private void isVerify(String email,String uid) {
                FirebaseUser firebaseUser=mAuth.getCurrentUser();
                boolean flag=firebaseUser.isEmailVerified();
                if (flag){
                    saveData(email,uid);
                }else {

                    /* TODO make the new activity for displaying user not verified*/
                    Toast.makeText(LoginActivity.this, "Please verify your email",
                            Toast.LENGTH_SHORT).show();
                    mAuth.signOut();
                }

            }

            void saveData(String email,String uid){

                SharedPreferences sharedPreferences=getSharedPreferences("userLoginData",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putBoolean("logincounter",true);
                editor.putString("email",email);
                editor.putString("uid",uid);

                editor.apply();
                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                finish();

            }

            @Override
            public void onBackPressed() {
                new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to exit")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                LoginActivity.super.onBackPressed();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();

            }
}