package com.zhangwei.example.springbootdemo80.elasticsearch.entites;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Location{
        private double lon;
        private double lat;
    }

    private String province;
    private String city;
    private String district;
    private String detail;
    private Location Location;
}
