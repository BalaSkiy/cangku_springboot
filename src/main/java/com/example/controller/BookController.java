package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.QueryPageParam;
import com.example.common.Result;
import com.example.entity.Book;
import com.example.service.IBookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ch
 * @since 2023-05-25
 */
@RestController
@RequestMapping("/book")
public class BookController {
    @Resource
    private IBookService bookService;

    @GetMapping("/list")
    public List<Book> list() {
        return bookService.list();
    }

    @GetMapping("/findById")
    public Result findById(@RequestParam String bid) {
        List list = bookService.lambdaQuery().eq(Book::getBid, bid).list();
        return list.size() > 0 ? Result.success(list) : Result.fail();
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Book book) {
        return bookService.save(book) ? Result.success() : Result.fail();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Book book) {
        return bookService.updateById(book) ? Result.success() : Result.fail();
    }

    //删除
    @GetMapping("/delete")
    public Result del(@RequestParam String bid) {
        return bookService.removeById(bid) ? Result.success() : Result.fail();
    }

    //修改
    @PostMapping("/mod")
    public boolean mod(@RequestBody Book book) {
        return bookService.updateById(book);
    }

    //新增或修改
    @GetMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody Book book) {
        return bookService.saveOrUpdate(book);
    }

    //查询（模糊、匹配）
    @PostMapping("/listP")
    public Result listP(@RequestBody Book book) {
        LambdaQueryWrapper<Book> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(book.getBname())) {
            lambdaQueryWrapper.like(Book::getBname, book.getBname());
        }

        return Result.success(bookService.list(lambdaQueryWrapper));
    }

    @GetMapping("/listPage")
    public List<Book> listPage(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String bname = (String) param.get("bname");

        Page<Book> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Book> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(Book::getBname, bname);

        IPage result = bookService.page(page, lambdaQueryWrapper);
        System.out.println("total==" + result.getTotal());
        return result.getRecords();
    }

    @PostMapping("/listPageC")
    public Result listPageC(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String bname = (String) param.get("bname");

        Page<Book> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Book> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(bname) && !"null".equals(bname)) {
            lambdaQueryWrapper.like(Book::getBname, bname);
        }

        IPage result = bookService.page(page, lambdaQueryWrapper);
        System.out.println("total==" + result.getTotal());
        return Result.success(result.getRecords(), result.getTotal());
    }
}
