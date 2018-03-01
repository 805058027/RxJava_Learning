package com.example.administrator.rxjava_learning.net.gson;

import com.google.gson.JsonElement;

public interface GsonEnum<E extends Enum<E>> {

    String serialize();

    E deserialize(JsonElement jsonEnum);
}