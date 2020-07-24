package com.tarzan.reptile.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

/**
 * Created by tarzan liu on 2018/2/5.
 */
@Data
@ToString
@TableName( value ="t_public_account_article" )
public class InfoEntity {
    private String aid;
    private String appmsgid;
    private String cover;
    private String digest;
    private String itemidx;
    private String link;
    private String title;
    @SerializedName("create_time")
    private Long createTime;
    @SerializedName("update_time")
    private Long updateTime;

}
