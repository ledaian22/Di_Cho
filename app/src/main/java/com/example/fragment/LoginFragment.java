//package com.example.fragment;
//// Hoàng Bá Minh thiết kế giao diện
////
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.Prevalent.Prevalent;
//import com.example.di_cho.AdminAddStore;
//import com.example.di_cho.ForgotpassScreen;
//import com.example.di_cho.LoginBackUp;
//import com.example.di_cho.MainActivity;
//import com.example.di_cho.R;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import Model.Users;
//import io.paperdb.Paper;
//
////Đại An
//
//public class LoginFragment extends Fragment {
//    EditText ed_login_PhoneNumber, ed_login_Password;
//    Button btnBackUpLogin;
//    CheckBox chk_remember;
//    private ProgressDialog loadingBar;
//    private TextView isAdmin, isNotAdmin;
//    private String parentDbName = "Users";
//
//
//
//    public LoginFragment() {
//        // Required empty public constructor
//
//    }
//
//
//    public static LoginFragment newInstance() {
//        LoginFragment fragment = new LoginFragment();
//        return fragment;
//    }
//
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_login, container, false);
//
//        //Remember Password
//        Paper.init(this.getActivity());
//
//        //check admin
//        isNotAdmin.setVisibility(View.INVISIBLE);
//
//        isAdmin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isAdmin.setVisibility(View.INVISIBLE);
//                isNotAdmin.setVisibility(View.VISIBLE);
//                parentDbName="Admins";
//            }
//        });
//        isNotAdmin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isAdmin.setVisibility(View.VISIBLE);
//                isNotAdmin.setVisibility(View.INVISIBLE);
//                parentDbName="Users";
//            }
//        });
//
//        //ánh xạ
//        ed_login_PhoneNumber = v.findViewById(R.id.ed_login_PhoneNumber);
//        ed_login_Password = v.findViewById(R.id.ed_login_Password);
//        btnBackUpLogin = v.findViewById(R.id.btnBackUpLogin);
//        chk_remember = v.findViewById(R.id.chk_remember);
//        isAdmin = v.findViewById(R.id.tv_isAdminTrue);
//        isNotAdmin = v.findViewById(R.id.tv_isAdminFalse);
//
//        //Xử Lý nút đăng nhập
//        btnBackUpLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                kiemTra();
//            }
//        });
//
//        return v;
//    }
//
//
//    private void kiemTra() {
//        loadingBar = new ProgressDialog(this.getActivity());
//
//        String pass = ed_login_Password.getText().toString();
//        String phone = ed_login_PhoneNumber.getText().toString();
//        if (TextUtils.isEmpty(pass)){
//            Toast.makeText(getActivity(),"Không được để trống mật khẩu",Toast.LENGTH_SHORT).show();
//        } else if (TextUtils.isEmpty(phone)){
//            Toast.makeText(getActivity(),"Không được để trống số điện thoại",Toast.LENGTH_SHORT).show();
//        } else {
//            loadingBar.setTitle("Login");
//            loadingBar.setMessage("Login...");
//            loadingBar.setCanceledOnTouchOutside(false);
//            loadingBar.show();
//
//            AllowAccessToAccount(phone,pass);
//        }
//    }
//
//    private void AllowAccessToAccount(String phone, String pass) {
//        if (chk_remember.isChecked()){
//            Paper.book().write(Prevalent.UserPhoneKey,phone);
//            Paper.book().write(Prevalent.UserPasswordeKey,pass);
//        }
//
//
//        final DatabaseReference rootRef;
//        rootRef = FirebaseDatabase.getInstance("https://login-b73c7-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
//
//        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.child(parentDbName).child(phone).exists()){
//                    Users usersData = snapshot.child(parentDbName).child(phone).getValue(Users.class);
//                    if (usersData.getPhone().equals(phone)){
//                        if (usersData.getPass().equals(pass)){
//                            //Check Login as Admin
//                            if (parentDbName.equals("Admins")){
//                                Toast.makeText(getActivity(),"Logged as Admin",Toast.LENGTH_SHORT).show();
//                                loadingBar.dismiss();
//                                Intent i  = new Intent(getActivity(), AdminAddStore.class);
//                                startActivity(i);
//                            }
//                            //Check Login as User
//                            else if (parentDbName.equals("Users")) {
//                                Toast.makeText(getActivity(),"Welcome",Toast.LENGTH_SHORT).show();
//                                loadingBar.dismiss();
//                                Intent i  = new Intent(getActivity(), MainActivity.class);
//                                Prevalent.currentonlineUser = usersData;
//                                startActivity(i);
//                            }
//                        }
//                        //Check Password
//                        else {
//                            Toast.makeText(getActivity(),"Incorrect Password",Toast.LENGTH_SHORT).show();
//                            loadingBar.dismiss();
//                        }
//                    }
//                }
//                //Check if existing account
//                else {
//                    Toast.makeText(getActivity(),"Account do not exist",Toast.LENGTH_SHORT).show();
//                    loadingBar.dismiss();
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//
//}