package com.example.student.pedometerx;

public class Achievement {
    public Achievement(int i, String tle, String typ, double tot, int se, String stat, String st){
        id=i;
        sets=se;
        title=tle;
        type=typ;
        status=stat;
        stattoday=st;
        total=tot;
    }
    public int id, sets;
    public String title,type,status,stattoday;
    public double total;
}
