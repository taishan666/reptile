package com.tarzan.reptile.controller;

import com.tarzan.reptile.service.DynamicTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 定时任务控制器
 *
 * @author tarzan
 * @version 1.0
 * @date 2020/8/5$ 10:25$
 * @since JDK1.8
 */
@Controller
public class DynamicTaskController {

    @Autowired
    private DynamicTaskService taskService;


    @GetMapping("/task/start")
    private ModelAndView start(ModelAndView model){
        taskService.startCron();
        model.setViewName("/");
        return model;
    }

    @GetMapping("/task/stop")
    private ModelAndView stop(ModelAndView model){
        taskService.stopCron();
        model.setViewName("/");
        return model;
    }
}
