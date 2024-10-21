package com.example.searchforrecipes.ui.theme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.searchforrecipes.R
import com.example.searchforrecipes.myRecipes

class RecipeAdapter(private val recipes: MutableList<Recipe>) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

   class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.recipe_title)
        val ingredients: TextView = view.findViewById(R.id.recipe_ingredients)
        val instructions: TextView = view.findViewById(R.id.recipe_instructions)
        val deleteButton: Button = itemView.findViewById(R.id.recipe_delete)
    val izmbutton:Button=itemView.findViewById(R.id.recipe_izm)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.title.text = recipe.title
        holder.ingredients.text = recipe.ingredients
        holder.instructions.text = recipe.instructions

        holder.deleteButton.setOnClickListener {
            removeItem(position)
        }

    }

    override fun getItemCount() = recipes.size


    fun removeItem(position: Int) {
        recipes.removeAt(position)
        notifyDataSetChanged()
    }

}