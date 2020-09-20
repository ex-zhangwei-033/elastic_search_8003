package com.zhangwei.example.springbootdemo80.elasticsearch.common;

import lombok.*;

import java.util.List;

/**
 * @program: spring-boot-demo-80
 * @description
 * @author: 张伟
 * @create: 2020-09-20 22:54
 **/
@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Page<T> {
    private int pageNo;
    private int pageSize;
    private long totalHits;
    private List<T> list;
}
