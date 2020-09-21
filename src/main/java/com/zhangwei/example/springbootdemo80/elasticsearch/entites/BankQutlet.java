package com.zhangwei.example.springbootdemo80.elasticsearch.entites;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "cmb_outlet",type="cmb_outlet")
@Data
@ToString
@NoArgsConstructor
public class BankQutlet {
    @Id
    private String id;
    //网点名
    @Field(
            type= FieldType.Text,
            store = true
    )
    private String name;
    //移动电话
    @Field(
            type= FieldType.Text,
            store = true
    )
    private String cellPhone;
    //营业时间
    @Field(
            type= FieldType.Text,
            store = true
    )
    private String businessTime;
    //地址
    @Field(
            type= FieldType.Nested,
            store = true
    )
    private Address address;
    //距离
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private double distance;
}
