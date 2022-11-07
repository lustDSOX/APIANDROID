package com.example.apiwork;

import android.graphics.Bitmap;

public class Animal {
    String name,kind,age,weight,id;
    Bitmap image;

    public Animal(String name,String kind,String age,String weight, String id,Bitmap image){
        this.name = name;
        this.kind = kind;
        this.age = age;
        this.weight = weight;
        this.id = id;
        this.image =image;
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
    public Bitmap GetImage(){return image;}
}