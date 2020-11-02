package com.fxrcode.doubancomments;

import com.fxrcode.doubancomments.movies.ShortReviews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@RequestMapping("/reviews")
public class ReviewsController {
    @Autowired
    ShortReviews shortReviews;


    @GetMapping("/mono")
    public Mono<String> helloPerson(String name) {
        return Mono.just("Hello " + name + "!");
    }

    @GetMapping("/short")
    public int hello() throws IOException, InterruptedException {
        int hello = shortReviews.getShortReviews("hello");
        return hello;
    }

    @GetMapping("/parse")
    public int parse() throws IOException {
        return shortReviews.parseLocalShortReviws();
    }
}
