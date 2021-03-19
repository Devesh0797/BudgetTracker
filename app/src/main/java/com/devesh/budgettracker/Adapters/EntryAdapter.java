package com.devesh.budgettracker.Adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devesh.budgettracker.Models.Entry;
import com.devesh.budgettracker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ImageViewHolder> {
    public Context mContext1;
    private List<Entry> mUploads;
    private int flag;


    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView desc,time,money;
        public Button del;

        public ImageViewHolder(View itemView) {
            super(itemView);
            this.desc = (TextView) itemView.findViewById(R.id.tv_des);
            this.time = (TextView) itemView.findViewById(R.id.tv_date);
            this.money = (TextView) itemView.findViewById(R.id.tv_money);
            this.del = (Button) itemView.findViewById(R.id.btn_del);
        }
    }

    public EntryAdapter(Context context, List<Entry> uploads,int flag) {
        this.mContext1 = context;
        this.mUploads = uploads;
        this.flag = flag;
    }

    public EntryAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(flag==1) {
            return new EntryAdapter.ImageViewHolder(LayoutInflater.from(this.mContext1).inflate(R.layout.my_items, parent, false));
        }
        else{
            return new EntryAdapter.ImageViewHolder(LayoutInflater.from(this.mContext1).inflate(R.layout.user_item, parent, false));
        }
    }

    public void onBindViewHolder(final EntryAdapter.ImageViewHolder holder, int position) {
        final Entry story = (Entry) this.mUploads.get(position);

        Calendar calendar=Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(story.getDate());
        String time = DateFormat.format("dd/MM hh:mm aa",calendar).toString();

        holder.money.setText(String.valueOf(story.getMoney()));
        holder.time.setText(time);
        holder.desc.setText(story.getDesc());

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Schedule");
                reference.child(story.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(mContext1, "Done", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    public int getItemCount() {
        return this.mUploads.size();
    }
}
