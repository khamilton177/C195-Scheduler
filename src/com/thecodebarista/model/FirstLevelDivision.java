package com.thecodebarista.model;

public class FirstLevelDivision {
    private int Division_ID;
    private String Division;
    private int Country_ID;

    public FirstLevelDivision(int division_ID, String division, int country_ID) {
        Division_ID = division_ID;
        Division = division;
        Country_ID = country_ID;
    }

    public int getDivision_ID() {
        return Division_ID;
    }

    public void setDivision_ID(int division_ID) {
        Division_ID = division_ID;
    }

    public String getDivision() {
        return Division;
    }

    public void setDivision(String division) {
        Division = division;
    }

    public int getCountry_ID() {
        return Country_ID;
    }

    public void setCountry_ID(int country_ID) {
        Country_ID = country_ID;
    }

    @Override
    public String toString() {
        return "FirstLevelDivision{" +
                "Division_ID=" + Division_ID +
                ", Division='" + Division + '\'' +
                ", Country_ID=" + Country_ID +
                '}';
    }

}
