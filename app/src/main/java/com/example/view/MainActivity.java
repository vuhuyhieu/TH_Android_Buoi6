package com.example.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.model.Student;
import com.example.th_anroid_buoi6.R;
import com.example.viewmodel.AdapterStudent;
import com.example.viewmodel.DatabaseStudent;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listViewStudent;
    ArrayList<Student> listStudent;
    AdapterStudent adapterStudent;
    DatabaseStudent dbStudent;
    Student selectedStudent;
    public static final int OPEN_CREATE_ITEM_ACTIVITY = 111;
    public static final int OPEN_UPDATE_ITEM_ACTIVITY = 222;
    public static final int CREATE_ITEM = 11;
    public static final int UPDATE_ITEM = 22;
    public static final int DELETE_ITEM = 33;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    private void initEvent() {
        listViewStudent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedStudent = listStudent.get(position);
                return false;
            }
        });
    }

    private void initView() {
        dbStudent = new DatabaseStudent(this);
        if (dbStudent.getStudentsCount()==0){
            dbStudent.initThreeStudent();
        }
        listViewStudent = findViewById(R.id.listViewStudent);
        listStudent = new ArrayList<>();
        loadStudent();
        adapterStudent = new AdapterStudent(this,R.layout.item_student,listStudent);
        listViewStudent.setAdapter(adapterStudent);
        registerForContextMenu(listViewStudent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_add_new_student: openInsertAndUpdateActivity(); break;
        }
        return true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_update_student: openInsertAndUpdateActivity();break;
            case R.id.item_delete_student: deleteStudent();break;
        }
        return true;
    }

    private void deleteStudent() {

    }


    private void openInsertAndUpdateActivity() {
        Intent intent = new Intent(MainActivity.this, InsertAndUpdateActivity.class);
        Bundle bundle = new Bundle();
        if (selectedStudent!=null){
            bundle.putSerializable("student", selectedStudent);
            intent.putExtra("data", bundle);
        }
        startActivityForResult(intent, OPEN_CREATE_ITEM_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==MainActivity.CREATE_ITEM){
            Bundle bundle = data.getBundleExtra("data");
            Student student = (Student) bundle.getSerializable("student");
            dbStudent.addStudent(student);
            loadStudent();
            adapterStudent.notifyDataSetChanged();
        }else if (requestCode==MainActivity.UPDATE_ITEM){
            Bundle bundle = data.getBundleExtra("data");
            Student student = (Student) bundle.getSerializable("student");
            dbStudent.updateStudent(student);
            loadStudent();
            adapterStudent.notifyDataSetChanged();
        }
    }

    public void loadStudent(){
        listStudent = dbStudent.getStudentList();
    }
}
