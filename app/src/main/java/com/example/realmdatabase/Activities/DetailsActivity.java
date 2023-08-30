package com.example.realmdatabase.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.realmdatabase.Model.Person;
import com.example.realmdatabase.R;
import com.example.realmdatabase.databinding.ActivityDetailsBinding;

import io.realm.Realm;

public class DetailsActivity extends AppCompatActivity {
    ActivityDetailsBinding binding;
    Realm realm;
    Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        realm = Realm.getDefaultInstance();

        int position = getIntent().getIntExtra("numPosition",0);

        person = realm.where(Person.class).equalTo("person_id",position).findFirst();
        binding.detailsEditText.setText(person.getPerson_name());

        binding.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm.beginTransaction();
                person.setPerson_name(binding.detailsEditText.getText().toString());
                realm.commitTransaction();

                onBackPressed();
            }
        });
        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        person.deleteFromRealm();
                        onBackPressed();
                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}