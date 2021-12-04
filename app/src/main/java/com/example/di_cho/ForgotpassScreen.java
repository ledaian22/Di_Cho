//package com.example.di_cho;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//
////Đại An
//
//public class ForgotpassScreen extends AppCompatActivity {
//
//    EditText edtEmail;
//    TextView tvMess ;
//    Button btnlayMatKhau;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_forgotpass_screen);
//
//        anhXa();
//        layMatKhau();
//    }
//
//    private void layMatKhau() {
//        btnlayMatKhau.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                kiemTra();
//            }
//        });
//    }
//
//    private void kiemTra() {
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        String emailAddress = edtEmail.getText().toString();
//        if(emailAddress.isEmpty()){
//            Toast.makeText(ForgotpassScreen.this,"Vui lòng nhập email",Toast.LENGTH_LONG).show();
//        }else {
//            auth.sendPasswordResetEmail(emailAddress)
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                tvMess.setText("Chúng tôi đã gửi link xác nhận mật khẩu cho bạn, vui lòng kiểm tra email");
//                                Handler handler = new Handler();
//                                handler.postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Intent intent = new Intent(ForgotpassScreen.this,LoginScreen.class);
//                                        startActivity(intent);
//                                    }
//                                },4000);
//                            }else {
//                                Toast.makeText(ForgotpassScreen.this,"Email không tồn tại",Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    });
//        }
//
//    }
//
//    private void anhXa() {
//        edtEmail = findViewById(R.id.edtEmail);
//        tvMess = findViewById(R.id.tvMess);
//        btnlayMatKhau = findViewById(R.id.btnlayMatKhau);
//    }
//}