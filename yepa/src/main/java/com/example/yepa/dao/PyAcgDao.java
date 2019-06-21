package com.example.yepa.dao;

import com.example.yepa.mapper.PyAcgMapper;
import com.example.yepa.pojo.PyAcgPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component
public class PyAcgDao implements Pipeline {

    @Autowired
    private PyAcgMapper pyAcgMapper;


    @Override
    public void process(ResultItems resultItems, Task task) {
        boolean skip = resultItems.isSkip();
        if (!skip){

            PyAcgPojo pyAcgPojo = new PyAcgPojo();
            pyAcgPojo.setBaiduyunPass(resultItems.get("baiduyunPass").toString());
            pyAcgPojo.setBaiduyunUrl(resultItems.get("baiduyunUrl").toString());
            pyAcgPojo.setTitle( resultItems.get("title").toString());
            pyAcgPojo.setItemUrl(resultItems.get("itemUrl").toString());


            pyAcgMapper.insert(pyAcgPojo);
        }

    }
}
