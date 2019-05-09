package com.example.dell.mealdb;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dell.mealdb.Database.FavouriteAdapter;
import com.example.dell.mealdb.Database.FavuoriteMeal;
import com.example.dell.mealdb.Database.MealModalView;
import com.example.dell.mealdb.JsonData.Gsondata;
import com.example.dell.mealdb.JsonData.Meal;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mlsdev.animatedrv.AnimatedRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RequestQueue queue;
    private static final String TAG = "MainActivity";

    private AdView mAdView;
    MyAdapter adapter;
    ArrayList<Meal> meals;
    Context context=this;
    SharedPreferences sharedPreferences;
    MealModalView modalView;
    String CATEGORY,COUNTRY;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String values;
    public static String url="https://www.themealdb.com/api/json/v1/1/randomselection.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        modalView= ViewModelProviders.of(this).get(MealModalView.class);
        recyclerView=findViewById(R.id.recyclerview);
        firebaseAuth=FirebaseAuth.getInstance();
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
         firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
        queue= Volley.newRequestQueue(this);
        parseData(url);
        sharedPreferences=getPreferences(MODE_PRIVATE);
        if(savedInstanceState !=null){
            meals=savedInstanceState.getParcelableArrayList("meals");
            if(values.equalsIgnoreCase("favmeals")){
                int conf = getResources().getConfiguration().orientation;
                if (conf == Configuration.ORIENTATION_LANDSCAPE) {
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
                } else {
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                }
                recyclerView.setAdapter(new MyAdapter(MainActivity.this, meals));
            }
            else{
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));

            }
        }

    }
        else{
            finish();
           Intent intent=new Intent(this,LoginActivity.class);
           startActivity(intent);

        }
    }


    private void RunAnimation(AnimatedRecyclerView recyclerView) {
        recyclerView = new AnimatedRecyclerView.Builder(context)
                .orientation(GridLayoutManager.VERTICAL)
                .layoutManagerType(AnimatedRecyclerView.LayoutManagerType.LINEAR)
                .animation(R.anim.layout_animation_from_bottom)
                .animationDuration(600)
                .reverse(false)
                .build();
        adapter.notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();

    }



    private void Load() {
        modalView.getListLiveData().observe(this,
                new Observer<List<FavuoriteMeal>>() {
            @Override
            public void onChanged(@Nullable List<FavuoriteMeal> favuoriteMeal) {
                FavouriteAdapter favouriteAdapter=new FavouriteAdapter(MainActivity.this,favuoriteMeal);
               /* favouriteAdapter.notifyDataSetChanged();
                recyclerView.scheduleLayoutAnimation();*/
                recyclerView.setAdapter(favouriteAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
            }
        });
    }

    public void parseData(String url) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder builder=new GsonBuilder();
                Gson gson=builder.create();
                Gsondata data = gson.fromJson(response, Gsondata.class);
                meals= (ArrayList<Meal>) data.getMeals();
                adapter=new MyAdapter(MainActivity.this,meals);
            /*    adapter.notifyDataSetChanged();
                recyclerView.scheduleLayoutAnimation();*/
                recyclerView.setAdapter(adapter);
                int conf = getResources().getConfiguration().orientation;
                if (conf == Configuration.ORIENTATION_LANDSCAPE) {
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
                } else {
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });
        queue.add(request);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("meals", meals);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.Seafood:
                CATEGORY="Seafood";
                url="https://www.themealdb.com/api/json/v1/1/filter.php?c="+CATEGORY;
                parseData(url);
                break;
            case R.id.Beef:
                CATEGORY="Beef";
                url="https://www.themealdb.com/api/json/v1/1/filter.php?c="+CATEGORY;
                parseData(url);
                break;
            case R.id.Lamb:
                CATEGORY="Lamb";
                url="https://www.themealdb.com/api/json/v1/1/filter.php?c="+CATEGORY;
                parseData(url);
                break;
            case R.id.Desert:
                CATEGORY="Desert";
                url="https://www.themealdb.com/api/json/v1/1/filter.php?c="+CATEGORY;
                parseData(url);
                break;
            case R.id.Starter:
                CATEGORY="Starter";
                url="https://www.themealdb.com/api/json/v1/1/filter.php?c="+CATEGORY;
                parseData(url);
                break;
            case R.id.Chicken:
                CATEGORY="Chicken";
                url="https://www.themealdb.com/api/json/v1/1/filter.php?c="+CATEGORY;
                parseData(url);
                break;
            case R.id.Pasta:
                CATEGORY="Pasta";
                url="https://www.themealdb.com/api/json/v1/1/filter.php?c="+CATEGORY;
                parseData(url);
                break;
            case R.id.Chinese:
                COUNTRY="Chinese";
                url="https://www.themealdb.com/api/json/v1/1/filter.php?a="+COUNTRY;
                parseData(url);
                break;
            case R.id.French:
                COUNTRY="French";
                url="https://www.themealdb.com/api/json/v1/1/filter.php?a="+COUNTRY;
                parseData(url);
                break;
            case R.id.Canadian:
                COUNTRY="Canadian";
                url="https://www.themealdb.com/api/json/v1/1/filter.php?a="+COUNTRY;
                parseData(url);
                break;
            case R.id.Egyptian:
                COUNTRY="Egyptian";
                url="https://www.themealdb.com/api/json/v1/1/filter.php?a="+COUNTRY;
                parseData(url);
                break;
            case R.id.Greek:
                COUNTRY="Greek";
                url="https://www.themealdb.com/api/json/v1/1/filter.php?a="+COUNTRY;
                parseData(url);
                break;
            case R.id.British:
                COUNTRY="British";
                url="https://www.themealdb.com/api/json/v1/1/filter.php?a="+COUNTRY;
                parseData(url);
                break;
            case R.id.American:
                COUNTRY="American";
                url="https://www.themealdb.com/api/json/v1/1/filter.php?a="+COUNTRY;
                parseData(url);
                break;


            case R.id.signout:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.app_name);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage("Do you want to signout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                firebaseAuth.signOut();
                                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            case R.id.favourite:
                Load();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
