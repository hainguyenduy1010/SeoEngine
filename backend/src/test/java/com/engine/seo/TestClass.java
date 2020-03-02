package com.engine.seo;

import com.engine.seo.utils.Utils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Created by HaiND on 2/17/2020 11:00 PM.
 */
public class TestClass {

    @Test
    void test_1() {
        long countLastPage = 40 % 20;
        System.out.println(countLastPage);

        System.out.println(Utils.normalizeColumnName("hai_nd_dh_asd_asf_12_a"));
    }

    @Test
    void test_2() throws IOException {
//        InputStream response = null;
//
//        String url = "https://www.couponsherpa.com/merrell/";
//        response = new URL(url).openStream();
//
//
//        Scanner scanner = new Scanner(response);
//        String responseBody = scanner.useDelimiter("\\A").next();
//        System.out.println(responseBody.substring(responseBody.indexOf("<title>") + 7, responseBody.indexOf("</title>")));

        Document doc = Jsoup.connect("https://www.nextag.com/shopping/products")
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
//                .userAgent("Mozilla/5.0")
//                .header("Accept", "text/html")
//                .header("Accept-Encoding", "gzip,deflate")
//                .header("Accept-Language", "it-IT,en;q=0.8,en-US;q=0.6,de;q=0.4,it;q=0.2,es;q=0.2")
//                .header("Connection", "keep-alive")
//                .header("Content-Type","application/x-www-form-urlencoded")
                .ignoreContentType(true)
                .get();
        String title;
        Elements metaOgTitle = doc.select("meta[property=og:title]");
        if (metaOgTitle!=null && metaOgTitle.size() != 0) {
            title = metaOgTitle.attr("content");
        }
        else {
            title = doc.title();
        }
        System.out.println("title is: " + title);

        String description = doc.select("meta[name=description]").first().attr("content");
        System.out.println("Meta description : " + description);

        String description2 = getMetaTag(doc, "description");
        if (description == null) {
            description = getMetaTag(doc, "og:description");
        }

        System.out.println("Meta description 2 : " + description2);
    }

    String getMetaTag(Document document, String attr) {
        Elements elements = document.select("meta[name=" + attr + "]");
        for (Element element : elements) {
            final String s = element.attr("content");
            if (s != null) return s;
        }
        elements = document.select("meta[property=" + attr + "]");
        for (Element element : elements) {
            final String s = element.attr("content");
            if (s != null) return s;
        }
        return null;
    }
}
