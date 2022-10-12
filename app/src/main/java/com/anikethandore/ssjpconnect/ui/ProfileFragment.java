package com.anikethandore.ssjpconnect.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anikethandore.ssjpconnect.R;
import com.anikethandore.ssjpconnect.ui.user.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    //------------------------------------------
    //make variable here

    TextInputEditText name,email,phoneno;
 //   EditText  password;
    Button updateBtn,changePassBtn,logoutBtn;
    TextView fName,fEmail;
    private FirebaseAuth mAuth;
String mailid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        //write code here to make hock view.find-view-by-id(--id--)
        fName=view.findViewById(R.id.proUNtxt);
        fEmail=view.findViewById(R.id.proUEtxt);
        updateBtn=view.findViewById(R.id.btn_update);
        changePassBtn=view.findViewById(R.id.btn_cngPass);
        logoutBtn=view.findViewById(R.id.btn_logout);
        name=view.findViewById(R.id.proFNet);
        email=view.findViewById(R.id.proEMet);
       // password=view.findViewById(R.id.proPSet);
        phoneno=view.findViewById(R.id.proPNet);

        FirebaseUser user = mAuth.getCurrentUser();
        String uid=user.getUid();

        final ProgressDialog pd=new ProgressDialog(getContext());
        pd.setTitle("Loading....");
        pd.show();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("user").child(uid);

        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                fName.setText(snapshot.child("name").getValue().toString());
                fEmail.setText(snapshot.child("email").getValue().toString());
                name.setText(snapshot.child("name").getValue().toString());
                email.setText(snapshot.child("email").getValue().toString());
                phoneno.setText(snapshot.child("phoneNo").getValue().toString());
                mailid=snapshot.child("email").getValue().toString();

                pd.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"failed to get data for database",Toast.LENGTH_SHORT);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new AlertDialog.Builder(getContext())
                        .setMessage("Are you sure you want to Logout")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userLoginData", MODE_PRIVATE);
                                sharedPreferences.edit().clear().commit();
                                mAuth.signOut();

                                Toast.makeText(getContext(), "Logout Successfully",
                                        Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getContext(), LoginActivity.class));
                                getActivity().finish();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();


            }
        });

        changePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recoverPassword();
            }
        });

        return view;
    }

    private void recoverPassword() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("Change Password");
        LinearLayout linearLayout=new LinearLayout(getContext());
        EditText mail=new EditText(getContext());
        mail.setHint("Enter your email");
        mail.setText(mailid);
        mail.setMinEms(16);

        linearLayout.addView(mail);
        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);

        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String txt=mail.getText().toString().trim();
                if (txt.isEmpty()){
                    dialogInterface.dismiss();
                }else {
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
        ProgressDialog pd=new ProgressDialog(getContext());
        pd.setMessage("Please wait..");
        pd.show();
        mAuth.sendPasswordResetEmail(txt).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getContext(),"Recover email sent to your mail",Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }else {
                    Toast.makeText(getContext(),"Failed to Recover.Try again",Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });
    }
}