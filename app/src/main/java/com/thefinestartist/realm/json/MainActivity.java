package com.thefinestartist.realm.json;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.thefinestartist.realm.json.models.AllTypes;
import com.thefinestartist.realm.json.models.AllTypesGen;
import com.thefinestartist.realm.json.serializers.AllTypesSerializer;
import com.thefinestartist.royal.Rson;

import java.io.StringWriter;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmObject;

public class MainActivity extends AppCompatActivity {

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getInstance(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    protected void onResume() {
        super.onResume();

        AllTypes allTypes = new AllTypes();
        allTypes.setColumnBinary(new byte[]{0, 1, 2, 3, 4, 5, 6, 7});
        allTypes.setColumnBoolean(true);
        allTypes.setColumnDate(new Date());
        allTypes.setColumnDouble(Double.MAX_VALUE);
        allTypes.setColumnFloat(Float.MAX_VALUE);
        allTypes.setColumnLong(Long.MAX_VALUE);
        allTypes.setColumnString("allType");

        tryMoshi(allTypes);
        tryGson(allTypes);
        tryJackson(allTypes);
        tryJPP(allTypes);
        trySerialize(allTypes);
        tryRson(allTypes);
    }

    private void tryMoshi(AllTypes allTypes) {
        try {
            final Moshi moshi = new Moshi.Builder().build();
            final JsonAdapter<AllTypes> adapter = moshi.adapter(AllTypes.class);

            Logger.d("Moshi(Realm Object)");
            Logger.json(adapter.toJson(allTypes));

            final AllTypes allTypesProxy;

            realm.beginTransaction();
            {
                allTypesProxy = realm.copyToRealm(allTypes);
            }
            realm.commitTransaction();

            Logger.d("Moshi(Proxy Object)");
            Logger.json(adapter.toJson(allTypesProxy));
        } catch (Exception e) {
            Logger.e("Moshi: error: " + e.getMessage());
        }
    }

    private void tryGson(AllTypes allTypes) {
        final Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();

        Logger.d("Gson(Realm Object)");
        Logger.json(gson.toJson(allTypes));

        final AllTypes allTypesProxy;

        realm.beginTransaction();
        {
            allTypesProxy = realm.copyToRealm(allTypes);
        }
        realm.commitTransaction();

        Logger.d("Gson(Proxy Object)");
        Logger.json(gson.toJson(allTypesProxy));
    }

    private void tryJPP(AllTypes allTypes) {
        try {
            StringWriter writer = new StringWriter();
            AllTypesGen.encode(writer, allTypes);
            writer.close();

            Logger.d("JPP(Realm Object)");
            Logger.json(writer.toString());

            final AllTypes allTypesProxy;

            realm.beginTransaction();
            {
                allTypesProxy = realm.copyToRealm(allTypes);
            }
            realm.commitTransaction();

            writer = new StringWriter();
            AllTypesGen.encode(writer, allTypesProxy);
            writer.close();

            Logger.d("JPP(Proxy Object)");
            Logger.json(writer.toString());
        } catch (Exception e) {
            Logger.e("JPP: error: " + e.getMessage());
        }
    }

    private void tryJackson(AllTypes allTypes) {
        try {
            final ObjectMapper mapper = new ObjectMapper();

            Logger.d("Jackson(Realm Object)");
            Logger.json(mapper.writeValueAsString(allTypes));

            final AllTypes allTypesProxy;

            realm.beginTransaction();
            {
                allTypesProxy = realm.copyToRealm(allTypes);
            }
            realm.commitTransaction();

            Logger.d("Jackson(Proxy Object)");
            Logger.json(mapper.writeValueAsString(allTypesProxy));
        } catch (Exception e) {
            Logger.e("Jackson: error: " + e.getMessage());
        }
    }

    private void trySerialize(AllTypes allTypes) {
        try {
            final Gson gson = new GsonBuilder()
                    .setExclusionStrategies(new ExclusionStrategy() {
                        @Override
                        public boolean shouldSkipField(FieldAttributes f) {
                            return f.getDeclaringClass().equals(RealmObject.class);
                        }

                        @Override
                        public boolean shouldSkipClass(Class<?> clazz) {
                            return false;
                        }
                    })
                    .registerTypeAdapter(Class.forName("io.realm.AllTypesRealmProxy"), new AllTypesSerializer())
                    .create();

            Logger.d("Serialize(Realm Object)");
            Logger.json(gson.toJson(allTypes));

            final AllTypes allTypesProxy;

            realm.beginTransaction();
            {
                allTypesProxy = realm.copyToRealm(allTypes);
            }
            realm.commitTransaction();

            Logger.d("Serialize(Proxy Object)");
            Logger.json(gson.toJson(allTypesProxy));
        } catch (Exception e) {
            Logger.e("Serialize: error: " + e.getMessage());
        }
    }

    private void tryRson(AllTypes allTypes) {
        Logger.d("Rson(Realm Object)");
        Logger.json(Rson.toJsonString(allTypes));

        final AllTypes allTypesProxy;

        realm.beginTransaction();
        {
            allTypesProxy = realm.copyToRealm(allTypes);
        }
        realm.commitTransaction();

        Logger.d("Rson(Proxy Object)");
        Logger.json(Rson.toJsonString(allTypesProxy));
    }
}
