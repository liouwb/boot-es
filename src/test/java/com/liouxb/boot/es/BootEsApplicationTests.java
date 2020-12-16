package com.liouxb.boot.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liouxb.boot.es.domain.TestEs;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class BootEsApplicationTests {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    void contextLoads() {
    }

    /**
     * 创建索引 表
     */
    @Test
    void createIndex() throws IOException {
        // 1、创建索引请求
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("test_ex");
        // 2、执行创建请求
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
    }

    /**
     * 获取索引(判断索引是否存在)
     */
    @Test
    void getIndex() throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest("test_es");
        boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        System.out.println(exists); // true
    }

    /**
     * 删除索引
     */
    @Test
    void deleteIndex() throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("test_ex");
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged()); // true
    }

    /**
     * 添加文档
     */
    @Test
    void addDocument() throws IOException {
        // 创建对象
        TestEs testEs = new TestEs(100, "测试姓名1", 18, LocalDateTime.now(), LocalDateTime.now(), false);
        // 创建请求
        IndexRequest indexRequest = new IndexRequest("test_es");
        // 规则 PUT test_es/_doc/1
        indexRequest.id("1");
        indexRequest.timeout(TimeValue.timeValueSeconds(1));
        // 将我们的数据放入请求 json
//        indexRequest.source(JSON.toJSONString(user), XContentType.JSON);
        indexRequest.source(new ObjectMapper().writeValueAsString(testEs), XContentType.JSON);
        // 客户端发送请求， 获取响应的结果
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        System.out.println(indexResponse.toString());
        System.out.println(indexResponse.status());
    }

    /**
     * 判断文档是否存在
     */
    @Test
    void isExistsDocument() throws IOException {
        GetRequest getRequest = new GetRequest("test_es");
        // 不获取返回的 _source上下文
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        boolean exists = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println(exists); // true
    }

    /**
     * 获取文档信息
     */
    @Test
    void getDocument() throws IOException {
        GetRequest getRequest = new GetRequest("test_es");
        GetResponse response = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        // 打印文档的内容
        System.out.println(response.getSourceAsString()); // {"age":18,"name":"高志红"}
        System.out.println(response); // {"_index":"gao_index","_type":"_doc","_id":"1","_version":1,"_seq_no":0,"_primary_term":1,"found":true,"_source":{"age":18,"name":"高志红"}}
    }

    /**
     * 更新文档
     */
    @Test
    void updateDocument() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest("test_es", "int", "1");
        updateRequest.timeout(TimeValue.timeValueSeconds(1));
        TestEs testEs = new TestEs(111, "测试xingming", 18, LocalDateTime.now(), LocalDateTime.now(), false);

        updateRequest.doc(new ObjectMapper().writeValueAsString(testEs), XContentType.JSON);
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(updateResponse.status()); // OK
    }

    /**
     * 删除文档记录
     */
    @Test
    void deleteDocument() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("test_es");
        deleteRequest.timeout(TimeValue.timeValueSeconds(1));
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(deleteResponse.status()); // OK
    }

    /**
     * 批量插入数据
     */
    @Test
    void bulkAddData() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout(TimeValue.timeValueSeconds(10));
        List<TestEs> userList = new ArrayList<>();
        userList.add(new TestEs(131, "测试xingming", 18, LocalDateTime.now(), LocalDateTime.now(), false));
        userList.add(new TestEs(132, "测试xingming", 18, LocalDateTime.now(), LocalDateTime.now(), false));
        userList.add(new TestEs(133, "测试xingming", 18, LocalDateTime.now(), LocalDateTime.now(), false));
        userList.add(new TestEs(134, "测试xingming", 18, LocalDateTime.now(), LocalDateTime.now(), false));
        // 批处理请求
        for (int i = 0; i < userList.size(); i++) {
            bulkRequest.add(new IndexRequest("gao_index")
                    .id(i + 1 + "")
                    .source(new ObjectMapper().writeValueAsString(userList.get(i)), XContentType.JSON));
        }
        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulkResponse.hasFailures()); // 是否失败 false 代表没有失败
    }

    /**
     * 花样查询二十四式
     */
    @Test
    void Search() throws IOException {
        SearchRequest searchRequest = new SearchRequest("gao_index");
        // 构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 我们可以使用QueryBuilders工具类来实现
        // QueryBuilders.matchAllQuery() 匹配所有
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("name", "高志红");
        searchSourceBuilder.query(matchQueryBuilder);
        // 分页
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(1);
        searchSourceBuilder.timeout(TimeValue.timeValueSeconds(10));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(new ObjectMapper().writeValueAsString(searchResponse.getHits()));
        System.out.println("—————————————我是分隔线————————————");
        for (SearchHit documentFields : searchResponse.getHits().getHits()) {
            System.out.println(documentFields.getSourceAsMap());
        }
    }
}
