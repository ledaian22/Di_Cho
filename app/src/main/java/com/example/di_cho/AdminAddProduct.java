package com.example.di_cho;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddProduct extends AppCompatActivity {
    private String CategoryName, Description, Price, Pname, saveCurrentDate, saveCurrentTime;
    private String productRandomKey, downloadImageURL;
    private Button btnAddNew;
    private EditText edName, edDesc, edPrice;
    private ImageView AddImage;
    private static final int GalleryPick =1;
    private Uri ImageURI;
    private ProgressDialog loadingBar;
    private StorageReference ProductImageRef;
    private DatabaseReference ProductsRef;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_admin_add_product);
        InitUI();
        CategoryName = getIntent().getExtras().get("Category").toString();
        ProductImageRef = FirebaseStorage.getInstance().getReference().child("ProductImage");
        loadingBar = new ProgressDialog(this);
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        AddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();

            }
        });

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
            }
        });

    }

    private void ValidateProductData() {
        Description = edDesc.getText().toString();
        Price = edPrice.getText().toString();
        Pname = edName.getText().toString();

        if (ImageURI == null){
            Toast.makeText(this,"Please selec picture",Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Description)){
            Toast.makeText(this,"Please write description",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Price)) {
            Toast.makeText(this, "Please insert price", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(Pname)) {
            Toast.makeText(this, "Please write Name", Toast.LENGTH_SHORT).show();
        } else {
            StorageProductInfo();
        }
    }

    private void StorageProductInfo() {
        loadingBar.setTitle("Adding new Product");
        loadingBar.setMessage("Please wait while we are adding new product.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        StorageReference filePath = ProductImageRef.child(ImageURI.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageURI);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AdminAddProduct.this,message,Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminAddProduct.this,"Product Image upload is succesfull",Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();

                        }
                    downloadImageURL = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            downloadImageURL = task.getResult().toString();
                            Toast.makeText(AdminAddProduct.this,"Product Image URL is saved to Database",Toast.LENGTH_SHORT).show();
                            SaveProductInfoToDataBase();
                            loadingBar.dismiss();
                        }
                    }
                });
            }
        });
    }

    private void SaveProductInfoToDataBase() {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid",productRandomKey);
        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("desc",Description);
        productMap.put("image",downloadImageURL);
        productMap.put("catergory",CategoryName);
        productMap.put("price",Price);
        productMap.put("pname",Pname);

        ProductsRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Intent i = new Intent(AdminAddProduct.this,AdminAddStore.class);
                            startActivity(i);
                            loadingBar.dismiss();
                            Toast.makeText(AdminAddProduct.this,"Product is added to Database",Toast.LENGTH_SHORT).show();
                        } else {
                            String message = task.getException().toString();
                            Toast.makeText(AdminAddProduct.this,"Error: " + message,Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                        }
                    }
                });
    }

    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GalleryPick && resultCode==RESULT_OK && data !=null){
            ImageURI = data.getData();
            AddImage.setImageURI(ImageURI);
        }
    }

    public void InitUI() {
        btnAddNew = findViewById(R.id.btnAddNewProduct);
        edName = findViewById(R.id.product_name);
        edDesc = findViewById(R.id.product_desc);
        edPrice = findViewById(R.id.product_price);
        AddImage = findViewById(R.id.select_product_image);

    }
}