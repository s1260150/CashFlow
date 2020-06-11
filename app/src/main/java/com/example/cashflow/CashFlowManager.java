package com.example.cashflow;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.example.cashflow.CashFlow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CashFlowManager extends Application {
    private double total = 0;
    public ArrayList<CashFlow> cashFlow = new ArrayList<CashFlow>();

    public static final String FILE_NAME = "CashFlow";
    private File file;

    public void init(Context context) throws IOException
    {
        file = new File(context.getFilesDir(), FILE_NAME);
        loadCashFlow();
    }
    // ファイルを読み出し
    public void loadCashFlow() throws IOException
    {
        cashFlow = new ArrayList<CashFlow>();

        for(String line : readFile())
        {

            try
            {
                cashFlow.add(new CashFlow(line));
                calcTotal();
            }
            catch(Exception e)
            {
                cashFlow = new ArrayList<CashFlow>();
                throw new IOException(FILE_NAME + " ファイルが壊れています");
            }
        }
    }

    public void saveCashFlow() throws IOException
    {
        System.out.println("ファイルセーブします : ");
        System.out.println("ファイル存在してる？ : " + file.exists());
        PrintWriter pw = new PrintWriter(new FileWriter(file));

        System.out.println("ファイル存在してる？ : " + file.exists());

        System.out.println("書き込み開始");
        for(CashFlow cash : cashFlow)
        {
            pw.println(cash.toString());
            System.out.println(cash.getDesc());
        }
        pw.flush();
        System.out.println("書き込み終了");
    }

    private List<String> readFile() throws IOException
    {
        List<String> str = new ArrayList<>();


        System.out.println("ファイルロードします : ");
        System.out.println("ファイル存在してる？ : " + file.exists());
        if(file.exists())
        {
            try(BufferedReader reader = new BufferedReader(new FileReader(file)))
            {
                String lineBuffer;
                while( (lineBuffer = reader.readLine()) != null )
                {
                    str.add(lineBuffer);
                    System.out.println(lineBuffer);
                }
            }
        }
        return str;
    }

    public void add(CashFlow cash)
    {
        cashFlow.add(cash);
        calcTotal();
    }

    public String[] toStringList()
    {
        String[] strs = new String[cashFlow.size()];
        for(int i=0; i< strs.length; ++i)
        {
            strs[i] = cashFlow.get(i).getDesc();
        }
        return strs;
    }

    public void calcTotal()
    {
        total = 0;
        for(CashFlow cash : cashFlow)
        {
            total += cash.price * (cash.isBill ? 0.5 : -1.0);
        }
    }

    public int getTotal()
    {
        return (int)total;
    }
}
