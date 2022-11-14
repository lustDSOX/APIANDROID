package com.example.apiwork;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class AnimalAdapter extends BaseAdapter implements Filterable {

    Context ctx;
    LayoutInflater lInflater;
    List<Animal> animals;
    List<Animal> animals_s;

    AnimalAdapter(Context context, List<Animal> animals) {
        ctx = context;
        this.animals = animals;
        animals_s = animals;
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

        ((TextView) view.findViewById(R.id.animal_name)).setText(animal.nickname_animal );
        ((TextView) view.findViewById(R.id.kind)).setText(animal.kind +" (" +animal.weight_animal+")");
        ((TextView) view.findViewById(R.id.age)).setText(animal.age + " age");
        if(animal.image != "null"){
            ((ImageView) view.findViewById(R.id.image)).setImageBitmap(getImageBitmap(animal.image));
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

    @Override
    public Filter getFilter() {
        return new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                animals = (ArrayList<Animal>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<Animal> FilteredArrList = new ArrayList<>();

                if (animals_s == null) {
                    animals_s = new ArrayList<>(animals);
                }

                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = animals_s.size();
                    results.values = animals_s;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < animals_s.size(); i++) {
                        String data = animals_s.get(i).GetName();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new Animal(animals_s.get(i).GetName(),
                                    animals_s.get(i).GetKind(),
                                    animals_s.get(i).GetAge(),
                                    animals_s.get(i).GetWeight(),
                                    animals_s.get(i).Getid_animal(),
                                    animals_s.get(i).GetImage()));
                        }
                    }
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
    }
}
