package com.example.fragment;
// Hoàng Bá Minh thiết kế giao diện
//

import static android.app.ProgressDialog.show;

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

import com.example.di_cho.MainActivity;
import com.example.di_cho.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;

public class RegisterFragment extends Fragment {
    EditText edtUsername, edtPassword, edtAgPassword;
    Button btnSingup;


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

        //ánh xạ
        edtUsername = v.findViewById(R.id.edtUsername);
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
        String password = edtPassword.getText().toString().trim();
        String agPassword = edtAgPassword.getText().toString().trim();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (email.isEmpty() || password.isEmpty() || agPassword.isEmpty()) {
            Toast.makeText(getActivity(), "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(agPassword)) {
            Toast.makeText(getActivity(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
        } else {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getActivity(), "Kiểm tra Email của bạn", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}