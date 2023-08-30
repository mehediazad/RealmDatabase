package com.example.realmdatabase.Helper;

import com.example.realmdatabase.Model.Person;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class MyHelper {
    Realm realm;
    RealmResults<Person> person;

    public MyHelper(Realm realm) {
        this.realm = realm;
    }
    public void selectFromDB(){
        person = realm.where(Person.class).findAll();
    }
    public ArrayList<Person> justRefresh(){
        ArrayList<Person> listItem = new ArrayList<>();
        for (Person p : person){
            listItem.add(p);
        }

        return listItem;
    }
}
