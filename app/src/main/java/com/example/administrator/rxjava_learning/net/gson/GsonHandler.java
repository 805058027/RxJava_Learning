package com.example.administrator.rxjava_learning.net.gson;

import com.example.administrator.rxjava_learning.net.gson.adapter.ByteArrayTypeAdapter;
import com.example.administrator.rxjava_learning.net.gson.adapter.GsonEnumTypeAdapter;
import com.example.administrator.rxjava_learning.net.gson.adapter.TimestampTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Timestamp;

/**
 * toJson的相关方法
 *
 * @author dhua
 */
public class GsonHandler {

    /**
     * 获取GSON转换模式，默认日期格式为“yyyy-MM-dd HH:mm:ss”
     *
     * @return
     */
    public static Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        // 不转换没有 @Expose 注解的字段
        builder.excludeFieldsWithoutExposeAnnotation();
        builder.registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter()).setDateFormat
                ("yyyy-MM-dd HH:mm:ss").create();
        builder.registerTypeHierarchyAdapter(byte[].class, new ByteArrayTypeAdapter()).create();
        return builder.create();
    }

    /**
     * 获取GSON转换模式，枚举类
     *
     * @param baseType 枚举类
     * @return
     */
    public static <E extends Enum<E>> Gson getGson(Class<? extends GsonEnum<E>>... baseType) {
        GsonBuilder builder = new GsonBuilder();
        // 不转换没有 @Expose 注解的字段
        builder.excludeFieldsWithoutExposeAnnotation();
        for (Class<? extends GsonEnum<E>> gsonEnum : baseType) {
            builder.registerTypeAdapter(gsonEnum, new GsonEnumTypeAdapter<>(gsonEnum));
        }
        builder.registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter()).setDateFormat
                ("yyyy-MM-dd HH:mm:ss").create();
        builder.registerTypeHierarchyAdapter(byte[].class, new ByteArrayTypeAdapter()).create();
        return builder.create();
    }

    /**
     * 获取GSON转换模式，设置时间格式为dataFat
     *
     * @param dataFat 时间格式
     * @return
     */
    public static Gson getGson(String dataFat) {
        GsonBuilder builder = new GsonBuilder();
        // 不转换没有 @Expose 注解的字段
        builder.excludeFieldsWithoutExposeAnnotation();
        builder.registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter()).setDateFormat
                (dataFat).create();
        builder.registerTypeHierarchyAdapter(byte[].class, new ByteArrayTypeAdapter()).create();
        return builder.create();
    }

    public static Gson getNoExportGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter()).setDateFormat
                ("yyyy-MM-dd HH:mm:ss").create();
        builder.registerTypeHierarchyAdapter(byte[].class, new ByteArrayTypeAdapter()).create();
        return builder.create();
    }
}