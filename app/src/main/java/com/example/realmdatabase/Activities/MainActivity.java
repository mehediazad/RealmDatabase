package com.example.realmdatabase.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.realmdatabase.Activities.adapters.CustomAdapter;
import com.example.realmdatabase.Model.Person;
import com.example.realmdatabase.Helper.MyHelper;
import com.example.realmdatabase.R;
import com.example.realmdatabase.databinding.ActivityMainBinding;

import io.realm.Realm;
import io.realm.RealmChangeListener;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    RecyclerView recyclerView;
    MyHelper helper;
    RealmChangeListener realmChangeListener;
    CustomAdapter customAdapter;
    Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        realm = Realm.getDefaultInstance();
        recyclerView = binding.recyclerview;


        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();

            }
        });

        helper = new MyHelper(realm);
        helper.selectFromDB();
        setAdapter();
        Refresh();

    }

    private void Refresh() {
        realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object o) {
                CustomAdapter adapter = new CustomAdapter(MainActivity.this, helper.justRefresh());
                recyclerView.setAdapter(adapter);
            }
        };
        realm.addChangeListener(realmChangeListener);
    }

    private void setAdapter() {
        CustomAdapter adapter = new CustomAdapter(this, helper.justRefresh());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    private void saveData() {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Number maxID = bgRealm.where(Person.class).max("person_id");
//                int newKey = (maxID == null) ? 1 : maxID.intValue() + 1;

                int newKey;
                if (maxID == null) {
                    newKey = 1;
                } else {
                    newKey = maxID.intValue() + 1;
                }
                Person person = bgRealm.createObject(Person.class, newKey);
                person.setPerson_name(binding.editText.getText().toString());
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.removeChangeListener(realmChangeListener);
        realm.close();
    }
}