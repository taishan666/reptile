package com.tarzan.reptile.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @author tarzan
 * @version 1.0
 * @date 2020/8/5 11:23
 * @since JDK1.8
 */
@Mapper
@Component
public interface CronDao {

    @Select("select cron from t_cron limit 1")
    String getCron();
}
