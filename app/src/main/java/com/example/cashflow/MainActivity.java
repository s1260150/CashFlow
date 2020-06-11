package com.example.cashflow;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    public static final String STRING_PAYMENT = "支払い";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.button_rent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToRegistrationActivity("家賃", true);
            }
        });
        findViewById(R.id.button_gas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToRegistrationActivity("ガス代", true);
            }
        });
        findViewById(R.id.button_water).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToRegistrationActivity("水道代", true);
            }
        });
        findViewById(R.id.button_electricity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToRegistrationActivity("電気代", true);
            }
        });
        findViewById(R.id.button_internet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToRegistrationActivity("インターネット料金", true);
            }
        });
        findViewById(R.id.button_other).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToRegistrationActivity("その他", true);
            }
        });
        findViewById(R.id.button_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToRegistrationActivity(STRING_PAYMENT, false);
            }
        });
        findViewById(R.id.button_cashflow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToCashFlowActivity();
            }
        });

        try
        {
            CashFlowManager cashFlowManager = (CashFlowManager)getApplication();
            cashFlowManager.init(this);
            cashFlowManager.calcTotal();
            updateTotalView(cashFlowManager.getTotal());
        }
        catch(IOException e)
        {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Error") // タイトル
                    .setMessage(CashFlowManager.FILE_NAME + " ファイルが壊れています") // メッセージ
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    }).create();

            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        CashFlowManager cashFlowManager = (CashFlowManager)getApplication();
        cashFlowManager.calcTotal();
        updateTotalView(cashFlowManager.getTotal());
    }

    private void moveToRegistrationActivity(String type, boolean isBill)
    {
        Intent intent = new Intent(MainActivity.this, PaymentActivity.class);

        intent.putExtra(PaymentActivity.EXTRA_DATA_TYPE, type);
        intent.putExtra(PaymentActivity.EXTRA_DATA_IS_BILL, isBill);

        startActivity(intent);
    }

    private void moveToCashFlowActivity()
    {
        Intent intent = new Intent(this, CashFlowActivity.class);

        startActivity(intent);
    }

    private void updateTotalView(int amount)
    {
        TextView totalView = findViewById(R.id.text_main_total);

        totalView.setText("￥ " + amount);
    }
}
