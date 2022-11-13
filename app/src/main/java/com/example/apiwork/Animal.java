package com.example.apiwork;

import android.os.Parcel;
import android.os.Parcelable;


public class Animal implements Parcelable {
    int Id;
    String Name,Kind,Age,Weight,Image;

    public Animal(String Name,String Kind,String Age,String Weight, int Id,String Image){
        this.Name = Name;
        this.Kind = Kind;
        this.Age = Age;
        this.Weight = Weight;
        this.Id = Id;
        this.Image = Image;
    }

    protected Animal(Parcel in) {
        Id = in.readInt();
        Weight = in.readString();
        Name = in.readString();
        Kind = in.readString();
        Age = in.readString();
        Image = in.readString();
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
        dest.writeInt(Id);
        dest.writeString(Weight);
        dest.writeString(Name);
        dest.writeString(Kind);
        dest.writeString(Age);
        dest.writeString(Image);
    }
    public int GetAgeInt(){
        return Integer.parseInt(Age);
    }
    public float GetWeightInt(){
        return Float.parseFloat(Weight);
    }

    public String GetName(){return Name;}
    public String GetKind(){return Kind;}
    public String GetAge(){return Age;}
    public String GetWeight(){return Weight;}
    public String GetImage(){return Image;}
    public int Getid_animal(){return Id;}

    public void setImage(String imag) {Image = imag;}
    public void setName(String Image) {Name = Image;}
    public void setKind(String Image) {Kind = Image;}
    public void setAge(String Image) {Age = Image;}
    public void setWeight(String Image) {Weight = Image;}
    public void setid_animal(int Image) {Id = Image;}
}