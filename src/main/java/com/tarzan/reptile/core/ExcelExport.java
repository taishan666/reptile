package com.tarzan.reptile.core;

import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * excel导出
 *
 * @copyright (c) 2019 LuoYang TuLian Co'Ltd Inc. All rights reserved.
 * @date 2020/7/28$ 13:35$
 * @since JDK1.8
 */
public class ExcelExport {

    public static void main(String[] args) {
        List<StudentBean> rows = Lists.newArrayList();
        rows.add(new StudentBean("张三", 21, 80, "合格", DateUtil.date()));
        rows.add(new StudentBean("李四", 21, 96, "优秀", DateUtil.date()));
        rows.add(new StudentBean("王五", 21, 78, "不及格", DateUtil.date()));

        // 通过工具类创建writer
        BigExcelWriter writer = ExcelUtil.getBigWriter("d:/grade_score.xlsx");
        //自定义标题别名
        writer.addHeaderAlias("name", "姓名");
        writer.addHeaderAlias("age", "年龄");
        writer.addHeaderAlias("score", "分数");
        writer.addHeaderAlias("isPass", "是否通过");
        writer.addHeaderAlias("examDate", "考试时间");
        // 合并单元格后的标题行，使用默认标题样式
        writer.merge(4, "一班成绩单");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(rows, true);
        // 关闭writer，释放内存
        writer.close();
    }

    //学生内部类
    @Data
    @AllArgsConstructor
    static class StudentBean {
        private String name;
        private Integer age;
        private Integer score;
        private String isPass;
        private Date examDate;
    }

}
