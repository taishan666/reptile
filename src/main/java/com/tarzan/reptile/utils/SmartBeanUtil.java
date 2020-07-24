package com.tarzan.reptile.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description: bean操作
 * @Author: tarzan LIU
 * @Date: 2019/10/31 9:05
 */
public class SmartBeanUtil {

    /**
     *
     * 方法描述 不copy为null的属性
     *
     * @param @param source
     * @param @param target
     * @return void
     * @throws
     * @date 2018年3月12日下午4:29:40
     */
    public static void copyPropertiesExcludeNull(Object source, Object target) {
        BeanWrapperImpl wrappedSource = new BeanWrapperImpl(source);
        String[] ignoreProperties = Stream.of(wrappedSource.getPropertyDescriptors()).map(FeatureDescriptor::getName).filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
        BeanUtils.copyProperties(source, target, ignoreProperties);
    }

    /**
     * 复制bean的属性
     *
     * @param source 源 要复制的对象
     * @param target 目标 复制到此对象
     */
    public static void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }

    /**
     * 复制对象
     *
     * @param source 源 要复制的对象
     * @param target 目标 复制到此对象
     * @param <T>
     * @return
     */
    public static <T> T copy(Object source, Class<T> target) {
        try {
            T newInstance = target.newInstance();
            BeanUtils.copyProperties(source, newInstance);
            return newInstance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 复制list
     *
     * @param source
     * @param target
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T, K> List<K> copyList(List<T> source, Class<K> target) {

        if (null == source || source.isEmpty()) {
            return Collections.emptyList();
        }
        return source.stream().map(e -> copy(e, target)).collect(Collectors.toList());
    }

    /**
     * @Description:方法描述  生成uuid
     * @Author: Tarzan Liu
     * @Date: 2020/3/26 9:05
     */
    public static String genUUID() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid;
    }

    public static <T> Map<String, T> objectToMap(Object requestParameters) {
        Map<String, T> map = new HashMap<>();
        // 获取f对象对应类中的所有属性域
        Field[] fields = requestParameters.getClass().getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            String varName = fields[i].getName();
            // 获取原来的访问控制权限
            boolean accessFlag = fields[i].isAccessible();
            // 修改访问控制权限
            fields[i].setAccessible(true);
            // 获取在对象f中属性fields[i]对应的对象中的变量
            Object o = null;
            try {
                o = fields[i].get(requestParameters);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (o != null && StringUtils.isNotBlank(o.toString().trim())) {
                map.put(varName, (T)o);
                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            }
        }
        return map;
    }

}
