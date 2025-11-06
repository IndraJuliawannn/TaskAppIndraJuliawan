package com.informatika.taskappindrajuliawan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.informatika.taskappindrajuliawan.model.DataManager;
import com.informatika.taskappindrajuliawan.model.Task;

public class AddTaskActivity extends AppCompatActivity {

    private TextInputEditText etTitle, etDeadline;
    private Button btnSave;

    private int editIndex = -1; // -1 = mode tambah, >=0 = mode edit

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);


        etTitle = findViewById(R.id.ettitle);
        etDeadline = findViewById(R.id.etdeadline);
        btnSave = findViewById(R.id.btnsave);


        Log.d("AddTaskActivity", "Intent has edit_index? " + getIntent().hasExtra("edit_index"));
        if (getIntent().hasExtra("edit_index")) {
            editIndex = getIntent().getIntExtra("edit_index", -1);
            Task task = DataManager.taskList.get(editIndex);
            etTitle.setText(task.title);
            etDeadline.setText(task.deadline);
            btnSave.setText("Simpan Perubahan");
        }

        btnSave.setOnClickListener(v -> saveTask());
    }

    private void saveTask() {
        String title = etTitle.getText().toString().trim();
        String deadline = etDeadline.getText().toString().trim();

        if (title.isEmpty() || deadline.isEmpty()) {
            Toast.makeText(this, "Judul dan deadline tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Simpan ke daftar global
        if (editIndex == -1) {
            // Mode tambah
            DataManager.taskList.add(new Task(title, deadline));
            Toast.makeText(this, "Tugas ditambahkan!", Toast.LENGTH_SHORT).show();
        } else {
            // Mode edit
            Task updated = new Task(title, deadline);
            DataManager.taskList.set(editIndex, updated);
            Toast.makeText(this, "Tugas diperbarui!", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}
