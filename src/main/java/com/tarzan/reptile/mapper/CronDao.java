package com.tarzan.reptile.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @author liu yapeng
 * @version 1.0
 * @company 洛阳图联科技有限公司
 * @copyright (c) 2019 LuoYang TuLian Co'Ltd Inc. All rights reserved.
 * @date 2020/8/5$ 11:23$
 * @since JDK1.8
 */
@Mapper
@Component
public interface CronDao {

    @Select("select cron from t_cron limit 1")
    String getCron();
}
