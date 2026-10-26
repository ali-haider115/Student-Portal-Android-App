# Student Portal - Android Application

A comprehensive native Android application built with Java and XML layout resources. The application features a clean, modern Material 3 blue-and-white design system with full Light Mode and Dark Mode (DayNight) support, persistent local authentication, session security, and explicit navigation.

---

## 📱 App Screens & Features

1. **Splash Screen** (`SplashActivity.java` | `activity_splash.xml`)
   - Displays the application logo, title, and tagline with smooth transition timing.
   - Automatically navigates to the Login screen after a 2-second delay.

2. **Login Screen** (`LoginActivity.java` | `activity_login.xml`)
   - Allows students to log in using demo credentials or newly registered accounts.
   - Input validation for empty fields and email format.
   - Automatically pre-fills the email field after successful registration.

3. **Registration Screen** (`RegisterActivity.java` | `activity_register.xml`)
   - Account creation form accepting Full Name, Email, Password, and Confirm Password.
   - Validates required fields, email formatting, password length (minimum 8 characters), password matching, and duplicate account checks.
   - Saves credentials persistently to `SharedPreferences`.

4. **Forgot Password Screen** (`ForgotPasswordActivity.java` | `activity_forgot_password.xml`)
   - Accepts the registered student email or ID and initiates the recovery flow.
   - Passes the account identifier to the Reset Password screen via explicit Intent extras.

5. **Reset Password Screen** (`ResetPasswordActivity.java` | `activity_reset_password.xml`)
   - Accepts a new password and confirmation, checking minimum length and matching passwords.
   - Updates local persistent storage and rejects old passwords upon future login attempts.

6. **Dashboard Screen** (`DashboardActivity.java` | `activity_dashboard.xml`)
   - Displays a personalized welcome banner with the student's name.
   - Contains 6 interactive feature cards: **Courses**, **Attendance**, **Assignments**, **Results**, **Timetable**, and **Profile**.
   - Feature cards display interactive module summary dialogs, while the Profile card opens the Profile screen.

7. **Profile Screen** (`ProfileActivity.java` | `activity_profile.xml`)
   - Displays the logged-in student's saved profile details (Name, Email, Student ID, Department, and Semester).
   - Includes a secure Logout button that terminates the session and clears the activity stack (`FLAG_ACTIVITY_CLEAR_TASK`).

---

## 📐 Layout Types & Demonstrations

The project demonstrates all three core Android layout paradigms across its XML resources:

| Layout Type | Layout Files | Description & Role |
| :--- | :--- | :--- |
| **`LinearLayout`** | `activity_login.xml`<br>`activity_register.xml`<br>`activity_forgot_password.xml`<br>`activity_reset_password.xml` | Forms and linear input flows structured with vertical/horizontal linear orientations and weighted text views (`layout_weight="1"`). |
| **`GridLayout`** | `activity_dashboard.xml` | Arranges the 6 dashboard feature cards in a clean, responsive 2-column by 3-row grid with uniform row and column weights. |
| **`ConstraintLayout`** | `activity_profile.xml`<br>`activity_splash.xml`<br>`activity_main.xml` | Positions elements relative to each other using constraints, guidelines, and vertical chains for flexible profile and splash compositions. |

---

## 🎨 UI Design System & Theme

- **Primary Blue Palette**:
  - Primary Blue: `#0C4DA1`
  - Dark Blue: `#083777`
  - Light Blue Container: `#EAF1FB`
  - White: `#FFFFFF`
  - Main Text: `#172B4D`
  - Secondary Text: `#64748B`
  - Input Background: `#F5F8FD`
  - Input Border: `#D5DFEF`
- **DayNight / Dark Mode Support**:
  - Light mode values in `res/values/colors.xml` and `res/values/themes.xml`.
  - Dark mode values in `res/values-night/colors.xml` and `res/values-night/themes.xml`.
  - Layouts use dynamic attribute references (`?attr/colorSurface`, `?attr/colorOnSurface`, `?attr/colorPrimary`, `?attr/colorOutline`) to maintain contrast and readability in dark mode.
- **Insets & Scrollability**:
  - Edge-to-edge window insets listener (`ViewCompat.setOnApplyWindowInsetsListener`) prevents content from overlapping status or navigation bars.
  - `ScrollView` with `fillViewport="true"` and `windowSoftInputMode="adjustResize"` ensures form inputs remain accessible when the soft keyboard is active.

---

## 🔐 Local Authentication & Storage

- **Persistence**: Uses `SharedPreferences` (`StudentPortalPrefs`) for simulated local account registration, credential validation, and password updates.
- **Session Security**: Validates `USER_EMAIL` session extras on `DashboardActivity`. Unauthenticated attempts are redirected to `LoginActivity`.
- **Demo Credentials**:
  - **Email**: `student@portal.edu`
  - **Password**: `password123`

---

## 🛠️ Build & Technical Specifications

- **Language**: Java 11
- **Minimum SDK**: API 24 (Android 7.0)
- **Target SDK**: API 35 (Android 15)
- **Build Tool**: Gradle (`./gradlew :app:assembleDebug`)

### Running the App
1. Open the project in Android Studio.
2. Sync Gradle dependencies.
3. Select an emulator or physical device running Android 7.0 (API 24) or higher.
4. Click **Run** (`Shift + F10`) or execute `./gradlew :app:assembleDebug`.
