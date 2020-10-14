package com.zhangwei.es;


import com.zhangwei.example.springbootdemo80.SpringBootDemo80Application;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SpringBootDemo80Application.class }) // 指定启动类
public abstract class ApiTestBean{
}
