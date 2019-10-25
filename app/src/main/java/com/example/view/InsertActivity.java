package com.example.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.example.model.Student;
import com.example.th_anroid_buoi6.R;

public class InsertActivity extends AppCompatActivity {
    EditText editTextName, editTextAddress;
    Button btnOk, btnCancel;
    RadioButton radioMale, radioFemale;
    ImageButton buttonSelectImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_student);
        initView();
        initEvent();
    }

    private void initEvent() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewStudent();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        radioFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelectImage.setBackgroundResource(R.drawable.female);
            }
        });
        radioMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelectImage.setBackgroundResource(R.drawable.male);
            }
        });
    }

    private void addNewStudent() {
        Intent intent = getIntent();
        Student student = new Student();
        student.setStudentName(editTextName.getText().toString());
        student.setStudentAddress(editTextAddress.getText().toString());
        student.setStudentGender(radioMale.isChecked() ? true : false);
        Bundle bundle = new Bundle();
        bundle.putSerializable("student", student);
        intent.putExtra("data", bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void initView() {
        String title = "Thêm sinh viên";
        this.setTitle(title);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextName = findViewById(R.id.editTextName);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        btnOk = findViewById(R.id.btnOk);
        btnCancel = findViewById(R.id.btnCancel);
        buttonSelectImage = findViewById(R.id.buttonInsertAvatar);
        radioMale.setChecked(true);
    }
}
