package fr.digi.imdb.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.digi.imdb.bo.entity.Itest;
import fr.digi.imdb.bo.entity.Pays;

import java.util.ArrayList;
import java.util.Set;

public class Parser {
    public static String getAsString(JsonObject filmObj,String key){
        if(key.equals("id")){
            return filmObj.get(key).isJsonNull() ? "IdTmpl000random" : filmObj.get(key).getAsString().replaceAll("\\s+","");
        }
        return filmObj.get(key).isJsonNull() ? "" : filmObj.get(key).getAsString();
    }

    public static JsonObject getAsJsonObject(JsonObject filmObj,String key) {
        return filmObj.get(key).getAsJsonObject();
    }

    public static void insertTable(JsonObject filmObj,String key){
        JsonObject lieuTournageObj = getAsJsonObject(filmObj,key);
    }

//    public<T> void myIterator(JsonArray films, int i, String key, Itest obj){
//        JsonObject currentJsonObj = films.get(i).getAsJsonObject();
//        Set<String> objKeys = currentJsonObj.keySet();
//        for (String objKey:objKeys
//             ) {
//            if(currentJsonObj.get(objKey).isJsonPrimitive()){
//                String value =  currentJsonObj.get(objKey).getAsString();
////                list.get(0).setStringAttribute(key, value);
//                obj.setStringAttribute(key, value);
//            }
//        }
//    }

    public static<T> void myIterator(String filmkey, String value,Itest obj){
        obj.setStringAttribute(filmkey,value);
    }


}
