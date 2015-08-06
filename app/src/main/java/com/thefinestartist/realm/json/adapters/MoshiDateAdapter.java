package com.thefinestartist.realm.json.adapters;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.util.Date;

/**
 * Created by TheFinestArtist on 8/6/15.
 */
public class MoshiDateAdapter {

    @ToJson
    long dateToJson(Date d) {
        return d.getTime();
    }

    @FromJson
    Date jsonToDate(long value) throws Exception {
        return new Date(value);
    }

//    @ToJson
//    String realmToJson(Realm realm) {
//        return null;
//    }
//
//    @FromJson
//    Realm jsonToRealm(String value) throws Exception {
//        return null;
//    }
//
//    @ToJson
//    String rowToJson(Row row) {
//        return null;
//    }
//
//    @FromJson
//    Row jsonToRow(String value) throws Exception {
//        return null;
//    }
//
//    @ToJson
//    String dogToJson(Dog dog) {
//        return null;
//    }
//
//    @FromJson
//    Dog jsonToDog(String value) throws Exception {
//        return null;
//    }
}
