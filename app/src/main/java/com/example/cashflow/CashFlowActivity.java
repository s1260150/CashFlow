package com.example.cashflow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CashFlowActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_flow);

    }


    @Override
    protected void onResume()
    {
        super.onResume();
        CashFlowManager cashFlowManager = (CashFlowManager)getApplication();

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        adapter = new MyAdapter(cashFlowManager.cashFlow);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        // specify an adapter (see also next example)
        recyclerView.setAdapter(adapter);

        final Intent intent = new Intent(CashFlowActivity.this, PaymentActivity.class);

        adapter.setOnItemClickListener(new MyAdapter.MyOnClickListener() {
            @Override
            public void onClick(View view, int id) {

                CashFlowManager cashFlowManager = (CashFlowManager)getApplication();
                CashFlow target = cashFlowManager.cashFlow.get(id);

                System.out.println("REVICE ID : " + id);

                intent.putExtra(PaymentActivity.EXTRA_DATA_AMOUNT, String.valueOf(target.price));
                intent.putExtra(PaymentActivity.EXTRA_DATA_TYPE, target.type);
                intent.putExtra(PaymentActivity.EXTRA_DATA_IS_BILL, target.isBill);
                intent.putExtra(PaymentActivity.EXTRA_DATA_IS_REVISE, true);
                intent.putExtra(PaymentActivity.EXTRA_DATA_REVISE_ID, id);

                startActivity(intent);
            }
        }, new MyAdapter.MyOnClickListener() {
            @Override
            public void onClick(View view, int id) {
                showDeleteDialog(id);
            }
        });
    }

    private void showDeleteDialog(int id)
    {
        final CashFlowManager cashFlowManager = (CashFlowManager)getApplication();
        CashFlow target = cashFlowManager.cashFlow.get(id);

        final Context context = this;
        final int target_id = id;
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Confirm") // タイトル
                .setMessage(target.getDesc() + "\nを削除してよろしいですか？")
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                }).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                cashFlowManager.cashFlow.remove(target_id);
                try
                {
                    cashFlowManager.saveCashFlow();
                }
                catch (IOException e)
                {
                    Toast.makeText(context, "ファイルの書き込みに失敗しました", Toast.LENGTH_LONG);
                }

                adapter.notifyDataSetChanged();
            }
        }).create();


        dialog.show();
    }
}
