package com.dalao.service.impl;

import com.dalao.mapper.DataTBMapper;
import com.dalao.pojo.Data;
import com.dalao.pojo.PageResult;
import com.dalao.service.PageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Collections;
import java.util.List;

@Service
public class PageServiceImpl implements PageService {

    @Autowired
    private DataTBMapper dataTBMapper;

    @Override
    public PageResult<Data> searchData(Integer page, Integer rows, String key) {
        //分页
        PageHelper.startPage(page, rows);

        Example example = new Example(Data.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(key.trim())){
            criteria.andLike("title","%"+key+"%");
        }
        List<Data> dataList = dataTBMapper.selectByExample(example);
        if (dataList == null || dataList.size()==0){
            throw new RuntimeException("找不这个关键词");
        }

        PageInfo<Data> pageInfo = new PageInfo<>(dataList);


        return new PageResult<>(pageInfo.getTotal(),pageInfo.getPages(),dataList);
    }
}
