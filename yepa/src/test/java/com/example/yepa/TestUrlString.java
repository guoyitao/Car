package com.example.yepa;

import com.example.yepa.service.IdanmuService;
import org.apache.commons.lang3.StringUtils;

public class TestUrlString {
    public static void main(String[] args) {
        String url = "https://idanmu.at/v15/113529/";
        String id = IdanmuService.getId(url);
        System.out.printf(id);
    }
}
