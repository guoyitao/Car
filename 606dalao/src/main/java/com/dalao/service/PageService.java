package com.dalao.service;

import com.dalao.pojo.Data;
import com.dalao.pojo.PageResult;
import org.springframework.stereotype.Service;


public interface PageService {

    PageResult<Data> searchData(Integer page, Integer rows,  String key);

}
