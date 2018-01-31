package com.mursitaffandi.myfavoritemovie.util;

import java.util.Locale;

public class Localize  {
    public static String getCountry() {
        String country = Locale.getDefault().getCountry().toLowerCase();

        switch (country) {
            case "id":
                break;

            default:
                country = "en";
                break;
        }

        return country;
    }
}
