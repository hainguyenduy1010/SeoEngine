package com.engine.seo.controller;

import com.engine.seo.model.KeywordSearch;
import com.engine.seo.repository.KeywordSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Created by HaiND on 2/11/2020 9:59 PM.
 */
@Controller
public class MainController {

    @Autowired
    KeywordSearchRepository keywordSearchRepository;

    @GetMapping("/")
    public String index(Model model) {
        List<KeywordSearch> keywordSearchList = keywordSearchRepository.findAll();
        model.addAttribute("eventName", "FIFA 2020");
        return "index";
    }
}
