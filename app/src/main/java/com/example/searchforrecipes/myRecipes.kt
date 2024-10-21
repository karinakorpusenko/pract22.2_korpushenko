package com.example.searchforrecipes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.searchforrecipes.ui.theme.Recipe
import com.example.searchforrecipes.ui.theme.RecipeAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class myRecipes : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var recipes: MutableList<Recipe>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myrecipes)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Получаем список рецептов из Intent
        val sharedPreferences = getSharedPreferences("MyRecipes", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("recipes", null)
        val type = object : TypeToken<List<Recipe>>() {}.type
        val savedRecipes: List<Recipe>? = gson.fromJson(json, type)

        // Use saved recipes or the intent extras
        recipes = savedRecipes?.toMutableList() ?: intent.getParcelableArrayListExtra<Recipe>("recipes")?.toMutableList() ?: mutableListOf()
        recipeAdapter = RecipeAdapter(recipes)
        recyclerView.adapter = recipeAdapter
        saveRecipesToPreferences()


    }

    private fun saveRecipesToPreferences() {
        val sharedPreferences = getSharedPreferences("MyRecipes", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(recipes)
        editor.putString("recipes", json)
        editor.apply()
    }
    }




