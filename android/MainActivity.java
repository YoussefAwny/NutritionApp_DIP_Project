package com.example.spoon;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static MyDBHandler dbHandler;
    public static ArrayList<Profile> profiles;
    ListView profilesListView;
    public static ArrayList<String> names_strings;
    private static final String TAG = "mark";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout profile_list = (LinearLayout)findViewById(R.id.profile_list);
        dbHandler = new MyDBHandler(this, null, null, 1);
        profiles = new ArrayList<>();
        dbHandler.addProfile(new Profile(22, "mark", "Male"));
        dbHandler.addData(new Data(1,3,5,2019,2,2,3,3,3,3));
        dbHandler.addData(new Data(1,4,5,2019,1,1,1,1,1,1));
        //dbHandler.addProfile(new Profile(22, "Kiro", "Male"));
        //dbHandler.addProfile(new Profile(22, "Paula", "Male"));
        //dbHandler.addProfile(new Profile(22, "Awny", "Male"));
        profiles = dbHandler.getProfiles();
        names_strings = new ArrayList<>() ;
        for (Profile i : profiles) {
            names_strings.add(i.get_name());
        }
        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,names_strings);
        profilesListView = (ListView) findViewById(R.id.profiles_listview);
        profilesListView.setAdapter(listAdapter);
        Button add_profile_button = (Button)findViewById(R.id.add_profile_button);

        profilesListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent I = new Intent(MainActivity.this, Profile_Home.class);
                        String pname = String.valueOf(parent.getItemAtPosition(position));
                        I.putExtra("profile_name", pname);
                        startActivity(I);
                    }

                }
        );


    }

    public void add_profile (View view)
    {
        Intent I = new Intent(this, Create_Profile.class);
        startActivity(I);
    }

    @Override
    protected void onResume() {
        super.onResume();

        profiles = dbHandler.getProfiles();
        names_strings = new ArrayList<>() ;
        for (Profile i : profiles) {
            names_strings.add(i.get_name());
        }
        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,names_strings);
        profilesListView = (ListView) findViewById(R.id.profiles_listview);
        profilesListView.setAdapter(listAdapter);

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        profiles = dbHandler.getProfiles();
        names_strings = new ArrayList<>() ;
        for (Profile i : profiles) {
            names_strings.add(i.get_name());
        }
        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,names_strings);
        profilesListView = (ListView) findViewById(R.id.profiles_listview);
        profilesListView.setAdapter(listAdapter);
    }
}
