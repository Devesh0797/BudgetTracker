package com.devesh.budgettracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.devesh.budgettracker.Adapters.EntryAdapter;
import com.devesh.budgettracker.Models.Entry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Tracker extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EntryAdapter entryAdapter;
    private ArrayList<Entry> arrayList;

    private DatabaseReference reference;


    private TextView tot,perprson;
    int total=0;
    double per =0;
    private CardView relativeLayout;
    private EditText txt_inputText;
    private Button btn_okay,btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);

        tot = findViewById(R.id.tv_total);
        perprson = findViewById(R.id.tv_perperson);
        relativeLayout = findViewById(R.id.include);

        recyclerView=findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        reference = FirebaseDatabase.getInstance().getReference("Schedule");
        load();

         txt_inputText = findViewById(R.id.txt_input);
         btn_okay = findViewById(R.id.btn_okay);
         btn_cancel = findViewById(R.id.btn_cancel);

         btn_cancel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 relativeLayout.setVisibility(View.GONE);
             }
         });
        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt_inputText.getText().toString().trim().equals("pass123")) {
                    relativeLayout.setVisibility(View.GONE);
                    Intent intent1 = new Intent(Tracker.this, MainActivity.class);
                    startActivity(intent1);
                }
                else{
                    Toast.makeText(Tracker.this, "wrong password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void load() {
        arrayList = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                total=0;
                arrayList.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Entry entry = snapshot1.getValue(Entry.class);
                    arrayList.add(entry);
                    total = total + entry.getMoney();
                    entryAdapter = new EntryAdapter(Tracker.this,arrayList,2);
                    recyclerView.setAdapter(entryAdapter);
                    tot.setText(String.valueOf(total));
                    double ans = (double)(total) / 5;
                    perprson.setText(String.valueOf(ans));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.view_profile:
                relativeLayout.setVisibility(View.VISIBLE);
                txt_inputText.requestFocus();
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }


}
