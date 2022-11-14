package com.example.apiwork;

import android.os.Parcel;
import android.os.Parcelable;


public class Animal implements Parcelable {
    int id_animal;
    String nickname_animal,kind,age,weight_animal,image;

    public Animal(String nickname_animal,String kind,String age,String weight_animal, int id_animal,String image){
        this.nickname_animal = nickname_animal;
        this.kind = kind;
        this.age = age;
        this.weight_animal = weight_animal;
        this.id_animal = id_animal;
        this.image = image;
    }

    protected Animal(Parcel in) {
        id_animal = in.readInt();
        weight_animal = in.readString();
        nickname_animal = in.readString();
        kind = in.readString();
        age = in.readString();
        image = in.readString();
    }

    public static final Parcelable.Creator<Animal> CREATOR = new Parcelable.Creator<Animal>() {
        @Override
        public Animal createFromParcel(Parcel source) {
            return new Animal(source);
        }

        @Override
        public Animal[] newArray(int size) {
            return new Animal[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_animal);
        dest.writeString(weight_animal);
        dest.writeString(nickname_animal);
        dest.writeString(kind);
        dest.writeString(age);
        dest.writeString(image);
    }
    public int GetAgeInt(){
        return Integer.parseInt(age);
    }
    public float GetWeightInt(){
        return Float.parseFloat(weight_animal);
    }

    public String GetName(){return nickname_animal;}
    public String GetKind(){return kind;}
    public String GetAge(){return age;}
    public String GetWeight(){return weight_animal;}
    public String GetImage(){return image;}
    public int Getid_animal(){return id_animal;}

    public void setImage(String imag) {image = imag;}
    public void setName(String image) {nickname_animal = image;}
    public void setKind(String image) {kind = image;}
    public void setAge(String image) {age = image;}
    public void setWeight(String image) {weight_animal = image;}
    public void setid_animal(int image) {id_animal = image;}
}