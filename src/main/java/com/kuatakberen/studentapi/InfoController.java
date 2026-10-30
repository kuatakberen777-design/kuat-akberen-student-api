package com.kuatakberen.studentapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class InfoController {

    @Autowired
    private BuildProperties buildProperties;

    @GetMapping({"/", "/api/info"})
    public Map<String, String> info() {
        return Map.of(
            "student", "Kuat Akberen",
            "project", buildProperties.getName(),
            "version", buildProperties.getVersion()
        );
    }
}
