package com.zhangwei.example.springbootdemo80.elasticsearch.service.impl;

import com.zhangwei.example.springbootdemo80.elasticsearch.entites.Address;
import com.zhangwei.example.springbootdemo80.elasticsearch.entites.BankQutlet;
import com.zhangwei.example.springbootdemo80.elasticsearch.service.BankingOutLetsService;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BankingOutLetsServiceImpl implements BankingOutLetsService {

    @Override
    public void syncOutlets() {
        List<Map<String,Object>> list = null;
        List<IndexQuery> indexQueries = convertIndexQueries(list);
    }

    private List<IndexQuery>  convertIndexQueries(List<Map<String,Object>> outlets){


        List<IndexQuery> indexQueries = new ArrayList<>();
        for(Map map:outlets){
            BankQutlet bankQutlet = new BankQutlet();
            bankQutlet.setId((String)map.get("id"));

            Address address = new Address();
            address.setCity((String) map.get("city"));

            bankQutlet.setAddress(address);

            IndexQuery indexQuery = new IndexQuery();
            indexQuery.setObject(bankQutlet);
            indexQuery.setIndexName("cmb_outlet");
            indexQuery.setType("cmb_outlet");
            indexQueries.add(indexQuery);
        }
        return indexQueries;
    }
}
