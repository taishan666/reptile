package com.tarzan.reptile.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * Created by tarzan liu on 2018/2/5.
 */
@Data
@TableName()
public class InfoEntity {
    private String aid;
    private String appmsgid;
    private String cover;
    private String digest;
    private String itemidx;
    private String link;
    private String title;
    private Long updateTime;
    private Date releaseTime;

}
