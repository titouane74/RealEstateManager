package com.openclassrooms.realestatemanager.model;

/**
 * Created by Florence LE BOURNOT on 16/12/2020
 */
public class DataSearch {

    private String dsWhereClause;
    private int dsArg1Index;
    private String dsArg1;
    private int dsArg2Index;
    private String dsArg2;

    public DataSearch() { }

    public DataSearch(String pDsWhereClause, int pDsArg1Index, String pDsArg1) {
        dsWhereClause = pDsWhereClause;
        dsArg1Index = pDsArg1Index;
        dsArg1 = pDsArg1;
    }

    public DataSearch(String pDsWhereClause, int pDsArg1Index, String pDsArg1, int pDsArg2Index, String pDsArg2) {
        dsWhereClause = pDsWhereClause;
        dsArg1Index = pDsArg1Index;
        dsArg1 = pDsArg1;
        dsArg2Index = pDsArg2Index;
        dsArg2 = pDsArg2;
    }

    public String getDsWhereClause() {
        return dsWhereClause;
    }

    public void setDsWhereClause(String pDsWhereClause) {
        dsWhereClause = pDsWhereClause;
    }

    public int getDsArg1Index() {
        return dsArg1Index;
    }

    public void setDsArg1Index(int pDsArg1Index) {
        dsArg1Index = pDsArg1Index;
    }

    public String getDsArg1() {
        return dsArg1;
    }

    public void setDsArg1(String pDsArg1) {
        dsArg1 = pDsArg1;
    }

    public int getDsArg2Index() {
        return dsArg2Index;
    }

    public void setDsArg2Index(int pDsArg2Index) {
        dsArg2Index = pDsArg2Index;
    }

    public String getDsArg2() {
        return dsArg2;
    }

    public void setDsArg2(String pDsArg2) {
        dsArg2 = pDsArg2;
    }
}
