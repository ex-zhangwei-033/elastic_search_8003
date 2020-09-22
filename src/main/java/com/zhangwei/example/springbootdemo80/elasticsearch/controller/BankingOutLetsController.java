package com.zhangwei.example.springbootdemo80.elasticsearch.controller;

import com.zhangwei.example.springbootdemo80.elasticsearch.common.BaseResult;
import com.zhangwei.example.springbootdemo80.elasticsearch.entites.BankQutlet;
import com.zhangwei.example.springbootdemo80.elasticsearch.service.BankingOutLetsService;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankingOutLetsController {
    @Autowired
    private BankingOutLetsService bankingOutLetsService;

    /**
     * 初始化数据
     *
     */
    @RequestMapping("/syncOutlets")
    public BaseResult  syncOutlets(){
        bankingOutLetsService.syncOutlets();
        return BaseResult.success();
    }
    /**
     * 创建索引和映射
     *
     */
    @RequestMapping("/createIndex")
    public BaseResult createIndexByWordProfile(String index){
        bankingOutLetsService.createIndexByWordProfile(index);
        return BaseResult.success();
    }


    public static void main(String[] args) {
        GeoDistanceSortBuilder geoDistanceSortBuilder = new GeoDistanceSortBuilder("", new GeoPoint(1,1));
        GeoDistanceQueryBuilder geoDistanceQueryBuilder = new GeoDistanceQueryBuilder("");
    }

}
