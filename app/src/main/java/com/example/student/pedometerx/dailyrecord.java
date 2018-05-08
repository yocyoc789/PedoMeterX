package com.example.student.pedometerx;

public class dailyrecord {
    public dailyrecord(String da, int st, int sg, double s, double c, double d, String sta, long t){
        dates = da;
        steps = st;
        stepsgoal = sg;
        calburned = c;
        distances = d;
        speeds = s;
        status = sta;
        time = t;

    }
    public String dates, status;
    public int steps, stepsgoal;
    public double calburned,distances,speeds;
    public long time;
}
