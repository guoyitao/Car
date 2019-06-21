package com.dalao.web;

import com.dalao.pojo.Data;
import com.dalao.pojo.PageResult;
import com.dalao.service.PageService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class PageController {

    @Autowired
    private PageService pageService;

    @GetMapping("/")
    public String index(){
        return "search";
    }


    @GetMapping(value = "search")
    public ResponseEntity<PageResult<Data>> searchData(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "key", defaultValue = "") String key){

        PageResult<Data> dataPageResult = pageService.searchData(page, rows, key);

        for (Data item : dataPageResult.getItems()) {
                String delete = StringUtils.delete(item.getImg(), "[");
            String delete1 = StringUtils.delete(delete, " ");
            String delete2 = StringUtils.delete(delete1, "]");

                List<String> o = Arrays.asList(delete2.split(","));
                item.setImgs(o);

            }
        return new ResponseEntity<>(dataPageResult, HttpStatus.OK);
    }
}
