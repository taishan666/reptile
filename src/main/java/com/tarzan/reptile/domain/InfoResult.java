package com.tarzan.reptile.domain;

import com.google.gson.annotations.SerializedName;
import com.tarzan.reptile.entity.InfoEntity;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by tarzan liu on 2018/2/5.
 */
@Data
@ToString
public class InfoResult {
    @SerializedName("app_msg_list")
    private List<InfoEntity> appMsgList;
    @SerializedName("app_msg_cnt")
    private Integer totalCount;
}
