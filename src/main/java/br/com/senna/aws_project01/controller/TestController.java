package br.com.senna.aws_project01.controller;

import br.com.senna.aws_project01.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestService testService;

    @GetMapping("/dog/{name}")
    public ResponseEntity<?> dogTest(@PathVariable String name) {

        log.info("Dog name controller - name: {}", name);

        log.info("Test service - isMatilde: {}", testService.isUserMatilde(name));

        return ResponseEntity.ok("Name:" + name);
    }

    @GetMapping("/dog/color/{color}")
    public ResponseEntity<?> dogColor(@PathVariable String color){

        log.info("Dog name controller - color: {}", color);

        return ResponseEntity.ok("Color: " + color);
    }
}
