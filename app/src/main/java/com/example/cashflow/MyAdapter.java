package com.example.cashflow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
{
    private ArrayList<CashFlow> mDataset;
    private MyOnClickListener textListener, buttonListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public Button buttonDelete;
        public MyViewHolder(View v) {
            super(v);
            textView = (TextView)v.findViewById(R.id.show_string);
            buttonDelete = (Button)v.findViewById(R.id.button_cash_flow_delete);
        }
    }

    public MyAdapter(ArrayList<CashFlow> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String dataString = mDataset.get(mDataset.size() - 1 - position).getDesc();
        String[] flagment = dataString.split(" ");
        String showString = flagment[0] + "/" + flagment[1]  + " " + flagment[2] + " " + flagment[3] + " ￥" + flagment[4];

        holder.textView.setText(showString);

        final int id = position;
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textListener.onClick(view, mDataset.size() - 1 - id);
            }
        });
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonListener.onClick(view, mDataset.size() - 1 - id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setOnItemClickListener(MyOnClickListener textListener, MyOnClickListener buttonListener) {
        this.textListener = textListener;
        this.buttonListener = buttonListener;
    }

    public interface MyOnClickListener
    {
        public void onClick(View view, int id);
    }
}
