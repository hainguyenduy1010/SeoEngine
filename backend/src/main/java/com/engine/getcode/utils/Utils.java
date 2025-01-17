package com.engine.getcode.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by HaiND on 2/24/2020 12:56 AM.
 */
public class Utils {

    private static final String STR_UNDERSCORE = "_";

    private Utils() {
    }

    public static String boldTextByKeyword(String keyword, String text) {
        String[] singleKeywordList = StringUtils.split(keyword, " ");

        for (String singleKeyword : singleKeywordList) {
            text = text.replace(singleKeyword, "<b>" + singleKeyword + "</b>");
            singleKeyword = singleKeyword.substring(0, 1).toUpperCase() + singleKeyword.substring(1);
            text = text.replace(singleKeyword, "<b>" + singleKeyword + "</b>");
        }

        return text;
    }

    public static String normalizeColumnName(String name) {
        int index = name.indexOf(STR_UNDERSCORE);

        if (index >= 0) {
            name = name.replaceFirst(STR_UNDERSCORE, StringUtils.EMPTY);
            name = name.substring(0, index) + name.substring(index, index + 1).toUpperCase() + name.substring(index + 1);
            // Recursive call
            return normalizeColumnName(name);
        }

        return name;
    }
}
