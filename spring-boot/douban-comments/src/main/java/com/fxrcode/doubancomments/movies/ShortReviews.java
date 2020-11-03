package com.fxrcode.doubancomments.movies;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;

@Service
public class ShortReviews {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShortReviews.class);

    @Autowired
    ReviewRepository reviewRepository;

    public int getShortReviews(String url) throws IOException, InterruptedException {
        int count = 0;
        // 1. create an instance
        final String domain = "https://movie.douban.com";
        WebClient client1 = WebClient.create(domain);

        int start = 0;

        do {
            // 2. make a request
            String path = String.format("/subject/%s/comments?start=%d&limit=20&status=P&sort=follows",
                    "26969120",
                    start);
            // NextPage='https://movie.douban.com'+soup.find(class_='next').link.get('href')
            WebClient.RequestHeadersSpec<?> requestSpec = client1.get().uri(path)
                    .cookie("dbcl2", "\"164804700:#####\"");

            // 3. handle the response
            Mono<String> result = requestSpec.retrieve().bodyToMono(String.class); // .block();
//            result.subscribe(ShortReviews::parseReview);
//            System.out.println("Here you go: " + response);
            int current = parseReview(result.block());
            LOGGER.info("Start = {}, Count = {}, parseReview_current = {}", start, count, current);

            count += current;
            if (count >= 300) {
                break;
            }
            if (current == 0) {
                break;
            }
            start += current;
            Thread.sleep(getMillis());
        } while (true);
        return count;
    }

    public int parseLocalShortReviws() throws IOException {
        File file = new ClassPathResource("html/bird-short0.html").getFile();
        String htm = new String(Files.readAllBytes(file.toPath()));
        return parseReview(htm);
    }

    @Transactional
    int parseReview(String htm) throws IOException {
        Document doc = Jsoup.parse(htm);
        final Elements links = doc.select("div.comment");
        if (links.size() < 2) {
            return 0;
        }
        for (Element innerDetail : links) {
            String vote = innerDetail.select("span.votes").get(0).childNode(0).outerHtml();
            Element info = innerDetail.select("span.comment-info").get(0);
            String user = info.childNode(1).childNode(0).outerHtml();
            String userHomeUrl = info.childNode(1).attr("href");
            String[] separated = userHomeUrl.split("\\/");
            String userId = separated[separated.length-1];
            String stars = info.childNode(5).attributes().get("class");
            int star = 0;
            if (stars.contains("5"))
                star = 5;
            else if (stars.contains("4"))
                star = 4;
            else if (stars.contains("3"))
                star = 3;
            else if (stars.contains("2"))
                star = 2;
            else if (stars.contains("1"))
                star = 1;
            String time = innerDetail.select("span.comment-time").get(0).childNode(0).outerHtml();
            String short_review = "";
            try {
                short_review = innerDetail.select("span.short").get(0).childNode(0).outerHtml();
            } catch (Exception e) {
                //TODO: handle exception
                LOGGER.warn("No short review for {}", user);
            }
            LocalDate localDate = LocalDate.parse(time.strip());
            reviewRepository.save(new ReviewDAO(userId, user, star, localDate, short_review, Integer.parseInt(vote)));
        }
        return links.size();
    }

    private static long getMillis() {
        return (long) (Math.random() * 500 + 1_000);
    }
}
