package com.prevision.test.model;

import java.util.ArrayList;

/**
 * @author vcaprio
 */
public class Possibilities {

    private ArrayList<String[]> possibilityList = new ArrayList<String[]>();

    public ArrayList<String[]> getPossibilityList() {
        return possibilityList;
    }

    public void setPossibilityList(ArrayList<String[]> possibilityList) {
        this.possibilityList = possibilityList;
    }

    public void addPossibility(String[] possibility){
        this.possibilityList.add(possibility);
    }
}
