package com.example.staszicowyplanlekcji;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewDefaultAdapter extends RecyclerView.Adapter<RecyclerViewDefaultAdapter.ViewHolder> {

    private Context context;
    private List<TableRow> mRows;

    public RecyclerViewDefaultAdapter(Context context, List<TableRow> mRows) {
        this.context = context;
        this.mRows = mRows;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.godzView.setText(mRows.get(position).getGodz());
        if(mRows.get(position).getZastepstwo()=="") {
            holder.lekcjaView.setText(mRows.get(position).getLekcja());
            holder.wholeView.setBackgroundColor(context.getResources().getColor(R.color.lightMain));
            holder.lekcjaView.setBackgroundColor(context.getResources().getColor(R.color.lightMain));
            holder.godzView.setBackgroundColor(context.getResources().getColor(R.color.strongMain));
        }else{
            holder.lekcjaView.setText(mRows.get(position).getZastepstwo());
            holder.wholeView.setBackgroundColor(context.getResources().getColor(R.color.lightAlert));
            holder.lekcjaView.setBackgroundColor(context.getResources().getColor(R.color.lightAlert));
            holder.godzView.setBackgroundColor(context.getResources().getColor(R.color.strongAlert));
        }
    }

    @Override
    public int getItemCount() {
        return mRows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView godzView;
        TextView lekcjaView;
        LinearLayout wholeView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            godzView = itemView.findViewById(R.id.godzTekst);
            lekcjaView = itemView.findViewById(R.id.lekcjaText);
            wholeView = itemView.findViewById(R.id.wholeObject);
        }
    }
}
