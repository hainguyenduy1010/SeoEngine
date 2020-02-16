package com.engine.seo.controller;

import com.engine.seo.model.SearchData;
import com.engine.seo.repository.SearchDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by HaiND on 2/11/2020 9:59 PM.
 */
@Controller
public class MainController {

    @Autowired
    SearchDataRepository searchDataRepository;

    @GetMapping("/")
    public String index(Model model) {
        List<SearchData> searchDataList = searchDataRepository.findAll();
        model.addAttribute("eventName", "FIFA 2020");
        return "index";
    }

    @PostMapping("/search")
    public String search(@Valid @RequestBody String keyword) {
        List<SearchData> searchDataList = searchDataRepository.findAll();
//        searchDataList.addAttribute("eventName", "FIFA 2020");
        return "index";
    }
}
