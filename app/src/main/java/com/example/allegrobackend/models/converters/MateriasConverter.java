package com.example.allegrobackend.models.converters;

import androidx.room.TypeConverter;

import com.example.allegrobackend.models.Materias;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MateriasConverter {
    @TypeConverter
    public static ArrayList<Materias> storedStringToMaterias(String data) {
        Gson gson = new Gson();
        if (data == null) {
            return new ArrayList<>();
        }
        Type listType = new TypeToken<List<Materias>>() {}.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String materiasToStoredString(ArrayList<Materias> myObjects) {
        Gson gson = new Gson();
        return gson.toJson(myObjects);
    }
}
