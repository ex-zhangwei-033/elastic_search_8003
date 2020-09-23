package com.zhangwei.example.springbootdemo80.newelasticsearch.entites;


import lombok.*;

@Getter@Setter
public class TestsGeo {
    private Long id;


    private String name;


    private Location location;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Location{
        private double lon;
        private double lat;
    }
}
