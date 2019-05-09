package com.example.dell.mealdb;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dell.mealdb.Database.FavuoriteMeal;
import com.example.dell.mealdb.Database.MealModalView;
import com.example.dell.mealdb.Widget.MealAppWidget;
import com.like.LikeButton;
import com.like.OnLikeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
   LikeButton likeButton;
   ImageView imageView;
   MealModalView modalView;
   SharedPreferences sharedPreferences;
   TextView titiletv,instructions_tv,link,ing;
    String titile,instructions,Thumb,id,slink,Singrediants1,Singrediants2,Singrediants3,Singrediants4,Singrediants5,Singrediants6,Singrediants7,Singrediants8,Singrediants9,Singrediants10
            ,smeasure1,smeasure2,smeasure3,smeasure4,smeasure5,smeasure6,smeasure7,smeasure8,smeasure9,smeasure10,sname,sdesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        titiletv=findViewById(R.id.title);
        instructions_tv=findViewById(R.id.instructions);
        imageView=findViewById(R.id.listimage);
        likeButton=findViewById(R.id.likebutton);
        link=findViewById(R.id.link);
        ing=findViewById(R.id.ing);
        modalView= ViewModelProviders.of(this).get(MealModalView.class);
        id=getIntent().getStringExtra("key");
        titile=getIntent().getStringExtra("title");
        Thumb=getIntent().getStringExtra("image");
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(titile);
        titiletv.setText(titile);
        instructions_tv.setText(instructions);
        Glide.with(this).load(Thumb).into(imageView);
        new GetCategory(id).execute();

        addToWidgets();
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(Intent.ACTION_VIEW);

                intent.setPackage("com.google.android.youtube");
                intent.setData(Uri.parse(slink));

                startActivity(intent);
            }
        });

       favMeal();
        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                FavuoriteMeal favRecipie=new FavuoriteMeal(titile,id,sdesc,Thumb);
                Toast.makeText(ListActivity.this, "added to favuorites", Toast.LENGTH_SHORT).show();
                modalView.insertList(favRecipie);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                FavuoriteMeal favRecipie=new FavuoriteMeal(titile,sdesc,Thumb,id);
                Toast.makeText(ListActivity.this, "deleted from favuorites", Toast.LENGTH_SHORT).show();
                modalView.deletelist(favRecipie);
            }
        });

    }

    private void favMeal() {
        modalView.getListLiveData().observe( this, new Observer<List<FavuoriteMeal>>() {
            @Override
            public void onChanged(@Nullable List<FavuoriteMeal> favuorite) {
                for(int i=0;i<favuorite.size();i++){
                    String status=favuorite.get(i).getStrMeal();
                    if(status.equalsIgnoreCase(titile)){
                        likeButton.setLiked(true);
                    }
                }
            }
        });
    }

    public class GetCategory extends AsyncTask<Void,Void,String> {
        String STRING;

        public GetCategory(String id) {
            STRING=id;
        }

        @Override
        protected String doInBackground(Void... voids) {

            StringBuilder builder=new StringBuilder();
            try {
                Uri uri = Uri.parse("https://www.themealdb.com/api/json/v1/1/lookup.php?i="+STRING);
                URL url = new URL(uri.toString());
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream=connection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
                String line="";
                while (line!=null){
                    line=reader.readLine();
                    builder.append(line);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return builder.toString();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ArrayList<Category> list = new ArrayList<>();
            try {
                JSONObject object = new JSONObject(s);
                JSONArray array = object.getJSONArray("meals");
                for (int i=0;i<array.length();i++){
                    JSONObject jsonObject = array.getJSONObject(i);
                    sname = jsonObject.optString("strMeal");
                     sdesc = jsonObject.optString("strInstructions");
                     Thumb=jsonObject.optString("strMealThumb");
                    slink=jsonObject.optString("strYoutube");
                     Singrediants1=jsonObject.optString("strIngredient1");
                     smeasure1=jsonObject.optString("strMeasure1");
                    Singrediants2=jsonObject.optString("strIngredient2");
                    smeasure2=jsonObject.optString("strMeasure2");
                    Singrediants3=jsonObject.optString("strIngredient3");
                    smeasure3=jsonObject.optString("strMeasure3");
                    Singrediants4=jsonObject.optString("strIngredient4");
                    smeasure4=jsonObject.optString("strMeasure4");
                    Singrediants5=jsonObject.optString("strIngredient5");
                    smeasure5=jsonObject.optString("strMeasure5");
                    Singrediants6=jsonObject.optString("strIngredient6");
                    smeasure6=jsonObject.optString("strMeasure6");
                    Singrediants7=jsonObject.optString("strIngredient7");
                    smeasure7=jsonObject.optString("strMeasure7");
                    Singrediants8=jsonObject.optString("strIngredient8");
                    smeasure8=jsonObject.optString("strMeasure8");
                    Singrediants9=jsonObject.optString("strIngredient9");
                    smeasure9=jsonObject.optString("strMeasure9");
                    Singrediants10=jsonObject.optString("strIngredient10");
                    smeasure10=jsonObject.optString("strMeasure10");
                    ing.setText(Singrediants1+":"+smeasure1+"\n"+Singrediants2+":"+smeasure2+"\n"+Singrediants3+":"+smeasure3+"\n"+Singrediants4+":"+smeasure4+"\n"+Singrediants5+":"+smeasure5+"\n"+Singrediants6+":"+smeasure6+"\n"+
                            Singrediants7+":"+smeasure7+"\n"+Singrediants9+":"+smeasure8+"\n"+Singrediants9+":"+smeasure9+"\n"+Singrediants10+":"+smeasure10+"\n");

                    link.setText(slink);
                    titiletv.setText(sname);
                    instructions_tv.setText(sdesc);
                    Glide.with(ListActivity.this).load(Thumb).into(imageView);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    private void addToWidgets() {

        sharedPreferences = getSharedPreferences("mypref",0);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("title",titile);
        edit.putString("thumb",Thumb);

        edit.apply();

        Intent intent = new Intent(this,MealAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(
                new ComponentName(getApplication(),MealAppWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

