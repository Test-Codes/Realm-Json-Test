package com.thefinestartist.realm.json.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.thefinestartist.realm.json.models.AllTypes;
import com.thefinestartist.realm.json.utils.DateUtil;

import java.lang.reflect.Type;

/**
 * Created by TheFinestArtist on 8/6/15.
 */
public class AllTypesSerializer implements JsonSerializer<AllTypes> {

    @Override
    public JsonElement serialize(AllTypes src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("columnString", src.getColumnString());
        jsonObject.addProperty("columnLong", src.getColumnLong());
        jsonObject.addProperty("columnFloat", src.getColumnFloat());
        jsonObject.addProperty("columnDouble", src.getColumnDouble());
        jsonObject.addProperty("columnBoolean", src.isColumnBoolean());
        jsonObject.addProperty("columnDate", DateUtil.getDateFormat(src.getColumnDate()));
        jsonObject.add("columnBinary", context.serialize(src.getColumnBinary()));
        jsonObject.add("columnRealmObject", context.serialize(src.getColumnRealmObject()));
        jsonObject.add("columnRealmList", context.serialize(src.getColumnRealmList()));

        return jsonObject;
    }
}
