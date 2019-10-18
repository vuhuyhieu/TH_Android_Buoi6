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
import android.widget.Toast;

import com.example.model.Student;
import com.example.th_anroid_buoi6.R;
import com.example.viewmodel.DatabaseStudent;

public class InsertAndUpdateActivity extends AppCompatActivity {
    public static final int MODE_CREATE = 1;
    public static final int MODE_UPDATE = 2;
    boolean neededRefresh = false;
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
                neededRefresh=true;
                updateOrAddNewStudent(student);
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

    private void updateOrAddNewStudent(Student student) {
        DatabaseStudent db = new DatabaseStudent(this);
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

        if (mode==MODE_CREATE){
            db.addStudent(student);
        }else if (mode==MODE_UPDATE){
            db.updateStudent(student);
        }
        Toast.makeText(this, "Them thanh cong", Toast.LENGTH_LONG).show();
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
            mode = MODE_UPDATE;
            editTextName.setText(student.getStudentName());
            editTextAddress.setText(student.getStudentAddress());
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

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra("needRefresh", neededRefresh);
        this.setResult(MainActivity.RESULT_OK, data);
        super.finish();
    }
}
