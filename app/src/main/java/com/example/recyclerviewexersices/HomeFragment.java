package com.example.recyclerviewexersices;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment implements View.OnClickListener {

    // Declare the views for the form
    EditText etName, etAge, etCity;
    CheckBox cbIsStudent;
    Button addButton; // We no longer need the viewButton here, as the BottomNav handles it.

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views using the fragment's view
        etName = view.findViewById(R.id.et_name);
        etAge = view.findViewById(R.id.et_age);
        etCity = view.findViewById(R.id.et_city);
        cbIsStudent = view.findViewById(R.id.cb_student);
        addButton = view.findViewById(R.id.btn_add_to_list);

        // Set the click listener on the button
        addButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.btn_add_to_list) {
            addCurrentPersonToList();
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
        MainActivity.personList.add(newPerson);
        Toast.makeText(getContext(), name + " added to the list!", Toast.LENGTH_SHORT).show();

        // --- Clear input fields for next entry ---
        etName.setText("");
        etAge.setText("");
        etCity.setText("");
        cbIsStudent.setChecked(false);
        etName.requestFocus(); // Set focus back to the name field
    }
}