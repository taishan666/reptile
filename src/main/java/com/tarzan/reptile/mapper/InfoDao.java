package com.tarzan.reptile.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tarzan.reptile.domain.entity.InfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 公众号文章信息数据层
 *
 * @author liu yapeng
 * @version 1.0
 * @company 洛阳图联科技有限公司
 * @copyright (c) 2019 LuoYang TuLian Co'Ltd Inc. All rights reserved.
 * @date 2020/7/1$ 16:28$
 * @since JDK1.8
 */
@Mapper
@Component
public interface InfoDao extends BaseMapper<InfoEntity> {

    void insertUnique(InfoEntity entity);

    void insertList(@Param("list") List<InfoEntity> list);
}
