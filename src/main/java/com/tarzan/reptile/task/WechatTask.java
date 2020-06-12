package com.tarzan.reptile.task;

import com.tarzan.reptile.core.ReptileDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 微信公众号任务
 *
 * @author liu yapeng
 * @version 1.0
 * @copyright (c) 2019 LuoYang TuLian Co'Ltd Inc. All rights reserved.
 * @date 2020/6/5$ 14:04$
 * @since JDK1.8
 */
@Component
@EnableScheduling
public class WechatTask {

    private static final Logger logger= LoggerFactory.getLogger("公众号文章采集");

    @Async
    @Scheduled(cron = "*/15 * * * * ?")
    public void task(){
        logger.info("===========每隔一分钟更新警告数据start==============");
        ReptileDemo.crawling();
        logger.info("===========每隔一分钟更新警告数据end================");
    }

}
