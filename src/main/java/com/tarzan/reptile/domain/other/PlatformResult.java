package com.tarzan.reptile.domain.other;

import com.tarzan.reptile.domain.entity.PlatformEntity;
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
