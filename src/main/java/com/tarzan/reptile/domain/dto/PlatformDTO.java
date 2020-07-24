package com.tarzan.reptile.domain.dto;

import lombok.Data;

/**
 * Created by tarzan liu on 2018/2/5.
 */
@Data
public class PlatformDTO {

    private String fakeId;
    private String nickname;
    private String alias;
    private String roundHeadImg;
    private int serviceType;
    //附加字段
    private String imgBase64;
}
