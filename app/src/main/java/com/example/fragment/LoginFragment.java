package com.example.fragment;
// Hoàng Bá Minh thiết kế giao diện
//

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.di_cho.LoginScreen;
import com.example.di_cho.MainActivity;
import com.example.di_cho.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;


public class LoginFragment extends Fragment {
    EditText edtUsername, edtPassword;
    Button btnLogin;
    CheckBox cb_pass;


    public LoginFragment() {
        // Required empty public constructor

    }


    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_login, container, false);

        //ánh xạ
        edtUsername = v.findViewById(R.id.edtUsername);
        edtPassword = v.findViewById(R.id.edtPassword);
        btnLogin = v.findViewById(R.id.btnLogin);
        cb_pass = v.findViewById(R.id.cb_pass);

        //Xử Lý nút đăng nhập
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kiemTra();
            }
        });
        return v;
    }

    private void kiemTra() {
        String Username = edtUsername.getText().toString().trim();
        String Password = edtPassword.getText().toString().trim();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (Username.isEmpty() || Password.isEmpty()) {
            Toast.makeText(getActivity(), "Nhập tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
        } else {
            auth.signInWithEmailAndPassword(Username, Password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getActivity(), "Tài khoản không tồn tại",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}