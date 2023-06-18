package com.example.burgerapp


import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(var menuData: Array<Menu>) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>(){

    lateinit var clickLambda: (Menu) -> Unit


    class RecyclerViewHolder(val viewItem: View) : RecyclerView.ViewHolder(viewItem) {

        fun bind(menu: Menu, clickLamda: (Menu) -> Unit, allitems:Array<Menu>) {

            /*

            To set a drawable to an image, use imgView.setImageResource(imageId). To find the
            resource id (Int) of a drawable file in layout/drawable, use the following function
            in an Activity

             */
            if (menu.imageId == "big_mac") {
                viewItem.findViewById<ImageView>(R.id.mv_img).setImageResource(R.drawable.big_mac)
            }
            if (menu.imageId == "quarter_pounder_with_cheese") {
                viewItem.findViewById<ImageView>(R.id.mv_img).setImageResource(R.drawable.quarter_pounder_with_cheese)
            }
            if (menu.imageId == "double_quarter_pounder_with_cheese") {
                viewItem.findViewById<ImageView>(R.id.mv_img).setImageResource(R.drawable.double_quarter_pounder_with_cheese)
            }
            if (menu.imageId == "quarter_pounder_with_cheese_deluxe") {
                viewItem.findViewById<ImageView>(R.id.mv_img).setImageResource(R.drawable.quarter_pounder_with_cheese_deluxe)
            }
            if (menu.imageId == "mcdouble") {
                viewItem.findViewById<ImageView>(R.id.mv_img).setImageResource(R.drawable.mcdouble)
            }
            if (menu.imageId == "quarter_pounder_with_cheese_bacon") {
                viewItem.findViewById<ImageView>(R.id.mv_img).setImageResource(R.drawable.quarter_pounder_with_cheese_bacon)
            }
            if (menu.imageId == "coca_cola") {
                viewItem.findViewById<ImageView>(R.id.mv_img).setImageResource(R.drawable.coca_cola)
            }
            if (menu.imageId == "small_sprite") {
                viewItem.findViewById<ImageView>(R.id.mv_img).setImageResource(R.drawable.small_sprite)
            }
            if (menu.imageId == "fanta") {
                viewItem.findViewById<ImageView>(R.id.mv_img).setImageResource(R.drawable.fanta)
            }
            if (menu.imageId == "dr_pepper") {
                viewItem.findViewById<ImageView>(R.id.mv_img).setImageResource(R.drawable.dr_pepper)
            }
            if (menu.imageId == "small_world_famous_fries") {
                viewItem.findViewById<ImageView>(R.id.mv_img).setImageResource(R.drawable.small_world_famous_fries)
            }


            // Find the id of each of the checkboxes
            val item_checkbox = viewItem.findViewById<CheckBox>(R.id.mv_item_checkbox)


            item_checkbox.isChecked = menu.selected_item

            item_checkbox.setOnClickListener{

                Log.d("test", allitems.size.toString())

                // The current menu item is selected thus, it returns true
                // Therefore, our selected menu item is now checked
                menu.selected_item = item_checkbox.isChecked

            }

            // We are only showing the image, name and calorie of each item on the menu
            viewItem.findViewById<TextView>(R.id.mv_name_text).text = menu.name
            viewItem.findViewById<TextView>(R.id.mv_calorie).text = menu.calorie


            viewItem.setOnClickListener{
                clickLamda(menu)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(

            // Layout of each viewItem should be our menu_view.xml
            R.layout.menu_view,
            parent,
            false
        )
        val rholder = RecyclerViewHolder(viewItem)
        return rholder
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(menuData[position], clickLambda, menuData)
    }

    override fun getItemCount(): Int {

        return menuData.size
    }


}
