/*
 * Copyright (c) 2018. This Code is created and Writed by Komang Candra Brata (k.candra.brata@ub.ac.id).
 * Inform the writer if you willing to edit or modify it for commercial purpose.
 */

package com.app.andra.hellofirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.andra.hellofirebase.Adapter.DosenAdapter;
import com.app.andra.hellofirebase.Model.DosenModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //firebase database reference object
    DatabaseReference databaseDosen;

    //view objects
    private TextView textViewUserEmail;
    private Button buttonLogout, buttonAdd;
    private EditText  editNip,editNama;
    private RecyclerView recyclerView;

    public static final String DOSEN_NAME = "com.app.andra.hellofirebase.name";
    public static final String DOSEN_ID = "com.app.andra.hellofirebase.nip";
    private DosenAdapter dosenAdapter;
    private ArrayList<DosenModel> dosenList = new ArrayList<DosenModel>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //getting the reference of artists node
        databaseDosen = FirebaseDatabase.getInstance().getReference("dosen");

        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null){
            startActivity(new Intent(this, LoginActivity.class));
            finish();

        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //initializing views
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        editNama =(EditText) findViewById(R.id.edit_nama);
        editNip =(EditText) findViewById(R.id.edit_nip);
        buttonAdd = (Button) findViewById(R.id.btn_add);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dosenAdapter = new DosenAdapter(this);

        //displaying logged in user name
        textViewUserEmail.setText("Welcome "+user.getDisplayName()+" "+user.getEmail());

        //adding listener to button
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //logging out the user
                firebaseAuth.signOut();
                //closing activity
                finish();
                //starting login activity
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertDosen();
            }
        });



        //attaching value event listener
        databaseDosen.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous dosen list
                dosenList.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting dosen
                    DosenModel dosen = postSnapshot.getValue(DosenModel.class);
                    //adding artist to the list
                    dosenList.add(dosen);
                }

                //creating adapter
                dosenAdapter.addItem(dosenList);

                //attaching adapter to the listview
                recyclerView.setAdapter(dosenAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void insertDosen() {
        //getting the values to save
        String nip = editNip.getText().toString();
        String name = editNama.getText().toString();

        //checking if the value is provided
        if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(nip)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            //String id = databaseArtists.push().getKey();

            //creating an Artist Object
            DosenModel dosen = new DosenModel(nip, name);

            //Saving the Artist
            databaseDosen.child(nip).setValue(dosen);

            //setting edittext to blank again
            editNama.setText("");
            editNip.setText("");


            //displaying a success toast
            Toast.makeText(this, "Dosen added", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a name and nip", Toast.LENGTH_LONG).show();
        }
    }
}
