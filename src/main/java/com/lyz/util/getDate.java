package com.lyz.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class getDate {

    public static Date getNow() throws ParseException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = sdf.format(date);
        return sdf.parse(nowTime);
    }

}
