package com.zhangwei.example.springbootdemo80.newelasticsearch.entites;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.elasticsearch.common.geo.GeoPoint;

import java.lang.annotation.Documented;

@Getter@Setter
public class TestsGeo {
    private Long id;


    private String name;


    private GeoPoint location;

}
