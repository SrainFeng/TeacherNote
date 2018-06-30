package com.example.srain.teachernote.ui.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.srain.teachernote.MyApplication;
import com.example.srain.teachernote.R;
import com.example.srain.teachernote.ui.activities.TeachClassActivity;
import com.example.srain.teachernote.entity.TeachClass;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Project: TeacherNote
 * Date: 2018/5/2
 *
 * @author srain
 */
public class TeachClassAdapter extends RecyclerView.Adapter<TeachClassAdapter.ViewHolder> {

    private List<TeachClass> mClassList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        TextView className;
        TextView classCode;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            className = view.findViewById(R.id.class_name);
            classCode = view.findViewById(R.id.class_code);
        }
    }

    public TeachClassAdapter(List<TeachClass> classList) {
        mClassList = classList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teach_class_list_item, parent, false);

        final ViewHolder holder = new ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                TeachClass teachClass = mClassList.get(position);
                Intent intent = new Intent(MyApplication.getContext(), TeachClassActivity.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                String classCode = teachClass.getClassCode();
                intent.putExtra("class_code", classCode);
                MyApplication.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TeachClass teachClass = mClassList.get(position);
        holder.className.setText(teachClass.getName());
        holder.classCode.setText(teachClass.getClassCode());
    }

    @Override
    public int getItemCount() {
        return mClassList.size();
    }

}
