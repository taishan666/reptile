package com.tarzan.reptile.service;


import com.tarzan.reptile.mapper.CronDao;
import com.tarzan.reptile.task.RunnableTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledFuture;


/**
 * @author tarzan
 * @version 1.0
 * @date 2020/8/5$ 10:07$
 * @since JDK1.8
 */
@Slf4j
@Service
public class DynamicTaskService {


    @Autowired
    private CronDao cronDao;

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private ScheduledFuture<?> future;

    public static int taskStatus=0;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    public  void startCron() {
        String cron=cronDao.getCron();
        if(StringUtils.isBlank(cron)){
            taskStatus=0;
            log.error("定时任务开启失败");
        }else{
            if(taskStatus==0){
                log.info("定时任务开启成功");
                RunnableTask runnableTask= new RunnableTask();
                runnableTask.run();
                taskStatus=1;
                future = threadPoolTaskScheduler.schedule(runnableTask, triggerContext -> new CronTrigger(cron).nextExecutionTime(triggerContext));
            }else{
                log.info("定时任务正在执行");
            }

        }
    }

    public void stopCron() {
        if (future != null) {
            future.cancel(true);
        }
        taskStatus=0;
        log.info("定时任务关闭成功");
    }

}
