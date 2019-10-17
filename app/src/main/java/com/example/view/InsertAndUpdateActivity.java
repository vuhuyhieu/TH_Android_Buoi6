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

public class InsertAndUpdateActivity extends AppCompatActivity {
    public static final int MODE_CREATE = 1;
    public static final int MODE_UPĐATE = 2;
    int mode;
    EditText editTextName, editTextAddress;
    Button btnOk, btnCancel;
    RadioButton radioMale, radioFemale;
    ImageButton buttonSelectImage;
    Intent intent;
    Student student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_and_update_student);
        getData();
        initView();
        initEvent();
    }

    private void getData() {
        intent = getIntent();
        if (intent.hasExtra("data")){
            Bundle bundle = intent.getBundleExtra("data");
            student = (Student) bundle.getSerializable("student");
        }
    }

    private void initEvent() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode==MODE_CREATE) {
                    addNewStudent();
                }else {
                    updateStudent(student);
                }
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

    private void updateStudent(Student student) {
        student.setStudentName(editTextName.getText().toString());
        student.setStudentAddress(editTextAddress.getText().toString());
        Drawable image = buttonSelectImage.getBackground();
        student.setStudentAvatar(String.valueOf(image));
        boolean gender;
        if (radioFemale.isChecked()){
            gender = false;
            buttonSelectImage.setBackgroundResource(R.drawable.female);
        }else {
            gender = true;
        }
        student.setStudentGender(gender);

        Bundle bundle = new Bundle();
        bundle.putSerializable("student", student);
        intent.putExtra("data", bundle);
        setResult(MainActivity.UPDATE_ITEM, intent);
        finish();
    }

    private void addNewStudent() {
        Student student = new Student();
        student.setStudentName(editTextName.getText().toString());
        student.setStudentAddress(editTextAddress.getText().toString());
        Drawable image = buttonSelectImage.getBackground();
        student.setStudentAvatar(String.valueOf(image));
        boolean gender;
        if (radioFemale.isChecked()){
            gender = false;
            buttonSelectImage.setBackgroundResource(R.drawable.female);
        }else {
            gender = true;
        }
        student.setStudentGender(gender);

        Bundle bundle = new Bundle();
        bundle.putSerializable("student", student);
        intent.putExtra("data", bundle);
        setResult(MainActivity.CREATE_ITEM, intent);
        finish();
    }

    private void initView() {
        String title;
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextName = findViewById(R.id.editTextName);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        btnOk = findViewById(R.id.btnOk);
        btnCancel = findViewById(R.id.btnCancel);
        buttonSelectImage = findViewById(R.id.buttonInsertAvatar);

        if (student!=null){
            mode = MODE_UPĐATE;
            editTextName.setText(student.getStudentName());
            editTextAddress.setText(student.getStudentName());
            if (student.isMale()){
                radioMale.setChecked(true);
                buttonSelectImage.setBackgroundResource(R.drawable.male);
            }else {
                radioFemale.setChecked(true);
                buttonSelectImage.setBackgroundResource(R.drawable.female);
            }
            title="Sửa thông tin";
        }else {
            mode = MODE_CREATE;
            title="Thêm sinh viên";
        }
        this.setTitle(title);
    }
}
