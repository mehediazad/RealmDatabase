package com.example.realmdatabase.Activities.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realmdatabase.Activities.DetailsActivity;
import com.example.realmdatabase.Activities.MainActivity;
import com.example.realmdatabase.Model.Person;
import com.example.realmdatabase.R;
import com.example.realmdatabase.databinding.ItemListBinding;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    Context context;
    ArrayList<Person> personList;


    public CustomAdapter(Context context, ArrayList<Person> personList) {
        this.context = context;
        this.personList = personList;
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemListBinding itemListBinding = ItemListBinding.inflate(inflater, parent, false);
        return new ViewHolder(itemListBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        Person person = personList.get(position);

        final int numPosition = person.getPerson_id();

        holder.itemListBinding.textViewName.setText(person.getPerson_name());

        holder.itemListBinding.nameCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("numPosition",numPosition);

                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemListBinding itemListBinding;

        public ViewHolder(@NonNull ItemListBinding itemListBinding) {
            super(itemListBinding.getRoot());
            this.itemListBinding = itemListBinding;
        }
    }

}
