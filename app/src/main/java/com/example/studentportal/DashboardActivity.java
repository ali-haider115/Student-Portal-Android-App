package com.example.studentportal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DashboardActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private Button btnLogout;
    private LinearLayout cardCourses, cardAttendance, cardAssignments, cardResults, cardTimetable, cardProfile;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            float density = getResources().getDisplayMetrics().density;
            int padH = (int) (20 * density);
            int padV = (int) (16 * density);
            v.setPadding(
                    padH + systemBars.left,
                    padV + systemBars.top,
                    padH + systemBars.right,
                    padV + systemBars.bottom
            );
            return insets;
        });

        initViews();
        
        // Prevent dashboard access without a valid simulated login session
        if (!validateSession()) {
            return;
        }

        setupWelcomeMessage();
        setupListeners();
    }

    private void initViews() {
        tvWelcome = findViewById(R.id.tvWelcome);
        btnLogout = findViewById(R.id.btnLogout);
        cardCourses = findViewById(R.id.cardCourses);
        cardAttendance = findViewById(R.id.cardAttendance);
        cardAssignments = findViewById(R.id.cardAssignments);
        cardResults = findViewById(R.id.cardResults);
        cardTimetable = findViewById(R.id.cardTimetable);
        cardProfile = findViewById(R.id.cardProfile);
    }

    private boolean validateSession() {
        userEmail = getIntent().getStringExtra("USER_EMAIL");
        if (userEmail == null || userEmail.trim().isEmpty()) {
            Toast.makeText(this, "Unauthorized access. Please login first.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return false;
        }
        return true;
    }

    private void setupWelcomeMessage() {
        String userName = "Student";
        if (userEmail != null && userEmail.contains("@")) {
            String prefix = userEmail.substring(0, userEmail.indexOf("@"));
            if (!prefix.isEmpty()) {
                userName = Character.toUpperCase(prefix.charAt(0)) + prefix.substring(1);
            }
        }
        tvWelcome.setText(getString(R.string.dashboard_welcome, userName));
    }

    private void setupListeners() {
        btnLogout.setOnClickListener(v -> performLogout());

        cardCourses.setOnClickListener(v -> showModuleDialog("Courses", "Enrolled Courses:\n• Data Structures & Algorithms\n• Operating Systems\n• Mobile Application Development\n• Database Systems"));
        
        cardAttendance.setOnClickListener(v -> showModuleDialog("Attendance", "Attendance Summary:\n• Overall Attendance: 92%\n• Status: Good Standing\n• Total Classes Attended: 46 / 50"));
        
        cardAssignments.setOnClickListener(v -> showModuleDialog("Assignments", "Pending Assignments:\n1. Mobile App UI Wireframe (Due: Friday)\n2. Database ER Diagram (Due: Next Monday)"));
        
        cardResults.setOnClickListener(v -> showModuleDialog("Results", "Academic Results (GPA):\n• Current Semester GPA: 3.82\n• Cumulative GPA: 3.75\n• Status: Dean's Honor List"));
        
        cardTimetable.setOnClickListener(v -> showModuleDialog("Timetable", "Today's Schedule:\n• 09:00 AM - Data Structures (Hall A)\n• 11:00 AM - Mobile Dev Lab (Lab 3)\n• 02:00 PM - Operating Systems (Room 204)"));

        cardProfile.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
            intent.putExtra("USER_EMAIL", userEmail);
            startActivity(intent);
        });
    }

    private void showModuleDialog(String moduleName, String details) {
        new AlertDialog.Builder(this)
                .setTitle(moduleName + " Module")
                .setMessage(details)
                .setPositiveButton(R.string.dialog_btn_ok, (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void performLogout() {
        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
