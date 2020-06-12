package com.tarzan.reptile.domain;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

/**
 * Created by tarzan liu on 2018/2/5.
 */
@Data
@ToString
public class Platform {
    @SerializedName("fakeid")
    private String fakeId;
    private String nickname;
    private String alias;
    @SerializedName("round_head_img")
    private String roundHeadImg;
    @SerializedName("service_type")
    private int serviceType;
}
