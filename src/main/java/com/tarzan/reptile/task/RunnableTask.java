package com.tarzan.reptile.task;


import com.tarzan.reptile.service.ReptileService;
import com.tarzan.reptile.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author liu yapeng
 * @version 1.0
 * @date 2020/8/5$ 10:12$
 * @since JDK1.8
 */
@Component
@Slf4j
public class RunnableTask implements Runnable {

    @Autowired
    private ReptileService reptileService= SpringUtils.getBean("reptileService");

    private Integer i=0;

    @Override
    public void run() {
        i++;
        log.info("===================第"+i+"轮======================");
        log.info("=============第"+i+"轮采集数据start================");
        reptileService.crawling();
        log.info("=============第"+i+"轮采集数据end==================");
    }
}
