package com.thecodebarista.model;

/**
 * Country Model representing client_schedule.countries DB table.
 * Not an application managed table.
 */
public class Country {
    private int Country_ID;
    private String Country;

    public Country(int country_ID, String country) {
        Country_ID = country_ID;
        Country = country;
    }

    public int getCountry_ID() {
        return Country_ID;
    }

    public void setCountry_ID(int country_ID) {
        Country_ID = country_ID;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    @Override
    public String toString() {
        return "[" + Country_ID +
                "] " + Country;
    }
}
