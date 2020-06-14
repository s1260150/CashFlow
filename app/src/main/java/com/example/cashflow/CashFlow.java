package com.example.cashflow;

public class CashFlow {
    public String type;
    public int price, month, day;
    public boolean isBill;

    public CashFlow(boolean isBill, String type, int price, int month, int day) throws CreationException
    {
        this.isBill = isBill;
        this.type = type;
        this.price = price;
        this.month = month;
        this.day = day;
    }

    public CashFlow(String line) throws CreationException
    {
        String[] split = line.split(" ");

        this.month = Integer.parseInt(split[0]);

        this.day = Integer.parseInt(split[1]);
        try
        {
            this.isBill = check(split[2]);
        }
        catch(Exception e)
        {
            throw new CreationException();
        }
        this.type = split[3];

        this.price = Integer.parseInt(split[4]);

        System.out.println("CashFlow 生成 " + isBill + " " + type + " " + price + " " + month + " " + day);
    }

    private boolean checkIsBill(String target) throws Exception
    {
        if(target.equals("B")) return true;
        else if(target.equals("P")) return false;
        else throw new Exception();
    }

    public String toString()
    {
        return month + " " + day + " " + (isBill ? "B" : "P") + " " + type + " " + price;
    }

    public String getDesc()
    {
        //return type + " ￥" + String.valueOf(price);
        return toString();
    }

    public String showString()
    {
        return month + "/" + day + " " + (isBill ? "B" : "P") + " " + type + " ￥" + price;
    }


    private boolean check(String isBill) throws Exception
    {
        if(isBill.equals("B")) return true;
        else if(isBill.equals("P")) return false;
        throw new Exception();
    }

    public class CreationException extends Exception{
        //warningを回避するための宣言
        private static final long serialVersionUID = 1L;

        // コンストラクタ
        public CreationException(String msg){
            super(msg);
        }
        public CreationException()
        {
            super();
        }
    }
}
