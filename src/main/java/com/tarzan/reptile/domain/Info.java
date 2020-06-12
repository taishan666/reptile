package com.tarzan.reptile.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * Created by tarzan liu on 2018/2/5.
 */
@Data
@ToString
public class Info {
    private String aid;
    private String appmsgid;
    private String cover;
    private String digest;
    private String itemidx;
    private String link;
    private String title;
    @SerializedName("update_time")
    private Long updateTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date releaseTime;

}
