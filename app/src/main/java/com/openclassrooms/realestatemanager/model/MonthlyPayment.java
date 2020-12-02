package com.openclassrooms.realestatemanager.model;

import android.util.Log;

/**
 * Created by Florence LE BOURNOT on 02/12/2020
 */
public class MonthlyPayment {
    private static final String TAG = "TAG_MonthlyPayment";
    private int mpAmountBorrowed;
    private int mpContribution;
    private double mpRate;
    private int mpDuration;
    private int mpMonthlyPayment;

    public MonthlyPayment() {}

    public MonthlyPayment(int pMpAmountBorrowed, int pMpContribution, double pMpRate, int pMpDuration) {
        mpAmountBorrowed = pMpAmountBorrowed;
        mpContribution = pMpContribution;
        mpRate = pMpRate;
        mpDuration = pMpDuration;
    }

    public int getMpAmountBorrowed() { return mpAmountBorrowed; }

    public void setMpAmountBorrowed(int pMpAmountBorrowed) { mpAmountBorrowed = pMpAmountBorrowed; }

    public int getMpContribution() { return mpContribution; }

    public void setMpContribution(int pMpContribution) { mpContribution = pMpContribution; }

    public double getMpRate() { return mpRate; }

    public void setMpRate(double pMpRate) { mpRate = pMpRate/100; }

    public int getMpDuration() { return mpDuration; }

    public void setMpDuration(int pMpDuration) { mpDuration = pMpDuration; }

    public int getMpMonthlyPayment() { return mpMonthlyPayment; }

    public void setMpMonthlyPayment(int pMpMonthlyPayment) { mpMonthlyPayment = pMpMonthlyPayment; }

    public int calculateMonthlyPayment() {
        int lDurationInMonth = mpDuration*12;
        double lMonthRate = mpRate/12;
        int lC = mpAmountBorrowed-mpContribution;
        double lCMonthRate = (lC)*lMonthRate;
        double lMontExpo = Math.pow((1+lMonthRate),(lDurationInMonth*-1));
        int lPayment = (int) Math.round(lCMonthRate/(1-(lMontExpo)));

        setMpMonthlyPayment(lPayment);

        return lPayment;
    }
}
