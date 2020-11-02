package com.fxrcode.doubancomments;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.util.WebUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ReviewTest {

    @Test
    public void parseReview() throws IOException {
        File file = new ClassPathResource("data/bird-short0.html").getFile();
        String htm = new String(Files.readAllBytes(file.toPath()));
        Document doc = Jsoup.parse(htm);
        final Elements links = doc.select("div.comment");
        for (int i = 0; i < 5; i++) {
            Element innerDetail = links.get(i);
            String vote = innerDetail.select("span.votes").get(0).childNode(0).outerHtml();
            Element info = innerDetail.select("span.comment-info").get(0);
            String user = info.childNode(1).childNode(0).outerHtml();
            String userHomeUrl = info.childNode(1).attr("href");
            String[] separated = userHomeUrl.split("\\/");
            String userId = separated[separated.length-1];
            String stars = info.childNode(5).attributes().get("class");
            String time = innerDetail.select("span.comment-time").get(0).childNode(0).outerHtml();
            String short_review = innerDetail.select("span.short").get(0).childNode(0).outerHtml();
            System.out.println(userId + ": " + userHomeUrl);
        }
    }

    public void cookieTest() {
        //
    }
}
