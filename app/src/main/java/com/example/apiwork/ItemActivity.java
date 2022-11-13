package com.example.apiwork;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemActivity extends AppCompatActivity {

    TextInputLayout name;
    TextInputLayout kind;
    TextInputLayout age;
    TextInputLayout weight;
    int id;
    ImageView image;
    String encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Bundle arguments = getIntent().getExtras();
        String _name = arguments.get("name").toString();
        String _age = arguments.get("age").toString();
        String _kind = arguments.get("kind").toString();
        String _weight = arguments.get("weight").toString();
        String _image;
        try {
            _image = arguments.get("image").toString();
        }
        catch (Exception e){
            _image = "";
        }
        id = Integer.parseInt(arguments.get("id").toString());

        Log.e("id - ", String.valueOf(id));
        name = findViewById(R.id.text_name);
        kind = findViewById(R.id.text_kind);
        age = findViewById(R.id.text_age);
        weight = findViewById(R.id.text_weight);
        image = findViewById(R.id.image);

        name.getEditText().setText(_name);
        kind.getEditText().setText(_kind);
        age.getEditText().setText(_age);
        weight.getEditText().setText(_weight);

        if(_image != ""){
            image.setImageBitmap(getImageBitmap(_image));
        }
        else {
            image.setImageResource(R.drawable.icon);
        }
    }


    public void ImageClick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pickImg.launch(intent);
    }

    private final ActivityResultLauncher<Intent> pickImg = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            if (result.getData() != null) {
                Uri uri = result.getData().getData();
                try {
                    InputStream is = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    image.setImageBitmap(bitmap);
                    encodedImage = encodeImage(bitmap);
                } catch (Exception e) {

                }
            }
        }
    });
    private String encodeImage(Bitmap bitmap) {
        int prevW = 150;
        int prevH = bitmap.getHeight() * prevW / bitmap.getWidth();
        Bitmap b = Bitmap.createScaledBitmap(bitmap, prevW, prevH, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getEncoder().encodeToString(bytes);
        }
        return "";
    }

    private Bitmap getImageBitmap(String encodedImg) {
        if (encodedImg != null) {
            byte[] bytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = Base64.getDecoder().decode(encodedImg);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return BitmapFactory.decodeResource(ItemActivity.this.getResources(),
                R.drawable.icon);
    }

    public void BackBtn(View v){this.finish();}

    public void UpdateData(View v){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/NGKNN/ермолаевас/api/animals/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Animal modal = new Animal(String.valueOf(name.getEditText().getText()),
                String.valueOf(kind.getEditText().getText()),
                String.valueOf(age.getEditText().getText()),
                String.valueOf(weight.getEditText().getText()),
                id,encodeImage(bitmap));

        Call<Animal> call = retrofitAPI.updateData(id, modal);

        call.enqueue(new Callback<Animal>() {
            @Override
            public void onResponse(@NonNull Call<Animal> call, @NonNull Response<Animal> response) {
                Toast.makeText(ItemActivity.this, "Data updated to API", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<Animal> call, @NonNull Throwable t) {
                Toast.makeText(ItemActivity.this, t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Alert(View v){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Вы точно хотите удалить данный объект?")
                .setMessage("Для подпверждения нажмите ОК")
                .setPositiveButton("OK",DeleteData())
                .setNegativeButton("Отмена",null)
                .setCancelable(true)
                .create()
                .show();
    }
    public DialogInterface.OnClickListener DeleteData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/NGKNN/ермолаевас/api/animals/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<Animal> call = retrofitAPI.deleteData(id);

        call.enqueue(new Callback<Animal>() {
            @Override
            public void onResponse(@NonNull Call<Animal> call, @NonNull Response<Animal> response) {
                Toast.makeText(ItemActivity.this, "Data updated to API", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<Animal> call, @NonNull Throwable t) {
                Toast.makeText(ItemActivity.this, t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}