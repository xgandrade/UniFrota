package br.com.unifrota;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.unifrota.adapters.RecyclerCarroAdapter;
import br.com.unifrota.pojo.Carro;
import br.com.unifrota.utils.HttpService;

public class MainActivity extends AppCompatActivity {

    ArrayList<Carro> carros = new ArrayList<>();
    SQLiteDatabase db;
    FloatingActionButton fabNovoCarro;
    SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabNovoCarro = findViewById(R.id.fab_addCarro);
        swipe = findViewById(R.id.swipeContainer);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                iniciaRecyclerView();
            }
        });

        swipe.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );

        iniciaRecyclerView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        iniciaRecyclerView();
    }

    private void iniciaRecyclerView(){
        iniciaListaCarros();
        RecyclerView rv = findViewById(R.id.lista_carros);
        RecyclerCarroAdapter adaptador = new RecyclerCarroAdapter(this, carros);
        rv.setAdapter(adaptador);
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));
        rv.setLayoutManager(new LinearLayoutManager(this));
        swipe.setRefreshing(false);
    }

    public void fabNovoCarroClick(View v){
       Intent telaCadastro = new Intent(MainActivity.this, EditCarro.class);
       startActivity(telaCadastro);
    }

    private void iniciaListaCarros(){
        try{
            StringBuilder str = new HttpService("-1").execute().get();

            Type tipo = new TypeToken<List<Carro>>(){
            }.getType();

            Gson g = new GsonBuilder().create();

            carros = g.fromJson(String.valueOf(str), tipo);

        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }
    }
}
