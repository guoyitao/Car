package com.dalao.pojo;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Table(name = "data_tb")
@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class Data {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String itemUrl;
    private String title;
    private String baiduyunUrl;
    private String baiduyunPass;
    private String zipPassword;
    private String img;
    private List<String> imgs;

}

