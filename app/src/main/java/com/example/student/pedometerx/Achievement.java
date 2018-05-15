package com.example.student.pedometerx;

public class Achievement {
    public Achievement(int i,String dc, String tle, String typ, double tot, int se,int sa, String stat, String st){
        id = i;
        datecreated = dc;
        sets=se;
        title=tle;
        setsachieved =sa;
        type=typ;
        status=stat;
        stattoday=st;
        total=tot;
    }
    public int id, sets,setsachieved;
    public String title,type,status,stattoday,datecreated;
    public double total;
}
