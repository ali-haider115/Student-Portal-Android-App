package com.example.studentportal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    private static final String PREF_NAME = "StudentPortalPrefs";

    private TextView tvProfileName, tvProfileEmail, tvProfileId, tvProfileDepartment, tvProfileSemester;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

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
        loadUserData();
        setupListeners();
    }

    private void initViews() {
        tvProfileName = findViewById(R.id.tvProfileName);
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        tvProfileId = findViewById(R.id.tvProfileId);
        tvProfileDepartment = findViewById(R.id.tvProfileDepartment);
        tvProfileSemester = findViewById(R.id.tvProfileSemester);
        btnLogout = findViewById(R.id.btnLogout);
    }

    private void loadUserData() {
        String email = getIntent().getStringExtra("USER_EMAIL");
        if (email == null || email.isEmpty()) {
            email = "student@portal.edu";
        }

        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String normalizedEmail = email.toLowerCase(Locale.ROOT);
        String savedName = prefs.getString("name_" + normalizedEmail, null);

        String name = "Alex Johnson";
        if (savedName != null && !savedName.isEmpty()) {
            name = savedName;
        } else if (email.contains("@")) {
            String prefix = email.substring(0, email.indexOf("@"));
            if (!prefix.isEmpty()) {
                name = Character.toUpperCase(prefix.charAt(0)) + prefix.substring(1).replace('.', ' ');
            }
        }

        tvProfileName.setText(name);
        tvProfileEmail.setText(getString(R.string.label_email, email));
        tvProfileId.setText("Student ID: ST–2023–1042");
        tvProfileDepartment.setText("Department: Computer Science");
        tvProfileSemester.setText("Semester: 5th Semester");
    }

    private void setupListeners() {
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
