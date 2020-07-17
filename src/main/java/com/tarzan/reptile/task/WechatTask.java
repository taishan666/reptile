package com.tarzan.reptile.task;

import com.tarzan.reptile.service.ReptileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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

     Integer i=0;

    @Autowired
    private ReptileService reptileService;


    @PostConstruct
    public  void init(){
      //  ReptileDemo.crawling();
        reptileService.crawling();
    }

    @Async
    @Scheduled(cron = "0 */30 * * * ?")
    public void task(){
        i++;
        logger.info("===================第"+i+"轮======================");
        logger.info("===========每隔一分钟更新警告数据start==============");
        //ReptileDemo.crawling();
        reptileService.crawling();
        logger.info("===========每隔一分钟更新警告数据end================");
    }

}
