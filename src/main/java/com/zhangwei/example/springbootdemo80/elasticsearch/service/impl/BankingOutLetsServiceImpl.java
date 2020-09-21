package com.zhangwei.example.springbootdemo80.elasticsearch.service.impl;

import com.alibaba.fastjson.JSON;
import com.zhangwei.example.springbootdemo80.elasticsearch.entites.Address;
import com.zhangwei.example.springbootdemo80.elasticsearch.entites.BankQutlet;
import com.zhangwei.example.springbootdemo80.elasticsearch.service.BankingOutLetsService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BankingOutLetsServiceImpl implements BankingOutLetsService {
    @Autowired
    private RestHighLevelClient client;

    @Override
    public void syncOutlets() {
        List<Map<String, Object>> list = null;
        List<IndexQuery> indexQueries = convertIndexQueries(list);
    }

    @Override
    public boolean deleteIndex(String index) {
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        AcknowledgedResponse deleteIndexResponse = null;
        try {
            deleteIndexResponse = client.indices().delete(request, RequestOptions.DEFAULT);
            boolean acknowledged = deleteIndexResponse.isAcknowledged();
            log.info("deleteIndexResponse:{}", JSON.toJSONString(deleteIndexResponse));
            System.out.println("deleteIndexResponse:" + deleteIndexResponse);
            System.out.println("acknowledged:" + acknowledged);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (ElasticsearchStatusException elasticsearchStatusException) {
            log.info("该index不存在！");
            return false;
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public void createIndexByWordProfile(String index) {
        //创建名称为blog2的索
        CreateIndexRequest request = new CreateIndexRequest(index);

        try {
            //设置映射 doc type名称
            //设置映射 doc type名称
            request.mapping(index, "", XContentType.JSON);


            CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
            log.info("createIndexResponse:{}", JSON.toJSONString(createIndexResponse));

            //释放资源
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private List<IndexQuery> convertIndexQueries(List<Map<String, Object>> outlets) {
        List<IndexQuery> indexQueries = new ArrayList<>();
        for (Map map : outlets) {
            BankQutlet bankQutlet = new BankQutlet();
            bankQutlet.setId((String) map.get("id"));

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

    //批量插入
    private void bulkPutIndex(List<Map<String, Object>> list, String index, String type) throws IOException {

        int size = list.size();
        BulkRequest request = new BulkRequest();
        for (int i = 0; i < size; i++) {
            Map<String, Object> map = list.get(i);
            //这里必须每次都使用new IndexRequest(index,type),不然只会插入最后一条记录（这样插入不会覆盖已经存在的Id，也就是不能更新）
            //request.add(new IndexRequest(index,type).opType("create").id(map.remove("id").toString()).source(map));
            request.add(new IndexRequest(index, type, String.valueOf(map.get("id"))).source(map, XContentType.JSON));
        }

        client.bulk(request, RequestOptions.DEFAULT);
    }

}
