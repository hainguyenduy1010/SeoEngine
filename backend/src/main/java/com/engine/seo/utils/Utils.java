package com.engine.seo.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by HaiND on 2/24/2020 12:56 AM.
 */
public class Utils {

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
}
