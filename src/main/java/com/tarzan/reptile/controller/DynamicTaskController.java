package com.tarzan.reptile.controller;

import com.tarzan.reptile.service.DynamicTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 定时任务控制器
 *
 * @author liu yapeng
 * @version 1.0
 * @date 2020/8/5$ 10:25$
 * @since JDK1.8
 */
@Controller
public class DynamicTaskController {

    @Autowired
    private DynamicTaskService taskService;


    @GetMapping("/task/start")
    private void start(Model model){
        taskService.startCron();
    }

    @GetMapping("/task/stop")
    private void stop(Model model){
        taskService.stopCron();
    }
}
