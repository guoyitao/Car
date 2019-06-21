package com.example.yepa.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "haorenka_tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HaorenkaPojo {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String itemUrl;
    private String title;
    private String baiduyunUrl;
    private String baiduyunPass;
    private String zipPassword;

}
