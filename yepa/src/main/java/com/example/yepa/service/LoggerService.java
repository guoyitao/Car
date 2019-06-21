package com.example.yepa.service;

import com.example.yepa.mapper.ErrorPageMapper;
import com.example.yepa.pojo.ErrorPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;

@Service
public class LoggerService {
    @Autowired
    private ErrorPageMapper errorPageMapper;


    public void SendErrorPage(Page page){
        ErrorPage errorPage = new ErrorPage();
        errorPage.setPage(page.getUrl().toString());
        errorPageMapper.insert(errorPage);

    }
}
