package com.example.recyclerviewexersices;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast; // For user feedback

import androidx.appcompat.app.AppCompatActivity; // Correct import
// Remove EdgeToEdge if not strictly needed or causing issues
// import androidx.activity.EdgeToEdge;
// import androidx.core.graphics.Insets;
// import androidx.core.view.ViewCompat;
// import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etName, etAge, etCity;
    CheckBox cbIsStudent;
    Button addButton, viewButton; // Kept your variable names

    // This list will hold the Person objects.
    // Static for easy access from PersonListActivity (simplification for this example).
    public static ArrayList<Person> personList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge.enable(this); // Optional
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, HomeFragment())
                    .commit()
        }

        initViews();
        setClickListeners(); // It's good practice to set listeners after views are initialized
    }

    private void initViews() {
        etName = findViewById(R.id.et_name);
        etAge = findViewById(R.id.et_age);
        etCity = findViewById(R.id.et_city);
        cbIsStudent = findViewById(R.id.cb_student);

        addButton = findViewById(R.id.btn_add_to_list);
        viewButton = findViewById(R.id.btn_view_list);
    }

    private void setClickListeners() {
        if (addButton != null) {
            addButton.setOnClickListener(this);
        }
        if (viewButton != null) {
            viewButton.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == R.id.btn_add_to_list) {
            addCurrentPersonToList();
        } else if (viewId == R.id.btn_view_list) {
            viewPersonList();
        }
    }

    private void addCurrentPersonToList() {
        String name = etName.getText().toString().trim();
        String ageStr = etAge.getText().toString().trim();
        String city = etCity.getText().toString().trim();
        boolean isStudent = cbIsStudent.isChecked();

        // --- Input Validation ---
        if (name.isEmpty()) {
            etName.setError("Name cannot be empty");
            etName.requestFocus();
            return;
        }
        if (ageStr.isEmpty()) {
            etAge.setError("Age cannot be empty");
            etAge.requestFocus();
            return;
        }
        if (city.isEmpty()) {
            city = "N/A"; // Default value
        }

        int age;
        try {
            age = Integer.parseInt(ageStr);
            if (age <= 0 || age > 150) {
                etAge.setError("Please enter a valid age");
                etAge.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            etAge.setError("Please enter a valid number for age");
            etAge.requestFocus();
            return;
        }

        // --- Create Person object ---
        Person newPerson = new Person(name, age, city, isStudent);

        // --- Add to list ---
        personList.add(newPerson);
        Toast.makeText(this, name + " added to the list!", Toast.LENGTH_SHORT).show();

        // --- Clear input fields for next entry ---
        etName.setText("");
        etAge.setText("");
        etCity.setText("");
        cbIsStudent.setChecked(false);
        etName.requestFocus(); // Set focus back to the name field
    }

    private void viewPersonList() {
        if (personList.isEmpty()) {
            Toast.makeText(this, "The list is empty. Add some people first.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }
}
