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
@TableName( value ="t_plat_form" )
public class PlatformEntity {

    @SerializedName("fakeid")
    private String fakeId;
    private String nickname;
    private String alias;
    @SerializedName("round_head_img")
    private String roundHeadImg;
    @SerializedName("service_type")
    private int serviceType;

}
