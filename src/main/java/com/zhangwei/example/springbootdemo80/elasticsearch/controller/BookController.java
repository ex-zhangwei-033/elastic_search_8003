package com.zhangwei.example.springbootdemo80.elasticsearch.controller;

import com.alibaba.fastjson.JSON;
import com.zhangwei.example.springbootdemo80.elasticsearch.common.BaseResult;
import com.zhangwei.example.springbootdemo80.elasticsearch.common.Page;
import com.zhangwei.example.springbootdemo80.elasticsearch.entites.BookModel;
import com.zhangwei.example.springbootdemo80.elasticsearch.service.BookService;
import com.zhangwei.example.springbootdemo80.elasticsearch.vo.BookRequestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: spring-boot-demo-80
 * @description
 * @author: 张伟
 * @create: 2020-09-20 21:56
 **/
@Slf4j
@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    /**
     * 列表分页查询
     */
    @GetMapping("/list")
    public BaseResult<Page<BookModel>> list(BookRequestVO bookRequestVO) {
        Page<BookModel> list = bookService.list(bookRequestVO);
        if (list == null) {
            return BaseResult.error(201, "操作失败！");
        }
        return BaseResult.success(list);
    }

    /**
     * 查看文档
     */
    @GetMapping("/detail")
    public BaseResult detail(String id) {
        if (null == id) {
            return BaseResult.error("ID不能为空");
        }
        BookModel book = bookService.detail(id);
        return BaseResult.success(book);
    }

    /**
     * 添加文档
     */
    @PostMapping("/add")
    public BaseResult add(@RequestBody BookModel bookModel) {
        bookService.save(bookModel);
        log.info("插入文档成功！请求参数: {}", JSON.toJSONString(bookModel));
        return BaseResult.success();
    }

    /**
     * 删除文档
     */
    @GetMapping("/delete")
    public BaseResult delete(String id) {
        if (null == id) {
            return BaseResult.error("ID不能为空");
        }
        bookService.delete(id);
        return BaseResult.success();
    }

}
