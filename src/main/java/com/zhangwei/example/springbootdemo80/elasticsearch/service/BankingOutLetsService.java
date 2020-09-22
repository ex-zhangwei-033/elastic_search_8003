package com.zhangwei.example.springbootdemo80.elasticsearch.service;

public interface BankingOutLetsService {


    boolean deleteIndex(String index);

    void createIndexByWordProfile(String index);
}
