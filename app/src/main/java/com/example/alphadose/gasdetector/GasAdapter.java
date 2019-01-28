package com.example.alphadose.gasdetector;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class GasAdapter extends RecyclerView.Adapter<GasAdapter.MyViewHolder> {

    private List<Gas> gasList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView formula, name, concentration;

        public MyViewHolder(View view) {
            super(view);
            formula = view.findViewById(R.id.formula);
            name = view.findViewById(R.id.name);
            concentration = view.findViewById(R.id.concentration);
        }
    }


    public GasAdapter(List<Gas> moviesList) {
        this.gasList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Gas gas = gasList.get(position);
        holder.formula.setText(gas.getFormula());
        holder.name.setText(gas.getName());
        holder.concentration.setText(gas.getConcentration());
    }

    @Override
    public int getItemCount() {
        return gasList.size();
    }
}