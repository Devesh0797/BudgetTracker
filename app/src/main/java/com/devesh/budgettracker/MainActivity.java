package com.devesh.budgettracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.devesh.budgettracker.Adapters.EntryAdapter;
import com.devesh.budgettracker.Models.Entry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference reference;
    private EditText e1,e2;
    private Button b1;

    private RecyclerView recyclerView;
    private EntryAdapter entryAdapter;
    private ArrayList<Entry> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1 = findViewById(R.id.et_des);
        e2 = findViewById(R.id.et_money);
        b1 = findViewById(R.id.btn_sub);

        recyclerView=findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        reference = FirebaseDatabase.getInstance().getReference("Schedule");
        final String key = reference.push().getKey();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String des = e1.getText().toString();
                String m = e2.getText().toString();

                if(des.isEmpty() ||(m.isEmpty())){
                    Toast.makeText(MainActivity.this, "Empty Field", Toast.LENGTH_SHORT).show();
                }
                else{
                    int money = Integer.parseInt(m);
                    long date= System.currentTimeMillis();

                    Entry entry = new Entry(des,date,money,key);
                    reference.child(key).setValue(entry).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        load();
    }

    private void load() {
        arrayList = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Entry entry = snapshot1.getValue(Entry.class);
                    arrayList.add(entry);
                    entryAdapter = new EntryAdapter(MainActivity.this,arrayList,1);
                    recyclerView.setAdapter(entryAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
