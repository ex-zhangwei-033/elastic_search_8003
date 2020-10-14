package com.zhangwei.example.springbootdemo80.elasticsearch.entites;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Person2 {
    private String name;
    private int age;
    private String newName;
}
