package com.zhangwei.example.springbootdemo80.elasticsearch.service;

public interface BankingOutLetsService {

    void syncOutlets();

    boolean deleteIndex(String index);

    void createIndexByWordProfile(String index);
}
