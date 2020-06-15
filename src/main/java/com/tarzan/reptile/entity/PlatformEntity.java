package com.tarzan.reptile.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

/**
 * Created by tarzan liu on 2018/2/5.
 */
@Data
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
