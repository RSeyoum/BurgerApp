package com.example.burgerapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    companion object {

        // All the keys we are going to show in our detail page
        val menu_image_key = "menu_image_key"
        val menu_name_key = "menu_name_key"
        val menu_description_key = "menu_description_key"

    }

    lateinit var detail_menu_img: ImageView
    lateinit var detail_name_text: TextView
    lateinit var detail_description_text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Finding each id
        detail_menu_img = findViewById(R.id.da_menu_img)
        detail_name_text = findViewById(R.id.da_name_text)
        detail_description_text = findViewById(R.id.da_description_text)

        // Find each of the menu item image
        val menu_img = intent.getStringExtra(menu_image_key)
        val drawableResourceId = resources.getIdentifier(menu_img, "drawable", this.packageName)


        // Show corresponding menu detail
        detail_menu_img.setImageResource(drawableResourceId)
        detail_name_text.text = intent.getStringExtra(menu_name_key)
        detail_description_text.text = intent.getStringExtra(menu_description_key)
    }
}