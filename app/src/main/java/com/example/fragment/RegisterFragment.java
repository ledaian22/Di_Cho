package com.example.fragment;
// Hoàng Bá Minh thiết kế giao diện
//

import static android.app.ProgressDialog.show;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.di_cho.LoginScreen;
import com.example.di_cho.MainActivity;
import com.example.di_cho.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.DatabaseMetaData;
import java.util.HashMap;
import java.util.concurrent.Executor;

import javax.xml.validation.Validator;

public class RegisterFragment extends Fragment {
    EditText edtUsername, edtPassword, edtAgPassword, edtPhonenumber;
    Button btnSingup;
    ProgressDialog loadingBar;


    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_register, container, false);

        //loadingBar
        loadingBar = new ProgressDialog(this.getActivity());

        //ánh xạ
        edtUsername = v.findViewById(R.id.edtUsername);
        edtPhonenumber = v.findViewById(R.id.edtPhonenumber);
        edtPassword = v.findViewById(R.id.edtPassword);
        edtAgPassword = v.findViewById(R.id.edtAgPassword);
        btnSingup = v.findViewById(R.id.btnSingup);

        //Xử lý nút đăng ký
        btnSingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kiemTra();
            }
        });
        return v;
    }

    private void kiemTra() {
        String email = edtUsername.getText().toString().trim();
        String phoneNumber = edtPhonenumber.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String agPassword = edtAgPassword.getText().toString().trim();

        if (email.isEmpty() || phoneNumber.isEmpty() || password.isEmpty() || agPassword.isEmpty()) {
            Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(agPassword)) {
            Toast.makeText(getActivity(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Tạo tài khoản");
            loadingBar.setMessage("Vui lòng chờ");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidatephoneNumber(email, phoneNumber, password);
        }
    }

    private void ValidatephoneNumber(String email, String phoneNumber, String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!(snapshot.child("Users").child(phoneNumber).exists())) {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("email",email);
                    userdataMap.put("phone",phoneNumber);
                    userdataMap.put("password",password);

                    RootRef.child("Users").child(phoneNumber).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                       if(task.isSuccessful()){
                           Toast.makeText(getActivity(), "Bạn đã tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                           loadingBar.dismiss();
                           Intent intent = new Intent(getActivity(), LoginScreen.class);
                           startActivity(intent);
                       }else {
                           loadingBar.dismiss();
                           Toast.makeText(getActivity(), "Tạo tài khoản thất bại", Toast.LENGTH_SHORT).show();
                       }
                        }
                    });


                } else {
                    Toast.makeText(getActivity(), "This " + phoneNumber + "alredy exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(getActivity(), "Vui lòng sử dụng số điện thoại khác", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), LoginScreen.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}