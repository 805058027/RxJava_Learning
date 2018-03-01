package com.example.administrator.rxjava_learning.net.gson.adapter;

import com.example.administrator.rxjava_learning.net.gson.GsonEnum;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;


public class GsonEnumTypeAdapter<E extends Enum<E>> implements JsonSerializer<E>, JsonDeserializer<E> {

    private final Class<? extends GsonEnum<E>> gsonEnum;

    public GsonEnumTypeAdapter(Class<? extends GsonEnum<E>> gsonEnum) {
        this.gsonEnum = gsonEnum;
    }



    @Override
    public JsonElement serialize(E src, Type typeOfSrc, JsonSerializationContext context) {
        if (null != src && src instanceof GsonEnum) {
            return new JsonPrimitive(((GsonEnum) src).serialize());
        }
        return null;
    }

    @Override
    public E deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json != null) {
            GsonEnum[] objs = gsonEnum.getEnumConstants();
            return (E) objs[0].deserialize(json);
        }
        return null;
    }

}