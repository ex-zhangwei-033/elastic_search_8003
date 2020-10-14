package com.zhangwei.es;

import com.zhangwei.example.springbootdemo80.elasticsearch.entites.Person1;
import com.zhangwei.example.springbootdemo80.elasticsearch.entites.Person2;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class MyTest extends ApiTestBean{

    public static Person2 person2 = null;

    @BeforeClass
    public static void before() {
        person2 = new Person2("zhangsan",12,"lisi");
    }

    @Test
    public void test(){
        int subLength = 400;
        int size =400;

        for(int i=0;i<=size/subLength;i++){
            int fromIndex = i*subLength;
            int toIndex = (i+1)*subLength;
            toIndex = toIndex>size?size:toIndex;
            System.out.println("from:"+fromIndex+"   to"+toIndex);

            List<Integer> list = new ArrayList<>();
            list.add(0);
            list.add(1);
            List<Integer> subList =list.subList(2,2);

            System.out.println(subList.size());


        }
    }

}
