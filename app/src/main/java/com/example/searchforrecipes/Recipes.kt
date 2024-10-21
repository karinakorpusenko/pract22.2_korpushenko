package com.example.searchforrecipes

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.searchforrecipes.ui.theme.Recipe
import com.example.searchforrecipes.ui.theme.RecipeAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

import org.json.JSONArray


class Recipes : AppCompatActivity() {
    lateinit var query: EditText
    lateinit var description: TextView
    lateinit var ingredients: TextView
    lateinit var recipe: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes)


        val searchButton: Button = findViewById(R.id.search_button)
        searchButton.setOnClickListener { search_Click(it) }
    }

    var recipesList = mutableListOf<Recipe>()
    @SuppressLint("SuspiciousIndentation")
    private fun search_Click(view: View) {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view) // Ensure you have a RecyclerView in your layout

        query=findViewById(R.id.query)
        description=findViewById(R.id.description)
        ingredients=findViewById(R.id.ingredients)
        recipe=findViewById(R.id.recipe)
        if (query.text.toString().isNotEmpty()) {

            var apiKey = "P3ZeruujgVr45uxkbSxRyw==ICICp46iEWu1xS7p"
        var url = "https://api.api-ninjas.com/v1/recipe?query="+ query.text.toString() + "&X-Api-Key=" + apiKey
        val queue = Volley.newRequestQueue(this)
            val stringRequest = StringRequest(
                com.android.volley.Request.Method.GET,
                url,
            { response ->
                val jsonArray = JSONArray(response) // Parse the response as a JSONArray
                if (jsonArray.length() > 0) {
                    val obj = jsonArray.getJSONObject(0) // Get the first recipe object

                    val title = obj.getString("title") // Correctly get the title
                    recipe.text = title

                    val instructions = obj.getString("instructions") // Get instructions directly
                    description.text = "Inctructions: "+instructions

                    val ingredientsList = obj.getString("ingredients") // Get ingredients directly
                    ingredients.text = "Ingredients: "+ingredientsList

                    recipesList.add(Recipe(title,"Ingredients: "+ ingredientsList,"Inctructions: "+ instructions))




                }else {
                Snackbar.make(view, "No recipes found", Snackbar.LENGTH_LONG).show()}},
                {
                        error ->
                    Log.e("RecipeDetailsError", "Error fetching recipe details: ${error.message}")
                    Snackbar.make(view, "Error fetching recipe details", Snackbar.LENGTH_LONG).show()
                }
                )
                queue.add(stringRequest)

    }else {
            Snackbar.make(view,"Введите название блюда", Snackbar.LENGTH_LONG).show()
        }
}
        fun myRecipes(view: View){
            val sharedPreferences = getSharedPreferences("MyRecipes", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            val gson = Gson() // Make sure to include Gson dependency in your build.gradle
            val json = gson.toJson(recipesList) // Convert the list to JSON
            editor.putString("recipes", json)
            editor.apply()
            val intent = Intent(this,myRecipes()::class.java)

            startActivity(intent)
        }

}

