package com.example.recyclerviewexersices;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.Lifecycle;

public class StudentListFragment extends Fragment implements Adapter.OnItemDeleteListener, Adapter.OnItemClickListener {

    RecyclerView recyclerView;
    Adapter personAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find the RecyclerView using the inflated view
        recyclerView = view.findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Pass the list and the listener to the adapter
        personAdapter = new Adapter(MainActivity.personList, this);
        recyclerView.setAdapter(personAdapter);

        // Set up the MenuProvider for the Fragment's menu
        MenuHost menuHost = requireActivity();

        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.options_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.action_add_student) {
                    Toast.makeText(getContext(), "Add student clicked", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.action_delete_all) {
                    Toast.makeText(getContext(), "Delete all clicked", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    @Override
    public void onItemDelete(Person person, int position) {
        DialogInterface.OnClickListener deleteListener = (dialog, which) -> {
            personAdapter.removeItem(position);
            Toast.makeText(getContext(), person.getName() + " deleted", Toast.LENGTH_SHORT).show();
        };

        MyDialogHelper.showAlertDialogWithListener(
                getContext(),
                "Delete Item",
                "Are you sure you want to delete " + person.getName() + "?",
                deleteListener
        );
    }

    @Override
    public void onItemClick(Person person, int position) {
        DialogInterface.OnClickListener changeStatus = (dialog, which) -> {
            person.setStudent(!person.getIsStudent());
            personAdapter.notifyItemChanged(position);
            String status = person.getIsStudent() ? "active" : "inactive";
            Toast.makeText(getContext(), person.getName() + " is now " + status, Toast.LENGTH_SHORT).show();
        };

        MyDialogHelper.showAlertDialogWithListener(getContext(), "Change status", "Are you sure you want to change the status of " + person.getName() + "?", changeStatus);
    }
}