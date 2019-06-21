package com.example.yepa;

import com.example.yepa.dao.YardEmmMoeDao;
import com.example.yepa.dao.ZhainansDao;
import com.example.yepa.service.YardEmmMoeService;
import com.example.yepa.service.ZhainansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.spring.annotation.MapperScan;
import us.codecraft.webmagic.Spider;

@SpringBootApplication
@MapperScan(basePackages = "com.example.yepa.mapper")
public class YepaApplication {

    public static void main(String[] args) {
        SpringApplication.run(YepaApplication.class, args);
    }


}
