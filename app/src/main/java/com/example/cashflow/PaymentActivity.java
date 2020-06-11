package com.example.cashflow;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentActivity extends AppCompatActivity {
    private TextView total= null;
    private EditText type = null, month = null, day = null;
    private String totalStr = "", typeStr = "";
    private boolean isBill, isRevise;
    private int reviseID = -1;

    public static final String EXTRA_DATA_TYPE = "type";
    public static final String EXTRA_DATA_IS_BILL = "isBill";
    public static final String EXTRA_DATA_AMOUNT = "amount";
    public static final String EXTRA_DATA_IS_REVISE = "isrevise";
    public static final String EXTRA_DATA_REVISE_ID = "reviseID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Intent intent = getIntent();
        if((typeStr = intent.getStringExtra(EXTRA_DATA_TYPE)) == null) typeStr = "";
        if((totalStr = intent.getStringExtra(EXTRA_DATA_AMOUNT)) == null) totalStr = "";
        isBill = intent.getBooleanExtra(EXTRA_DATA_IS_BILL, true);
        isRevise = intent.getBooleanExtra(EXTRA_DATA_IS_REVISE, false);
        if(isRevise) reviseID = intent.getIntExtra(EXTRA_DATA_REVISE_ID, -1);

        total = (TextView)findViewById(R.id.text_total);
        type = (EditText)findViewById(R.id.text_type);
        month = (EditText)findViewById(R.id.edit_month);
        day = (EditText)findViewById(R.id.edit_day);
        total.setText(totalStr);
        type.setText(typeStr);


        final DateFormat df = new SimpleDateFormat("MM/dd");
        String[]date = df.format(new Date(System.currentTimeMillis())).split("/");

        month.setText(date[0]);
        day.setText(date[1]);

        findViewById(R.id.button_zero).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalStr += "0";
                updateTotalText();
            }
        });
        findViewById(R.id.button_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalStr += "1";
                updateTotalText();
            }
        });
        findViewById(R.id.button_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalStr += "2";
                updateTotalText();
            }
        });
        findViewById(R.id.button_three).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalStr += "3";
                updateTotalText();
            }
        });
        findViewById(R.id.button_four).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalStr += "4";
                updateTotalText();
            }
        });
        findViewById(R.id.button_five).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalStr += "5";
                updateTotalText();
            }
        });
        findViewById(R.id.button_six).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalStr += "6";
                updateTotalText();
            }
        });
        findViewById(R.id.button_seven).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalStr += "7";
                updateTotalText();
            }
        });
        findViewById(R.id.button_eight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalStr += "8";
                updateTotalText();
            }
        });
        findViewById(R.id.button_nine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalStr += "9";
                updateTotalText();
            }
        });
        findViewById(R.id.button_c).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalStr = "";
                updateTotalText();
            }
        });
        findViewById(R.id.button_backspace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalStr.length() > 0) totalStr = totalStr.substring(0, totalStr.length() - 1);
                updateTotalText();
            }
        });
        findViewById(R.id.button_equal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!totalStr.equals(""))
                {
                    typeStr = type.getText().toString();
                    showConfirmDialog();
                }
            }
        });
    }

    private void showConfirmDialog()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Confirm") // タイトル
            .setMessage(typeStr + " ￥" + totalStr + "\nでよろしいですか？") // メッセージ
            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    totalStr = "";
                    updateTotalText();
                }
            }).setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    register();
                }
            }).create();


        dialog.show();
    }

    private void updateTotalText()
    {
        total.setText(totalStr);
    }

    private void register()
    {
        CashFlowManager cashFlowManager = (CashFlowManager)getApplication();

        try
        {
            if(typeStr.equals(MainActivity.STRING_PAYMENT)) isBill = false;
            else isBill = true;

            int m = Integer.parseInt(month.getText().toString());
            int d = Integer.parseInt(day.getText().toString());

            int total = Integer.parseInt(totalStr);

            if(isRevise)
            {
                if(reviseID != -1)
                {
                    cashFlowManager.cashFlow.set(reviseID, new CashFlow(isBill, typeStr, total, m, d));
                }
            }
            else
            {
                cashFlowManager.add(new CashFlow(isBill, typeStr, total, m, d));
            }
            cashFlowManager.saveCashFlow();
        }
        catch(Exception e)
        {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Error") // タイトル
                    .setMessage(CashFlowManager.FILE_NAME + " ファイルの書き込みに失敗しました") // メッセージ
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    }).create();

            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            return;
        }
        finish();
    }
}
