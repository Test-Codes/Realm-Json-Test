/*
 * Copyright 2014 Realm Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.thefinestartist.realm.json.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@JsonIgnoreProperties({"valid"}) // for Jackson
@JsonModel // fot JPP
public class DogPrimaryKey extends RealmObject {

    @PrimaryKey
    @JsonKey
    private long id;
    @JsonKey
    private String name;
    @JsonKey
    private long age;
    @JsonKey
    private float height;
    @JsonKey
    private double weight;
    @JsonKey
    private boolean hasTail;
    @JsonKey
    private Date birthday;
    @JsonKey
    private Owner owner;

    public DogPrimaryKey() {

    }

    public DogPrimaryKey(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public DogPrimaryKey(String name) {
        this.name = name;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean isHasTail() {
        return hasTail;
    }

    public void setHasTail(boolean hasTail) {
        this.hasTail = hasTail;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}