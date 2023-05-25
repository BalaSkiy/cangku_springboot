package com.example.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.sql.Date;


/**
 * <p>
 *
 * </p>
 *
 * @author ch
 * @since 2023-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Book implements Serializable {

    @TableId
    private Integer bid;
    /**
     * 书名
     */
    private String bname;
    /**
     * 书籍类别
     */
    private String bclass;
    /**
     * 书籍作者
     */
    private String bauthor;
    /**
     * 出版时间
     */
    private Date bpubdate;
    /**
     * 入库时间
     */
    private java.util.Date bindate;


}
