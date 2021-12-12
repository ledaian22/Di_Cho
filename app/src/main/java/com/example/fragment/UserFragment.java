package com.example.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

//import com.example.di_cho.LoginScreen;
import com.example.Prevalent.Prevalent;
import com.example.di_cho.Admin.AdminEditProductActivity;
import com.example.di_cho.R;
import com.example.di_cho.Seller.SellerCategoryActivity;
import com.example.di_cho.SplashScreen;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

//Hoàng Bá minh giao diện
//Đại An đăng xuất


public class UserFragment extends Fragment {
    private Button btn_dang_xuat, btnSaveInfo;
    private Toolbar toolbar;
    private EditText uName, uEmail, uAddress, uPhoneNumber, uPass,uConfirmPass;
    private ImageView userAvatar;
    private TextView welcome;
    private DatabaseReference userRef;
    private StorageReference userImageRef;
    private String userID = "";
    private ImageView AddImage;
    private Uri imageURI;
    private String myUrl="";
    private StorageTask uploadTask;
    private int checker =0;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_nguoidung, container, false);

        //ánh xạ
        welcome = v.findViewById(R.id.txt_cdc);
        userAvatar = v.findViewById(R.id.avatar);
        btn_dang_xuat = v.findViewById(R.id.btn_dang_xuat);
        btnSaveInfo = v.findViewById(R.id.btn_save_information);
        uName = v.findViewById(R.id.ed_information_user_name);
        uEmail = v.findViewById(R.id.ed_information_user_email);
        uAddress = v.findViewById(R.id.ed_information_user_address);
        uPhoneNumber = v.findViewById(R.id.ed_information_user_phonenumber);
        uPass = v.findViewById(R.id.ed_information_user_password);
        uConfirmPass = v.findViewById(R.id.ed_information_user_confirmpassword);
        //Display info
        uName.setText( Prevalent.currentonlineUser.getName());
        uEmail.setText(Prevalent.currentonlineUser.getEmail());
        uAddress.setText(Prevalent.currentonlineUser.getAddress());
        uPhoneNumber.setText( Prevalent.currentonlineUser.getPhone());
        welcome.setText("Welcome" + Prevalent.currentonlineUser.getName());
        uPass.setText(Prevalent.currentonlineUser.getPass());
        Picasso.get().load(Prevalent.currentonlineUser.getImage()).into(userAvatar);
        userID = Prevalent.currentonlineUser.getPhone();
        Log.d("User ID", userID);

        userRef = FirebaseDatabase.getInstance("https://login-b73c7-default-rtdb.asia-southeast1.firebasedatabase.app").getReference()
                .child("Users")
                .child(userID);
        userImageRef = FirebaseStorage.getInstance().getReference().child("UserAvatar");

        btnSaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPass()){
                    ApplyUserChanges();
                }
            }
        });

        //Change avatar
        userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker =1;
                CropImage.activity(imageURI)
                        .setAspectRatio(1,1)
                        .start(getContext(),UserFragment.this);
            }
        });


        //Xử lý đăng xuất
        btn_dang_xuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), SplashScreen.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        //Toolbar Init
        toolbar = (Toolbar) v.findViewById(R.id.main_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Hồ sơ");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setHasOptionsMenu(true);


        return v;
    }

    private boolean checkPass(){
        String userCurrentPass = uPass.getText().toString();
        String userConfirmPass = uConfirmPass.getText().toString();
        if (!userCurrentPass.equals(userConfirmPass)){
            Toast.makeText(getContext(),"Password mismatch",Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    private void ApplyUserChanges() {
        String UserNewName = uName.getText().toString();
        String UserNewEmail = uEmail.getText().toString();
        String UserNewAddress = uAddress.getText().toString();
        String UserNewPass = uConfirmPass.getText().toString();

        if (UserNewName.equals("")){
            Toast.makeText(getContext(),"You're not provide enough information",Toast.LENGTH_SHORT).show();
        } else if (UserNewEmail.equals("") && !UserNewEmail.contains("@")){
            Toast.makeText(getContext(),"This is not email address",Toast.LENGTH_SHORT).show();
        } else if (UserNewAddress.equals("")){
            Toast.makeText(getContext(),"You're not provide enough information",Toast.LENGTH_SHORT).show();
        } else {
            HashMap<String,Object> userMap = new HashMap<>();
            userMap.put("name",UserNewName);
            userMap.put("email",UserNewEmail);
            userMap.put("address",UserNewAddress);
            userMap.put("password",UserNewPass);

            userRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getContext(),"Changes successfully",Toast.LENGTH_SHORT).show();

                    }
                }
            });
            if (checker==1) {
                uploadImage();
            }
        }

    }

    private void uploadImage() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Updating Avatar");
        progressDialog.setMessage("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        if (imageURI!=null){
                final StorageReference fileRef =  userImageRef.child(Prevalent.currentonlineUser.getPhone() + ".jpg");
                uploadTask = fileRef.putFile(imageURI);

                uploadTask.continueWithTask(new Continuation() {
                    @Override
                    public Object then(@NonNull Task task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();

                        }
                        return fileRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        Uri downloadUrl = task.getResult();
                        myUrl = downloadUrl.toString();
                        DatabaseReference ref = FirebaseDatabase.getInstance("https://login-b73c7-default-rtdb.asia-southeast1.firebasedatabase.app").getReference()
                                .child("Users")
                                .child(userID);
                        HashMap<String,Object> userMap= new HashMap<>();
                        userMap.put("image",myUrl);
                        ref.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getContext(),"Upload image success",Toast.LENGTH_SHORT).show();

                            }
                        });
                        progressDialog.dismiss();
                    }
                });
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data!= null){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageURI = result.getUri();
            userAvatar.setImageURI(imageURI);
        } else {
            Toast.makeText(getContext(),"Error try again",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FragmentManager fm = getFragmentManager();
        switch (item.getItemId()){
            case android.R.id.home:

                HomeFragment homeFragment = new HomeFragment();
                fm.beginTransaction().replace(R.id.frament_container,homeFragment).addToBackStack(null)
                        .commit();
                break;
            case R.id.app_bar_cart:

                //Create Fragment Object
                CartFragment cartFragment = new CartFragment();
                //Set bundle data to Fragment
                fm.beginTransaction().replace(R.id.frament_container,cartFragment).addToBackStack(null)
                        .commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}