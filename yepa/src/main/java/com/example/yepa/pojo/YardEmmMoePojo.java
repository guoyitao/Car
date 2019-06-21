package com.example.yepa.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "yardemmmoe_tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YardEmmMoePojo {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String itemUrl;
    private String title;
    private String baiduyunUrl;
    private String baiduyunPass;
    private String zipPassword;

    public YardEmmMoePojo(String itemUrl, String title, String baiduyunUrl, String baiduyunPass, String zipPassword) {
        this.itemUrl = itemUrl;
        this.title = title;
        this.baiduyunUrl = baiduyunUrl;
        this.baiduyunPass = baiduyunPass;
        this.zipPassword = zipPassword;
    }
}


