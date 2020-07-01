package com.tarzan.reptile.domain;

import com.tarzan.reptile.entity.PlatformEntity;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by tarzan liu on 2018/2/5.
 */
@Data
@ToString
public class PlatformResult {
    private List<PlatformEntity> list;
}
