package com.example.sabyapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText etNama;
    private EditText etEmail;
    private EditText etPass;
    private EditText etKonfirmPass;
    private Button btnDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mAuth = FirebaseAuth.getInstance();

        etNama = findViewById(R.id.namareg);
        etEmail = findViewById(R.id.emailreg);
        etPass = findViewById(R.id.pwreg);
        etKonfirmPass = findViewById(R.id.konfirmpw);
        btnDaftar = findViewById(R.id.btnregis);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPass.getText().toString().trim();
                String confirmPassword = etKonfirmPass.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Konfirmasi password tidak cocok", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(RegisterActivity.this, "Registrasi berhasil",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(RegisterActivity.this, "Registrasi gagal",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        TextView alreadyHaveAccountTextView = findViewById(R.id.alreadyHaveAccountTextView);
        String text = "Sudah Punya akun? Masuk";

        SpannableString spannableString = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        };

        // Set color to tosca for "Masuk"
        int colorTosca = ContextCompat.getColor(RegisterActivity.this, R.color.tosca);
        spannableString.setSpan(clickableSpan, 18, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(colorTosca), 18, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new UnderlineSpan(), 18, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set color to navy for the rest of the text
        int colorNavy = ContextCompat.getColor(RegisterActivity.this, R.color.navy);
        spannableString.setSpan(new ForegroundColorSpan(colorNavy), 0, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        alreadyHaveAccountTextView.setText(spannableString);
        alreadyHaveAccountTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
