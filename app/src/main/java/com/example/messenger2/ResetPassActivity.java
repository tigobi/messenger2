package com.example.messenger2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class ResetPassActivity extends AppCompatActivity {
    private static final String EXTRA_EMAIL = "email";
    private EditText editTextEmail;
    private Button buttonResetPassword;
    private ResetPassViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        initViews();
        viewModel = new ViewModelProvider(this).get(ResetPassViewModel.class);
        observeViewModel();
        String email = getIntent().getStringExtra(EXTRA_EMAIL);
        editTextEmail.setText(email);
        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                viewModel.resetPassword(email);
            }
        });
    }

    private void observeViewModel() {
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                Toast.makeText(ResetPassActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.getSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean success) {
                Toast.makeText(ResetPassActivity.this, R.string.success_reset_message, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void initViews() {
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonResetPassword = findViewById(R.id.buttonResetPassword);
    }

    public static Intent newIntent(Context context, String email) {
        Intent intent = new Intent(context, ResetPassActivity.class);
        intent.putExtra(EXTRA_EMAIL, email);
        return intent;


    }
}