package com.tarzan.reptile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 请求错误控制器
 *
 * @author liu yapeng
 * @version 1.0
 * @company 洛阳图联科技有限公司
 * @copyright (c) 2019 LuoYang TuLian Co'Ltd Inc. All rights reserved.
 * @date 2020/7/21$ 8:58$
 * @since JDK1.8
 */
@Controller
public class ErrorController {

    @GetMapping("/error/{status}")
    private String error(@PathVariable("status") String status){
        return "error/"+status;
    }

}
