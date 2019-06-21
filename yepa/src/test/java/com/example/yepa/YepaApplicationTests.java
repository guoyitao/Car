package com.example.yepa;

import com.example.yepa.dao.PyAcgDao;
import com.example.yepa.dao.YardEmmMoeDao;
import com.example.yepa.dao.ZhainansDao;
import com.example.yepa.pojo.YardEmmMoePojo;
import com.example.yepa.service.PyAcgService;
import com.example.yepa.service.YardEmmMoeService;
import com.example.yepa.service.ZhainansService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.SpiderListener;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YepaApplicationTests {

    @Autowired
    private YardEmmMoeService yardEmmMoeService;

    @Autowired
    private YardEmmMoeDao yardEmmMoeDao;

    @Autowired
    private ZhainansService zhainansService;

    @Autowired
    private ZhainansDao zhainansDao;

    @Test
    public void contextLoads() {


        Spider.create(yardEmmMoeService).
                addUrl(YardEmmMoeService.startUrl).
                addPipeline(yardEmmMoeDao).
                thread(50).
                run();

    }


    @Test
    public void contextLoads2() {


        Spider.create(zhainansService).
                addUrl(ZhainansService.startUrl).

                thread(20).
                run();

    }

    @Autowired
    private PyAcgService pyAcgService;

    @Autowired
    private PyAcgDao pyAcgDao;

    @Test
    public void contextLoadsPy() {

        Spider spider = Spider.create(pyAcgService).
                addUrl(pyAcgService.startUrl).
                addPipeline(pyAcgDao).
                thread(50);
        ArrayList<SpiderListener> listeners = new ArrayList<>();
        listeners.add(new SpiderListener() {
            @Override
            public void onSuccess(Request request) {
            }

            @Override
            public void onError(Request request) {
                Integer cycleTriedTimes = (Integer) request.getExtra(Request.CYCLE_TRIED_TIMES);
                request.putExtra(Request.CYCLE_TRIED_TIMES, cycleTriedTimes == null ? 1 : cycleTriedTimes + 1);
                spider.addRequest(request);
            }
        });
        spider.setSpiderListeners(listeners);
        spider.run();

    }



}
