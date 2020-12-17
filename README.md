# boot-es

## 安装elasticsearch

- 下载地址：[https://www.elastic.co/guide/en/elastic-stack/index.html](https://www.elastic.co/guide/en/elastic-stack/6.7/elastic-stack.html)

![image-20201217104958712](README.assets/image-20201217104958712.png)

- 下载之后直接解压到相应目录

![image-20201217105107810](README.assets/image-20201217105107810.png)

- 启动es bin目录下

![image-20201217105537453](README.assets/image-20201217105537453.png)

- 启动成功 访问地址 http://localhost:9200/

![image-20201217105752900](README.assets/image-20201217105752900.png)

- 访问

![image-20201217105819163](README.assets/image-20201217105819163.png)

##  安装kibana

- 下载kibana版本与es版本一致
- 下载之后也解压到相关目录

![image-20201217105452458](README.assets/image-20201217105452458.png)

- 运行kibana 启动之后默认地址 http:localhost:5601

![image-20201217110102924](README.assets/image-20201217110102924.png)

- 访问kinaba

![image-20201217110259570](README.assets/image-20201217110259570.png)

- 访问之后默认是英文的 然后进行汉化 
- 修改config目录下的kibana.yml配置文件 最后一行添加 `i18n.locale: "zh-CN`

![image-20201217110502450](README.assets/image-20201217110502450.png)

- 修改配置文件之后重启kinaba

![image-20201217110733009](README.assets/image-20201217110733009.png)



## 配置ik分词器插件

- 同样在elasticsearch官网下载和es版本一致的ik分词器插件

![image-20201217110927066](README.assets/image-20201217110927066.png)

- 把下载的ik分词器插件解压后放到ik目录

![image-20201217111020074](README.assets/image-20201217111020074.png)

- 重启es

![image-20201217111115835](README.assets/image-20201217111115835.png)

## kinaba使用

![image-20201217111308298](README.assets/image-20201217111308298.png)

```json
# 添加数据 修改数据(修改数据)
put /kcloud/_doc/2
{
  "name":"zs",
  "title":"张三",
  "age":19,
  "created":"2018-12-25"
}

# 删除索引
DELETE kcloud

# 查询索引信息
GET test_es

# 创建索引 并创建field的数据结构
PUT /test_es
{
  "mappings": {
    "_doc": { 
      "properties": { 
        "name":     { "type": "text"  }, 
        "age":      { "type": "integer" },  
        "createdAt":  {
          "type":   "date", 
          "format": "strict_date_optional_time||epoch_millis"
        },
        "updatedAt":  {
          "type":   "date", 
          "format": "strict_date_optional_time||epoch_millis"
        },
        "deleted": { "type": "boolean" }
      }
    }
  }
}

# 查询索引test_es —doc类型(7.X以后不推荐使用，8.x取消)  id为4的数据记录
GET /test_es/_doc/4

PUT /test_es/_doc/3
{
  "name":"张三-46542634",
  "age":12
}

# 获取分词结果
GET _analyze
{
  "analyzer" : "ik_smart",  
  "text" : "床前明月光"
  
} 

# 采用ik分词器的 最小划分粒度
GET _analyze
{
  "analyzer" : "ik_max_word",  
  "text" : "床前明月光"
} 

# ik分词最大划分粒度
GET _analyze
{
  "analyzer" : "ik_max_word",
  "text" : "中国"
}
```



## springboot 2.3.2集成es 6.7.2

#### 一、添加依赖

```xml
        <dependency>
            <groupId>org.elasticsearch</groupId>
            <artifactId>elasticsearch</artifactId>
            <version>6.7.2</version>
        </dependency>
        <dependency>
            <groupId>org.elasticsearch.client</groupId>
            <artifactId>elasticsearch-rest-high-level-client</artifactId>
            <version>6.7.2</version>
        </dependency>
```

#### 二、配置

![image-20201217111454280](README.assets/image-20201217111454280.png)

#### 三、java restapi操作es

![image-20201217111555441](README.assets/image-20201217111555441.png)