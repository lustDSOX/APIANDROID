package com.example.apiwork;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AnimalAdapter adapter;
    private List<Animal> animalList = new ArrayList<>();

    ListView item_list;
    Intent add_activity;
    Intent item_activity;
    EditText txt_search;
    Spinner spinner;
    List<Animal> animalList_s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        Для того чтобы заполнить ListView  нам необходимо создать адптер. Адаптер используется для связи данных (массивы, базы данных)
        со списком (ListView)
        */
        ListView ivProducts = findViewById(R.id.item_list);//Находим лист в который будем класть наши объекты
        adapter = new AnimalAdapter(MainActivity.this, (ArrayList<Animal>) animalList); //Создаем объект нашего адаптера
        ivProducts.setAdapter(adapter); //Cвязывает подготовленный список с адаптером

        new GetProducts().execute(); //Подключение к нашей API в отдельном потоке

        String[] sort_name = { "По кол-ву лет", "По весу", "По имени", "По виду"};
        spinner= findViewById(R.id.spinner);
        ArrayAdapter<String> spinner_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sort_name);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinner_adapter);
        item_list = findViewById(R.id.item_list);
        txt_search = findViewById(R.id.txtsearch);
        add_activity = new Intent(MainActivity.this,AddActivity.class);
        item_activity = new Intent(MainActivity.this,ItemActivity.class);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                switch (position) {
                    case 0:
                        Collections.sort(animalList, new Comparator<Animal>() {
                            @Override
                            public int compare(Animal o1, Animal o2) {
                                return o1.GetAgeInt()-o2.GetAgeInt();
                            }
                        });
                        adapter = new AnimalAdapter(MainActivity.this, (ArrayList<Animal>) animalList);
                        item_list.setAdapter(adapter);

                        break;
                    case 1:
                        Collections.sort(animalList, new Comparator<Animal>() {
                            @Override
                            public int compare(Animal o1, Animal o2) {
                                return Float.compare(o1.GetWeightInt(), o2.GetWeightInt());
                            }
                        });
                        adapter = new AnimalAdapter(MainActivity.this, (ArrayList<Animal>) animalList);
                        item_list.setAdapter(adapter);
                        break;
                    case 2:
                        Collections.sort(animalList, new Comparator<Animal>() {
                            @Override
                            public int compare(Animal o1, Animal o2) {
                                return o1.nickname_animal.compareTo(o2.nickname_animal);
                            }
                        });
                        adapter = new AnimalAdapter(MainActivity.this, (ArrayList<Animal>) animalList);
                        item_list.setAdapter(adapter);
                        break;
                    case 3:
                        Collections.sort(animalList, new Comparator<Animal>() {
                            @Override
                            public int compare(Animal o1, Animal o2) {
                                return o1.kind.compareTo(o2.kind);
                            }
                        });
                        adapter = new AnimalAdapter(MainActivity.this, (ArrayList<Animal>) animalList);
                        item_list.setAdapter(adapter);
                        break;
                }
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }

        });

        item_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Animal item = (Animal) item_list.getItemAtPosition(i);
                item_activity.putExtra("name",item.nickname_animal);
                item_activity.putExtra("kind",item.kind);
                item_activity.putExtra("age",item.age);
                item_activity.putExtra("weight",item.weight_animal);
                item_activity.putExtra("id",item.id_animal);
                if(item.image != null) {
                    item_activity.putExtra("image",item.image);
                }
                startActivity(item_activity);
            }
        });

        txt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    private class GetProducts extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("https://ngknn.ru:5001/NGKNN/ермолаевас/api/animals");//Строка подключения к нашей API
                HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //вызываем нашу API

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                /*
                BufferedReader успрощает чтение текста из потока символов
                InputStreamReader преводит поток байтов в поток символов
                connection.getInputStream() получает поток байтов
                */
                StringBuilder result = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    result.append(line);//кладет строковое значение в потоке
                }
                return result.toString();

            } catch (Exception exception) {
                return null;
            }
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try
            {
                JSONArray tempArray = new JSONArray(s);//преоброзование строки в json массив
                for (int i = 0;i<tempArray.length();i++)
                {

                    JSONObject productJson = tempArray.getJSONObject(i);//Преобразование json объекта в нашу структуру
                    Animal tempProduct = new Animal(
                            productJson.getString("Name"),
                            productJson.getString("Kind"),
                            productJson.getString("Age"),
                            productJson.getString("Weight"),
                            productJson.getInt("Id"),
                            productJson.getString("Image")
                    );
                    animalList.add(tempProduct);
                    adapter.notifyDataSetInvalidated();
                }
            } catch (Exception ignored) {}
        }
    }
    public void AddAnimal(View v){
        startActivity(add_activity);
    }
    public void UpdateList(){
        animalList.clear();
        new GetProducts().execute();
    }
}