package com.zhangwei.example.springbootdemo80.newelasticsearch.entites;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: spring-boot-demo-80
 * @description
 * @author: 张伟
 * @create: 2020-09-22 22:47
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Tests {
    private Long id;

    private String name;
}
