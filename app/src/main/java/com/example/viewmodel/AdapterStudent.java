package com.example.viewmodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.model.Student;
import com.example.th_anroid_buoi6.R;

import java.util.List;

public class AdapterStudent extends ArrayAdapter<Student> {
    Context context;
    int resource;
    List<Student> objects;

    public AdapterStudent(@NonNull Context context, int resource, @NonNull List<Student> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.item_student, parent, false);
        ImageView imageAvatar = row.findViewById(R.id.imageAvatar);
        TextView textViewName = row.findViewById(R.id.textViewName);
        TextView textViewAddress = row.findViewById(R.id.textViewAddress);

        Student student = objects.get(position);
        textViewName.setText(student.getStudentName());
        textViewAddress.setText(student.getStudentAddress());
        int resID = student.isMale() ? R.drawable.male : R.drawable.female;
        imageAvatar.setImageResource(resID);
        return row;
    }
}
