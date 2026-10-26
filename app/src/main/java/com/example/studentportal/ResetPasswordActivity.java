package com.example.studentportal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class ResetPasswordActivity extends AppCompatActivity {

    private static final String PREF_NAME = "StudentPortalPrefs";

    private EditText etNewPassword, etConfirmNewPassword;
    private Button btnUpdatePassword;
    private TextView tvBackToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_password);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom
            );
            return insets;
        });

        initViews();
        setupListeners();
    }

    private void initViews() {
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmNewPassword = findViewById(R.id.etConfirmNewPassword);
        btnUpdatePassword = findViewById(R.id.btnUpdatePassword);
        tvBackToLogin = findViewById(R.id.tvBackToLogin);
    }

    private void setupListeners() {
        btnUpdatePassword.setOnClickListener(v -> attemptPasswordUpdate());

        tvBackToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void attemptPasswordUpdate() {
        String newPassword = etNewPassword.getText().toString(); // Do NOT trim password
        String confirmPassword = etConfirmNewPassword.getText().toString(); // Do NOT trim confirmation password

        if (TextUtils.isEmpty(newPassword)) {
            etNewPassword.setError("New password is required");
            etNewPassword.requestFocus();
            return;
        }

        if (newPassword.length() < 8) {
            etNewPassword.setError("Password must be at least 8 characters");
            etNewPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            etConfirmNewPassword.setError("Please confirm your password");
            etConfirmNewPassword.requestFocus();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            etConfirmNewPassword.setError("Passwords do not match");
            etConfirmNewPassword.requestFocus();
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        String recoveryEmail = getIntent().getStringExtra("RECOVERY_EMAIL");
        if (TextUtils.isEmpty(recoveryEmail)) {
            Toast.makeText(this, "Error: Recovery email not found.", Toast.LENGTH_SHORT).show();
            return;
        }

        String normalizedEmail = recoveryEmail.toLowerCase(Locale.ROOT);
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Check if account exists (demo user or registered user in prefs)
        boolean isDemo = normalizedEmail.equals("student@portal.edu");
        boolean isRegistered = prefs.contains("pass_" + normalizedEmail);

        if (!isDemo && !isRegistered) {
            Toast.makeText(this, "No account found associated with this email.", Toast.LENGTH_LONG).show();
            return;
        }

        // Save updated password to SharedPreferences
        boolean success = prefs.edit()
                .putString("pass_" + normalizedEmail, newPassword)
                .commit();

        if (success) {
            Toast.makeText(this, "Password updated successfully! Please login.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
            intent.putExtra("REGISTERED_EMAIL", recoveryEmail);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Failed to update password. Please try again.", Toast.LENGTH_LONG).show();
        }
    }
}
