package com.zhangwei.es;

import com.alibaba.fastjson.JSON;
import com.zhangwei.example.springbootdemo80.SpringBootDemo80Application;
import com.zhangwei.example.springbootdemo80.newelasticsearch.entites.TestsGeo;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringBootDemo80Application.class}) // 指定启动类
public class ESGeoTest {
    public static String INDEX_TEST = null;
    public static String TYPE_TEST = null;
    public static TestsGeo tests = null;
    public static List<TestsGeo> testsList = null;
    @Autowired
    private RestHighLevelClient client;

    @BeforeClass
    public static void before() {
        INDEX_TEST = "my_info_3"; // 索引名称
        TYPE_TEST = "my_info_3"; // 索引类型
        testsList = new ArrayList<>();
        for (int i = 0; i < 89; i++) {
            tests = new TestsGeo();
            tests.setId(Long.valueOf(i));
            tests.setName("test name  " + i);
            GeoPoint geoPoint = new GeoPoint(Double.valueOf(i),Double.valueOf(i));
//            tests.setLocation(geoPoint);
            testsList.add(tests);
        }
    }



    @Test
    public void test() throws IOException {
        if (!existsIndex(INDEX_TEST)) {
            createIndex();
        }
        add(INDEX_TEST,TYPE_TEST,tests);
//        bulk();
//        searchGeo();
    }

    /**
     * 创建索引
     */
    public void createIndex() {
        try {
            // 创建 Mapping
            XContentBuilder mapping = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("dynamic", "true")
                    .startObject("properties")

                    .startObject("id")
                    .field("type","long")
                    .endObject()

                    .startObject("name")
                    .field("type","text")
                    .endObject()

                    .startObject("location")
                    .field("type","geo_point")
                    .endObject()

                    .endObject()
                    .endObject();
            // 创建索引配置信息，配置
            Settings settings = Settings.builder()
                    .put("index.number_of_shards", 1)
                    .put("index.number_of_replicas", 0)
                    .build();
            // 新建创建索引请求对象，然后设置索引类型（ES 7.0 将不存在索引类型）和 mapping 与 index 配置
            CreateIndexRequest request = new CreateIndexRequest(INDEX_TEST, settings);
            request.mapping(TYPE_TEST, mapping);
            // RestHighLevelClient 执行创建索引
            CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
            // 判断是否创建成功
            boolean isCreated = createIndexResponse.isAcknowledged();
            System.out.println("是否创建成功：{}"+isCreated);
        } catch (IOException e) {
            System.out.println(""+e);
        }
    }


    public void searchGeo() throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX_TEST);
        SearchSourceBuilder ssb = new SearchSourceBuilder();

        //工体的坐标
        GeoPoint geoPoint = new GeoPoint(0.111111111d,0.2222222222d);
        //geo距离查询  name=geo字段
        QueryBuilder qb = QueryBuilders.geoDistanceQuery("location")
                //距离 3KM
                .distance(300d, DistanceUnit.KILOMETERS)
                //坐标工体
                .point(geoPoint);

        ssb.query(qb);
        searchRequest.source(ssb);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getSourceAsString());
        }


    }




    /**
     * 创建索引
     *
     * @param index
     * @throws IOException
     */
    public void createIndex(String index) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(index);
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println("createIndex: " + JSON.toJSONString(createIndexResponse));
    }

    /**
     * 判断索引是否存在
     *
     * @param index
     * @return
     * @throws IOException
     */
    public boolean existsIndex(String index) throws IOException {
        GetIndexRequest request = new GetIndexRequest();
        request.indices(index);
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println("existsIndex: " + exists);
        return exists;
    }

    /**
     * 判断记录是都存在
     * @param index
     * @param type
     * @param tests
     * @return
     * @throws IOException
     */
    public boolean exists(String index, String type, TestsGeo tests) throws IOException {
        GetRequest getRequest = new GetRequest(index, type, tests.getId().toString());
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println("exists: " + exists);
        return exists;
    }

    /**
     * 增加记录
     * @param index
     * @param type
     * @param tests
     * @throws IOException
     */
    public void add(String index, String type, TestsGeo tests) throws IOException {
        IndexRequest indexRequest = new IndexRequest(index, type, tests.getId().toString());
        indexRequest.source(JSON.toJSONString(tests), XContentType.JSON);
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println("add: " + JSON.toJSONString(indexResponse));
    }

    /**
     * 批量操作
     * @throws IOException
     */
    public void bulk() throws IOException {
        // 批量增加
        BulkRequest bulkAddRequest = new BulkRequest();
        for (int i = 0; i < testsList.size(); i++) {
            tests = testsList.get(i);
            IndexRequest indexRequest = new IndexRequest(INDEX_TEST, TYPE_TEST, tests.getId().toString());
            indexRequest.source(JSON.toJSONString(tests), XContentType.JSON);
            bulkAddRequest.add(indexRequest);
        }
        BulkResponse bulkAddResponse = client.bulk(bulkAddRequest, RequestOptions.DEFAULT);
        System.out.println("bulkAdd: " + JSON.toJSONString(bulkAddResponse));
    }
}
