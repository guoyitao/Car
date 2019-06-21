package com.example.yepa.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "errorpage_tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorPage {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String page;
}
