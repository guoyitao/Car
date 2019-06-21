package com.example.yepa.dao;

import com.example.yepa.mapper.ZhainansMapper;
import com.example.yepa.pojo.ZhainansPojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component
@Slf4j
public class ZhainansDao implements Pipeline {

    @Autowired
    private ZhainansMapper zhainansMapper;

    @Override
    public void process(ResultItems resultItems, Task task) {
        ZhainansPojo zhainansPojo = new ZhainansPojo();
        zhainansPojo.setBaiduyunUrl(resultItems.get("baiduyunUrl").toString());
        zhainansPojo.setItemUrl(resultItems.get("itemUrl").toString());
        zhainansPojo.setTitle(resultItems.get("title").toString());
        zhainansPojo.setImgs(resultItems.get("imgs").toString());
        zhainansPojo.setBaiduyunPass(resultItems.get("baiduyunPass").toString());
//        List<ZhainansPojo> select = zhainansMapper.select(zhainansPojo);
//        if (select == null || select.size()==0){
        int insert = zhainansMapper.insert(zhainansPojo);
        if (insert > 0) {
            log.debug("success" + insert);
        } else {
            log.error("fail" + insert);
        }


    }
}
