package com.example.allegroandroid.models.converters;

import androidx.room.TypeConverter;

import com.example.allegroandroid.models.MateriasResponse;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MateriasConverter {
    @TypeConverter
    public static ArrayList<MateriasResponse> storedStringToMaterias(String data) {
        Gson gson = new Gson();
        if (data == null) {
            return new ArrayList<>();
        }
        Type listType = new TypeToken<List<MateriasResponse>>() {}.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String materiasToStoredString(ArrayList<MateriasResponse> myObjects) {
        Gson gson = new Gson();
        return gson.toJson(myObjects);
    }
}
