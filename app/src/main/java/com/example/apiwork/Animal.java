package com.example.apiwork;

import android.graphics.Bitmap;

public class Animal {
    String name,kind,age,weight,id,image;

    public Animal(String name,String kind,String age,String weight, String id,String image){
        this.name = name;
        this.kind = kind;
        this.age = age;
        this.weight = weight;
        this.id = id;
    }

    public int GetAgeInt(){
        return Integer.parseInt(age);
    }
    public float GetWeightInt(){
        return Float.parseFloat(weight);
    }

    public String GetName(){return name;}
    public String GetKind(){return kind;}
    public String GetAge(){return age;}
    public String GetWeight(){return weight;}
    public String GetImage(){return image;}
}