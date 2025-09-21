package com.example.recyclerviewexersices;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView; // Assuming you have a TextView in your item layout

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List; // Assuming your data is a List

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Person> personList;

    public interface OnItemDeleteListener {
        void onItemDelete(Person person, int position);
    }

    // Interface for item click
    public interface OnItemClickListener {
        void onItemClick(Person person, int position);
    }

    private OnItemDeleteListener deleteListener;
    private OnItemClickListener itemClickListener;

    // Constructor to pass in your data
    public Adapter(List<Person> dataList, StudentListFragment StudentListFragment) {
        this.personList = dataList;
        this.deleteListener = StudentListFragment; // Assign the listener
        this.itemClickListener = StudentListFragment; // Assign the listener
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate your item layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_person, parent, false);
        return new MyViewHolder(itemView); // Corrected to use MyViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Person currentPerson = personList.get(position);

        // Bind data to the ViewHolder's views
        holder.name.setText(currentPerson.getName());
        holder.age.setText(String.valueOf(currentPerson.getAge()));
        holder.city.setText(currentPerson.getCity());
        holder.isStudent.setText(currentPerson.getIsStudent() ? "Yes" : "No");

        holder.deleteButton.setOnClickListener(v -> {
            // Correct way: Get the position at the time of the click
            int adapterPosition = holder.getBindingAdapterPosition();

            // Check for a valid position to prevent errors
            if (deleteListener != null && adapterPosition != RecyclerView.NO_POSITION) {
                deleteListener.onItemDelete(personList.get(adapterPosition), adapterPosition);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getBindingAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    itemClickListener.onItemClick(personList.get(adapterPosition), adapterPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        // Return the size of your dataset
        return personList == null ? 0 : personList.size();
    }


    public void removeItem(int position) {
        if (position >= 0 && position < personList.size()) {
            personList.remove(position);
            notifyItemRemoved(position);
            // Optional: To update positions of subsequent items if your IDs/logic depends on it
            // This is important if you have stable IDs or other logic that depends on absolute positions.
            notifyItemRangeChanged(position, personList.size() - position);
        }
    }

    /**
     * ViewHolder class
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // Declare views for your item
        public TextView name;
        public TextView age;
        public TextView city;
        public TextView isStudent;
        public ImageButton deleteButton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views
            name = itemView.findViewById(R.id.tv_name);
            age = itemView.findViewById(R.id.tv_age);
            city = itemView.findViewById(R.id.tv_city);
            isStudent = itemView.findViewById(R.id.tv_isStudent);
            deleteButton = itemView.findViewById(R.id.btn_delete);
        }
    }
}
