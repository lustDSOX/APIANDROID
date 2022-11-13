package com.example.apiwork;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Base64;
import java.util.List;

public class AnimalAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    List<Animal> animals;

    AnimalAdapter(Context context, List<Animal> animals) {
        ctx = context;
        this.animals = animals;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return animals.size();
    }

    @Override
    public Object getItem(int i) {
        return animals.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_layuot, parent, false);
        }

        Animal animal = getAnimal(i);

        ((TextView) view.findViewById(R.id.animal_name)).setText(animal.Name );
        ((TextView) view.findViewById(R.id.kind)).setText(animal.Kind +" (" +animal.Weight+")");
        ((TextView) view.findViewById(R.id.age)).setText(animal.Age + " age");
        if(animal.Image != "null"){
            ((ImageView) view.findViewById(R.id.image)).setImageBitmap(getImageBitmap(animal.Image));
        }
        else {
            ((ImageView) view.findViewById(R.id.image)).setImageResource(R.drawable.icon);
        }
        return view;
    }
    Animal getAnimal(int position) {
        return ((Animal) getItem(position));
    }

    private Bitmap getImageBitmap(String encodedImg) {
            byte[] bytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = Base64.getDecoder().decode(encodedImg);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

}
