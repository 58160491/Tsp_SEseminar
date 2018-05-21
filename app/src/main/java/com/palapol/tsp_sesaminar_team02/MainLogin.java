package com.palapol.tsp_sesaminar_team02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.palapol.tsp_sesaminar_team02.model.ResObj;
import com.palapol.tsp_sesaminar_team02.remote.ApiUtils;
import com.palapol.tsp_sesaminar_team02.remote.UserService;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainLogin extends AppCompatActivity {

    EditText editUsername;
    EditText editPassword;
    Button btnLogin;
    UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        userService = ApiUtils.getUserService();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();
                if (validateLogin(username,password)){
                    doLogin(username,password);
                }
            }
        });
    }
    private boolean validateLogin(String username,String password){
        if (username == null || username.trim().length() == 0){
            Toast.makeText(this,"Username is required",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password == null || password.trim().length() == 0){
            Toast.makeText(this,"Password is required",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void doLogin(final String username, String password){

        OkHttpClient okHttpClient = com.kingkarn.projectbeaconapplication.UnsafeOkHttpClient.getUnsafeOkHttpClient();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
//        if (retrofit == null){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://se.informatics.buu.ac.th/seminar/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);
        userService.login(username,password).enqueue(new Callback<ResObj>() {
            @Override
            public void onResponse(Call<ResObj> call, Response<ResObj> response) {
                if (response.isSuccessful()){
                    ResObj resObj = response.body();
                    if (resObj.getMessage().equals("true")){
                        //login start main activity
                        Intent intent = new Intent(MainLogin.this,MainActivity.class);
                        intent.putExtra("username",resObj.getIdUser());
                        intent.putExtra("UsName",resObj.getUsName());
                        intent.putExtra("WgID",resObj.getWgID());
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainLogin.this, "The username or password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainLogin.this, "Error Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResObj> call, Throwable t) {
                Toast.makeText(MainLogin.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
