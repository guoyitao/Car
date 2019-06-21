package com.example.yepa.dao;

import com.example.yepa.mapper.YardEmmMoePojoMapper;
import com.example.yepa.pojo.YardEmmMoePojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component
@Slf4j
public class YardEmmMoeDao implements Pipeline {

    @Autowired
    private YardEmmMoePojoMapper moePojoMapper;


    @Override
    public void process(ResultItems resultItems, Task task) {
        YardEmmMoePojo yardEmmMoePojo = new YardEmmMoePojo(
              resultItems.get("baiduyunUrl").toString(),
                resultItems.get("itemUrl").toString(),
                resultItems.get("title").toString(),
                resultItems.get("baiduyunPass").toString(),
                resultItems.get("zipPassword").toString()
        );
        int insert = moePojoMapper.insert(yardEmmMoePojo);
        if (insert>0){
            log.debug("success"+ insert);
        }else{
            log.error("fail"+ insert);
        }
    }
}
