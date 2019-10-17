package com.example.viewmodel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.model.Student;

import java.util.ArrayList;

public class DatabaseStudent extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "StudentManagement";
    public static final String TABLE_NAME = "Student";
    public static final String COLUMN_STUDENT_ID = "ID";
    public static final String COLUMN_STUDENT_NAME = "Name";
    public static final String COLUMN_STUDENT_GENDER = "Gender";
    public static final String COLUMN_STUDENT_ADDRESS = "Address";
    public static final String COLUMN_STUDENT_AVATAR = "Avatar";

    Context context;

    public DatabaseStudent(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createStatement = "create table " + TABLE_NAME + " ( "
                + COLUMN_STUDENT_ID + " integer primary key autoincrement, "
                + COLUMN_STUDENT_NAME + " TEXT, "
                + COLUMN_STUDENT_ADDRESS + " TEXT, "
                + COLUMN_STUDENT_AVATAR + " TEXT, "
                + COLUMN_STUDENT_GENDER + " TEXT )";
        db.execSQL(createStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public void initThreeStudent() {
        ArrayList<Student> list = new ArrayList();
        Student student1 = new Student("Vũ Huy Hiếu", "190 Lò Đúc, Hà Nội", true, "");
        Student student2 = new Student("Nguyễn Hoài Thu", "Thủ Đức, TP HCM", false, "");
        Student student3 = new Student("Nguyễn Thu Hà", "256 Trần Đại Nghĩa, Hà Nội", false, "");
        list.add(student1);
        list.add(student2);
        list.add(student3);
        for (int i = 0; i < 3; i++) {
            addStudent(list.get(i));
        }
    }

    public void addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String studentGender = "Male";
        if (!student.isMale()) {
            studentGender = "Female";
        }
        values.put(COLUMN_STUDENT_NAME, student.getStudentName());
        values.put(COLUMN_STUDENT_ADDRESS, student.getStudentAddress());
        values.put(COLUMN_STUDENT_AVATAR, student.getStudentAvatar());
        values.put(COLUMN_STUDENT_GENDER, studentGender);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Student getStudent(int studentID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{
                        COLUMN_STUDENT_ID, COLUMN_STUDENT_NAME, COLUMN_STUDENT_ADDRESS,
                        COLUMN_STUDENT_AVATAR, COLUMN_STUDENT_GENDER
                }, COLUMN_STUDENT_ID + " =?",
                new String[]{String.valueOf(studentID)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Student student = new Student();
        student.setStudentID(String.valueOf(studentID));
        student.setStudentName(cursor.getString(1));
        student.setStudentAddress(cursor.getString(2));
        student.setStudentAvatar(cursor.getString(3));
        boolean gender = true;
        if (cursor.getString(4).equals("Female")) {
            gender = false;
        }
        student.setStudentGender(gender);
        cursor.close();
        return student;
    }

    public ArrayList<Student> getStudentList() {
        ArrayList<Student> listStudent = new ArrayList<>();
        String selectQuery = "select * from " + TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String studentID = cursor.getString(0);
                String studentName = cursor.getString(1);
                String studentAddress = cursor.getString(2);
                String studentAvatar = cursor.getString(3);
                String gender = cursor.getString(4);
                boolean studentGender = true;
                if (gender.equals("Female")) {
                    studentGender = false;
                }
                listStudent.add(new Student(studentID, studentName, studentAddress, studentGender, studentAvatar));

            } while (cursor.moveToNext());
        }
        return listStudent;
    }

    public int getStudentsCount() {
        int count = 0;
        String selectQuery = "select * from " + TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            count = cursor.getCount();
        }
        cursor.close();
        return count;
    }

    public int updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String studentGender = "Male";
        if (!student.isMale()) {
            studentGender = "Female";
        }
        values.put(COLUMN_STUDENT_NAME, student.getStudentName());
        values.put(COLUMN_STUDENT_ADDRESS, student.getStudentAddress());
        values.put(COLUMN_STUDENT_AVATAR, student.getStudentAvatar());
        values.put(COLUMN_STUDENT_GENDER, studentGender);
        return db.update(TABLE_NAME, values, COLUMN_STUDENT_ID + " = ? ",
                new String[]{student.getStudentID()});
    }

    public void deleteStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_STUDENT_ID + " = ?", new String[]{student.getStudentID()});
        db.close();
    }
}
