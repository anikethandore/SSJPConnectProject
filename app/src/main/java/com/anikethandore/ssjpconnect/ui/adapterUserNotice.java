package com.anikethandore.ssjpconnect.ui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anikethandore.ssjpconnect.ModelSourceListNotice;
import com.anikethandore.ssjpconnect.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class adapterUserNotice extends FirebaseRecyclerAdapter<ModelSourceListNotice, adapterUserNotice.myviewholder>

{

    public adapterUserNotice(@NonNull FirebaseRecyclerOptions<ModelSourceListNotice> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull ModelSourceListNotice model)
    {
        holder.title.setText(model.getTitle());
        holder.decs.setText(model.getDesc());
        holder.branch.setText(model.getBranch());
        holder.year.setText(model.getYear());
        holder.date.setText(model.getDate());


        holder.item_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(holder.item_click.getContext(), pdfViewActivity.class);
                intent.putExtra("fileTitle",model.getTitle());
                intent.putExtra("fileUrl",model.getFileurl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.item_click.getContext().startActivity(intent);
            }
        });


    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_notice_single_element,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{
        TextView title,decs,branch,year,date,item_click;
        public myviewholder(View itemView){
            super(itemView);
            title=itemView.findViewById(R.id.nameTV);
            decs=itemView.findViewById(R.id.decsTV);
            branch=itemView.findViewById(R.id.branchTV);
            year=itemView.findViewById(R.id.yearTV);
            date=itemView.findViewById(R.id.dateTV);
            item_click=itemView.findViewById(R.id.item_click_btn);
        }
    }



}
