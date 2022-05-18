package com.miu.swa.project.prodcutservice.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("two")
public class ProfileTwoController {
    @GetMapping("/cart")
    public int getAll() {
        return 12;
    }
}
