package com.fpoly.dell.project.database;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {

    public static String convertDate(String dateTime) {
        Date date = new Date(dateTime);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }
}
