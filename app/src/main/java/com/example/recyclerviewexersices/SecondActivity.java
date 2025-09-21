package com.example.recyclerviewexersices;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;

public class SecondActivity extends AppCompatActivity implements Adapter.OnItemDeleteListener, Adapter.OnItemClickListener { // Implement the interface

    RecyclerView recyclerView;
    Adapter personAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Pass 'this' as the listener to the adapter's constructor
        personAdapter = new Adapter(MainActivity.personList, this);
        recyclerView.setAdapter(personAdapter);
    }

    // This method will be called from the adapter when a delete button is clicked
    @Override
    public void onItemDelete(Person person, int position) {
        DialogInterface.OnClickListener deleteListener = (dialog, which) -> {
            personAdapter.removeItem(position);
            Toast.makeText(SecondActivity.this, person.getName() + " deleted", Toast.LENGTH_SHORT).show();
        };

        MyDialogHelper.showAlertDialogWithListener(
                this,
                "Delete Item",
                "Are you sure you want to delete " + person.getName() + "?",
                deleteListener
        );
    }

    public void onItemClick(Person person, int position) {

        DialogInterface.OnClickListener changeStatus = (dialog, which) -> {
            person.setStudent(!person.getIsStudent());
            personAdapter.notifyItemChanged(position);
            String status = person.getIsStudent() ? "active" : "inactive";
            Toast.makeText(SecondActivity.this, person.getName() + " is now " + status, Toast.LENGTH_SHORT).show();
        };

            MyDialogHelper.showAlertDialogWithListener(this, "Change status", "Are you sure you want to change the status of " + person.getName() + "?", changeStatus);


        // Example: Start a new DetailActivity
        // Intent intent = new Intent(this, DetailActivity.class);
        // intent.putExtra("PERSON_NAME", person.getName()); // Pass data
        // startActivity(intent);
    }


    // Inflate the menu.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }
    // Handle menu item selections.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_student) {
            // do something for "Add student"
            return true;
        } else if (id == R.id.action_delete_all) {
            // do something for "Delete all"
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
