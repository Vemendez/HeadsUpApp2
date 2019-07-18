package com.example.headsupapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.headsupapp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CardViewAdapter adapter;
    private List<Category> categoriesList;
    private Map<String, List<String>> stringsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        categoriesList = new ArrayList<>();
        stringsList = new HashMap<String, List<String>>() {
            @Override
            public int size() {
                return stringsList.size();
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean containsKey(Object key) {
                return false;
            }

            @Override
            public boolean containsValue(Object value) {
                return false;
            }

            @Override
            public List<String> get(Object key) {
                return super.get(key);
            }

            @Override
            public List<String> put(String key, List<String> value) {
                return super.put(key, value);
            }

            @Override
            public List<String> remove(Object key) {
                return super.remove(key);
            }

            @Override
            public void putAll(@NonNull Map<? extends String, ? extends List<String>> m) {

            }

            @Override
            public void clear() {

            }

            @NonNull
            @Override
            public Set<String> keySet() {
                return super.keySet();
            }

            @NonNull
            @Override
            public Collection<List<String>> values() {
                return super.values();
            }

            @NonNull
            @Override
            public Set<Entry<String, List<String>>> entrySet() {
                return null;
            }
        };
        adapter = new CardViewAdapter(this, categoriesList, stringsList);
        prepareCategories();
        try {
            prepareStrings();
        } catch (IOException e) {
            e.printStackTrace();
        }

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void prepareCategories() {
        int[] covers = new int[]{
                R.drawable.album1,
                R.drawable.album2,
                R.drawable.album3,
                R.drawable.album4,
                R.drawable.album5,
                R.drawable.album7,
                R.drawable.album8
        };

        categoriesList.add(new Category("Movies", 15, covers[0]));
        categoriesList.add(new Category("Songs", 15, covers[1]));
        categoriesList.add(new Category("Cartoon Network", 15, covers[2]));
        categoriesList.add(new Category("Act it out", 15, covers[3]));
        categoriesList.add(new Category("Game of Thrones", 15, covers[4]));
        categoriesList.add(new Category("Food Items", 15, covers[5]));
        categoriesList.add(new Category("Animals", 15, covers[6]));

        adapter.notifyDataSetChanged();

    }

    private void prepareStrings() throws IOException {
        ArrayList<String> moviesString = new ArrayList<>(35);
        ArrayList<String> songsString = new ArrayList<>(24);
        ArrayList<String> cartoonString = new ArrayList<>(15);
        ArrayList<String> actItOutString = new ArrayList<>(15);
        ArrayList<String> gotString = new ArrayList<>(15);
        ArrayList<String> foodString = new ArrayList<>(15);
        ArrayList<String> animalString = new ArrayList<>(15);

        int ch;
        String str = "";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("movies.txt")));
        while((ch = bufferedReader.read()) != -1){
            str += (char)ch;
        }

        String[] strArray = str.split(",");
        for (int i = 0; i < strArray.length; i++){
            moviesString.add(strArray[i]);
        }
        stringsList.put(categoriesList.get(0).getName(), moviesString);

        str = "";
        bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("songs.txt")));
        while((ch = bufferedReader.read()) != -1){
            str += (char)ch;
        }

        strArray = str.split(",");

        for (int i = 0; i < strArray.length; i++){
            songsString.add(strArray[i]);
        }
        stringsList.put(categoriesList.get(1).getName(), songsString);

        str = "";
        bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("cartoons.txt")));
        while((ch = bufferedReader.read()) != -1){
            str += (char)ch;
        }
        strArray = str.split(",");

        for (int i = 0; i < strArray.length; i++){
            cartoonString.add(strArray[i]);
        }
        stringsList.put(categoriesList.get(2).getName(), cartoonString);

        str = "";
        bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("act it out.txt")));
        while((ch = bufferedReader.read()) != -1){
            str += (char)ch;
        }

        strArray = str.split(",");

        for (int i = 0; i < strArray.length; i++){
            actItOutString.add(strArray[i]);
        }
        stringsList.put(categoriesList.get(3).getName(), actItOutString);

        str = "";
        bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("game of thrones.txt")));
        while((ch = bufferedReader.read()) != -1){
            str += (char)ch;
        }

        strArray = str.split(",");

        for (int i = 0; i < strArray.length; i++){
            gotString.add(strArray[i]);
        }
        stringsList.put(categoriesList.get(4).getName(), gotString);

//        str = "";
//        bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("food items.txt")));
//        while((ch = bufferedReader.read()) != -1){
//            str += (char)ch;
//        }
//        strArray = str.split(",");
//
//        for (int i = 0; i < strArray.length; i++){
//            foodString.add(strArray[i]);
//        }
//        stringsList.put(categoriesList.get(5).getName(), foodString);

        str = "";
        bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("food items.txt")));
        while((ch = bufferedReader.read()) != -1){
            str += (char)ch;
        }
        strArray = str.split(",");

        for (int i = 0; i < strArray.length; i++){
            foodString.add(strArray[i]);
        }
        stringsList.put(categoriesList.get(5).getName(), foodString);

        str = "";
        bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("animals.txt")));
        while((ch = bufferedReader.read()) != -1){
            str += (char)ch;
        }
        strArray = str.split(",");

        for (int i = 0; i < strArray.length; i++){
            animalString.add(strArray[i]);
        }
        stringsList.put(categoriesList.get(6).getName(), animalString);

        adapter.notifyDataSetChanged();
    }
}