package tagreed.app.waitinglist.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import tagreed.app.waitinglist.R;
import tagreed.app.waitinglist.database.DatabaseHelper;
import tagreed.app.waitinglist.database.model.Student;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.viewHolder> {
    private ArrayList<Student> Students;
    private Context context;
    private DatabaseHelper db;
    public StudentsAdapter(ArrayList<Student> Students, Context context, DatabaseHelper db) {
        this.Students = Students;
        this.context = context;
        this.db = db;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.studednt_row, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.Name.setText(Students.get(holder.getAdapterPosition()).getName());
        holder.Priority.setText(Students.get(holder.getAdapterPosition()).getPriority());
        holder.Date.setText(formatDate(Students.get(holder.getAdapterPosition()).getTimestamp()));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditStudent.class);
                intent.putExtra("S_Id", Students.get(holder.getAdapterPosition()).getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context).setTitle("Delete Student Confirmation")
                        .setMessage("Are you sure you want to remove this student ?")
                        .setPositiveButton("Yes", (dialog, whichButton) -> {
                            db.deleteStudent(Students.get(holder.getAdapterPosition()));
                            Students.remove(Students.get(holder.getAdapterPosition()));
                            notifyDataSetChanged();
                            // show the empty text is there is no items
                            if (db.getStudentsCount()>0){
                                ((MainActivity) context).E_Student(true);
                            }else {
                                ((MainActivity) context).E_Student(false);
                            }
                        })
                        .setNegativeButton("No", null).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return Students.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder {
        TextView Name;
        TextView Priority;
        TextView Date;
        RelativeLayout layout;
        ImageView Delete;
        public viewHolder(View view) {
            super(view);
            Name = view.findViewById(R.id.name);
            Priority = view.findViewById(R.id.priority);
            Date = view.findViewById(R.id.date);
            layout = view.findViewById(R.id.relative_top);
            Delete = view.findViewById(R.id.delete);
        }
    }
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");
            return fmtOut.format(date);
        } catch (ParseException e) {
        }
        return "";
    }
}
