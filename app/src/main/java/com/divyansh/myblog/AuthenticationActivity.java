package com.divyansh.myblog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationActivity extends AppCompatActivity {

    EditText username,password;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        Button signInButton = findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFormValid()){
                    performSignIn();
                }
            }
        });

        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFormValid()){
                    //perform registration
                    performRegistration();
                }
            }
        });

        //initialising Progress Dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait");
    }

    private Boolean isFormValid(){
        if(username.getText().toString().trim().isEmpty()){
            Toast.makeText(this,"Username cannot be left empty",Toast.LENGTH_LONG).show();
            return false;
        }
        if(password.getText().toString().trim().isEmpty()){
            Toast.makeText(this,"Password cannot be left empty",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void performSignIn(){
        //new SignInTask().execute(username.getText().toString().trim(),password.getText().toString().trim());
        showProgressDialog(true);

        ApiManager.getApiInterface().login(new AuthenticationRequest(username.getText().toString().trim(),password.getText().toString().trim()))
                .enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        showProgressDialog(false);

                        if(response.isSuccessful()){
                            //ShowAlert("Welcome",response.body().getMessage().toString());
                            navigateToArticleListActivity();
                        }else {
                            try {
                                String errorMessage = response.errorBody().string();

                                try{
                                    ErrorResponse errorResponse = new Gson().fromJson(errorMessage,ErrorResponse.class);
                                    ShowAlert("SignIn Failed",errorResponse.getError());
                                }catch (JsonSyntaxException jsonException){
                                    jsonException.printStackTrace();
                                    ShowAlert("SignIn Failed","Something went wrong");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                ShowAlert("SignIn Failed","Something went wrong");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        ShowAlert("SignIn Failed","Something went wrong");
                    }
                });
    }

    private void performRegistration(){
        showProgressDialog(true);

        ApiManager.getApiInterface().registrartion(new AuthenticationRequest(username.getText().toString().trim(),password.getText().toString().trim()))
                .enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        showProgressDialog(false);

                        if(response.isSuccessful()){
                            ShowAlert("Welcome",response.body().getMessage());
                        }else {
                            try {
                                String errorMessage = response.errorBody().string();

                                try{
                                    ErrorResponse errorResponse = new Gson().fromJson(errorMessage,ErrorResponse.class);
                                    ShowAlert("Registration Failed",errorResponse.getError());
                                }catch (JsonSyntaxException jsonException){
                                    jsonException.printStackTrace();
                                    ShowAlert("Registration Failed","Something went wrong");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                ShowAlert("Registration Failed","Something went wrong");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        ShowAlert("Registration Failed","Something went wrong");
                    }
                });

    }

    private void showProgressDialog(Boolean shouldShow){
        if(shouldShow){
            progressDialog.show();
        }else{
            progressDialog.dismiss();
        }
    }

    private void ShowAlert(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    class SignInTask extends AsyncTask<String,Void,Boolean>{

        String mockUsername = "Divyansh";
        String mockPassword = "test";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog(true);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            String username = strings[0];
            String password = strings[1];

            //sleep the thread for 2 seconds to show the progress dialog box
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return username.contentEquals(mockUsername) && password.contentEquals(mockPassword);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            showProgressDialog(false);

            if (aBoolean) {
                ShowAlert("Welcome","You have successfully signed in");
            }else{
                ShowAlert("Failed","Username/Password is incorrect");
            }
        }
    }

    private void navigateToArticleListActivity(){
        Intent intent = new Intent(this,ArticleListActivity.class);
        startActivity(intent);
    }
}
