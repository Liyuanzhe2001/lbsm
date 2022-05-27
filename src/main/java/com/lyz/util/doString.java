package com.lyz.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class doString {

    public static Integer getLastInteger(String str) {
        char c;
        String relStr = "";
        boolean flag = false;
        for (int i = str.length() - 1; i >= 0; i--) {
            c = str.charAt(i);
            if (c <= '9' && c >= '0') {
                relStr = c + relStr;
                flag = true;
            } else if (flag) {
                if (c != '.')
                    break;
                else
                    relStr = c + relStr;
            }
        }
        return Math.toIntExact(Math.round(Double.parseDouble(relStr)));
    }

    public static String insertNumber(String str, Integer num) {
        String relStr = "";
        Integer rel = 0;
        if (str.contains("=")) {
            rel = Integer.parseInt(str.substring(str.indexOf("=") + 1, str.indexOf("分")));
            relStr = str.substring(0, str.indexOf("=")) + "+" + num + "=" + (rel + num) + "分";
        } else {
            rel = Integer.parseInt(str.substring(str.indexOf("：") + 1, str.indexOf("分")));
            relStr = "共计：" + rel + "+" + num + "=" + (rel + num);
        }
        return relStr;
    }

    public static List<Map<String, String>> getStrings(String str) {
        int i = 1;
        Map<String, String> map;
        List<Map<String, String>> relList = new ArrayList<>();
        String tmpStr;
        String name;
        String content;
        while (str.contains(i + ".")) {
            map = new HashMap<>();
            try {
                tmpStr = str.substring(str.indexOf((i) + "."), str.indexOf("\n" + (i + 1) + ". "));
            } catch (StringIndexOutOfBoundsException e) {
                tmpStr = str.substring(str.indexOf(i + "."));
            }
            name = tmpStr.substring(tmpStr.indexOf(". ") + 1, tmpStr.indexOf("\n"));
            content = tmpStr.substring(tmpStr.indexOf("\n") + 1);

            map.put("name", name.replaceAll(" ", ""));
            map.put("content", content);
            relList.add(map);
            i++;
            System.out.println(name + "\n" + content);
        }
        return relList;
    }

}
