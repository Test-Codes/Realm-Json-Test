package com.thefinestartist.realm.json;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.thefinestartist.realm.json.models.AllTypes;
import com.thefinestartist.realm.json.models.AllTypesGen;

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
    }

    private void tryMoshi(AllTypes allTypes) {
        try {
            final Moshi moshi = new Moshi.Builder().build();
            final JsonAdapter<AllTypes> adapter = moshi.adapter(AllTypes.class);
            Log.d("json", "Moshi(Realm Object): " + adapter.toJson(allTypes));


            final AllTypes allTypesProxy;

            realm.beginTransaction();
            {
                allTypesProxy = realm.copyToRealm(allTypes);
            }
            realm.commitTransaction();

            Log.d("json", "Moshi(Proxy Object): " + adapter.toJson(allTypesProxy));
        } catch (Exception e) {
            Log.d("json", "Moshi: error", e);
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

        Log.d("json", "Gson(Realm Object): " + gson.toJson(allTypes));


        final AllTypes allTypesProxy;

        realm.beginTransaction();
        {
            allTypesProxy = realm.copyToRealm(allTypes);
        }
        realm.commitTransaction();

        Log.d("json", "Gson(Proxy Object): " + gson.toJson(allTypesProxy));
    }

    private void tryJPP(AllTypes allTypes) {
        try {
            StringWriter writer = new StringWriter();
            AllTypesGen.encode(writer, allTypes);
            writer.close();

            Log.d("json", "JPP(Realm Object): " + writer.toString());


            final AllTypes allTypesProxy;

            realm.beginTransaction();
            {
                allTypesProxy= realm.copyToRealm(allTypes);
            }
            realm.commitTransaction();

            writer = new StringWriter();
            AllTypesGen.encode(writer, allTypesProxy);
            writer.close();

            Log.d("json", "JPP(Proxy Object): " + writer.toString());
        } catch (Exception e) {
            Log.d("json", "JPP: error", e);
        }
    }

    private void tryJackson(AllTypes allTypes) {
        try {
            final ObjectMapper mapper = new ObjectMapper();

            Log.d("json", "Jackson(Realm Object): " + mapper.writeValueAsString(allTypes));


            final AllTypes allTypesProxy;

            realm.beginTransaction();
            {
                allTypesProxy = realm.copyToRealm(allTypes);
            }
            realm.commitTransaction();

            Log.d("json", "Jackson(Proxy Object): " + mapper.writeValueAsString(allTypesProxy));
        } catch (Exception e) {
            Log.d("json", "Jackson: error", e);
        }
    }
}
