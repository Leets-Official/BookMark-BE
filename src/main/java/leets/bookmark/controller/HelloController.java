package leets.bookmark.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, BookMark!";
    }

    @GetMapping("/")
    public String root() {
        return "Welcome to the BookMark API!";
    }
}