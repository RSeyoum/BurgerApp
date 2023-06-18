package com.example.burgerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class MainActivity : AppCompatActivity() {

    var menuArray = ArrayList<Menu>()
    var calculate_array = ArrayList<Menu>()

    lateinit var menu_recycler: RecyclerView
    lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var viewAdapter: RecyclerViewAdapter
    lateinit var burger_checkbox: CheckBox
    lateinit var drink_checkbox: CheckBox
    lateinit var snack_checkbox: CheckBox
    lateinit var calculate_button: Button
    lateinit var sortby_spinner: Spinner

    val menuClickLambda = { menu: Menu ->

        val intent = Intent(this, DetailActivity::class.java)

        intent.putExtra(DetailActivity.menu_image_key, menu.imageId)
        intent.putExtra(DetailActivity.menu_name_key, menu.name)
        intent.putExtra(DetailActivity.menu_description_key, menu.description)

        startActivity(intent)
    }


    fun loadData() {

        val dataString =
            resources.openRawResource(R.raw.mcdonalds).bufferedReader()
                .use { it.readText() } // read the entire file as a string
        var lines = dataString.trim().split("\n") // split each line
        lines = lines.subList(1, lines.size) // get rid of the header line

        // Add to the stock Array.
        lines.forEach { line:String ->

            // Add your code here
            // Process each line, separate by ";" and read different values and create Model objects
            // Refer to the code reading CSV file
            val cells = line.split(";")
            val menu = Menu(
                cells[0],
                cells[1],
                cells[2],
                cells[3],
                cells[4]
            )

            menuArray.add(menu)
        }
    }

    private fun setupSpinner() {

        sortby_spinner = findViewById<Spinner>(R.id.sortby_spinner)

        val menuSpinner = arrayOf("Type", "Name", "Calorie")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, menuSpinner)
        sortby_spinner.adapter = arrayAdapter

        sortby_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {

                /*
                    Each time an item is selected from the spinner we update our list to sort all
                    menu items in ascending order
                 */

                updateList()
            }

            // However, if nothing is selected we do nothing
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }
    }

    /*
        Update menu list based on Spinner and Checkbox
     */

    fun updateList() {

        // This variable if going to hold our new array list
        var updated_array = ArrayList<Menu>()


        // Find the corresponding id for each checkbox
        burger_checkbox = findViewById<CheckBox>(R.id.burger_checkbox)
        drink_checkbox = findViewById<CheckBox>(R.id.drink_checkbox)
        snack_checkbox = findViewById<CheckBox>(R.id.snack_checkbox)


        /*
            If any of the checkboxes are checked, we go through a for loop for each menu item within
            our menuArray. For each menu item, we check the type. If the type matches, the corresponding
            menu item is then added to the updated array list.
         */
        if (burger_checkbox.isChecked) {
            menuArray.forEach{ menu:Menu ->
                if (menu.type == "Burger") {
                    updated_array.add(menu)
                }
            }
        }
        if (drink_checkbox.isChecked) {
            menuArray.forEach{ menu:Menu ->
                if (menu.type == "Drink") {
                    updated_array.add(menu)
                }
            }
        }
        if (snack_checkbox.isChecked) {
            menuArray.forEach{ menu:Menu ->
                if (menu.type == "Snack") {
                    updated_array.add(menu)
                }
            }
        }


        // spinner.selectedItemPosition is used to get the currently selected item position in the Spinner
        var sort = sortby_spinner.selectedItemPosition


        /*
            The second portion deals with sorting the menu items in ascending order

            We first find the selected item position in the Spinner, then using that information we
            are able to identity in what order we are going to arrange the new array list. For example,
            the three different options include; type, name and calorie. In order to sort the array
            list, we used the arrayList.sortBy{} function

         */

        if (sort == 0) {
            updated_array.sortBy {
                it.type
            }
        }
        else if (sort == 1) {
            updated_array.sortBy {
                it.name
            }
        }
        else  if (sort == 2) {
            updated_array.sortBy {
                it.calorie
            }
        }

        /*
           Copy all the contents of the updated array to the menuArray so that we have an updated
           version of the menu item arraylist
         */


        calculate_array = updated_array

        // Assign it to the adapter and update the RecyclerView
        viewAdapter.menuData = updated_array.toTypedArray()
        viewAdapter.notifyDataSetChanged()
    }

    /*
        Calculate total calorie of selected items
     */
    fun calculate_total_calorie() {

        // Variable that will be holding the total calorie
        var total_calorie = 0


        // Iterate through the menu array
        calculate_array.forEach { menu: Menu ->


            // If the menu item is selected, we then add the value to our total calorie variable
            if (menu.selected_item) {
                total_calorie += menu.calorie.toInt()
            }
        }

        var text = "Total Calorie: " + total_calorie
        val duration = Toast.LENGTH_SHORT

        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load all the data within our txt file
        loadData()

        // Setting up the Spinner
        setupSpinner()

        // Find the id of each checkbox
        burger_checkbox = findViewById<CheckBox>(R.id.burger_checkbox)
        drink_checkbox = findViewById<CheckBox>(R.id.drink_checkbox)
        snack_checkbox = findViewById<CheckBox>(R.id.snack_checkbox)


        // If any of the checkboxes are clicked we call our updateList function
        burger_checkbox.setOnClickListener {
            updateList()
        }
        drink_checkbox.setOnClickListener {
            updateList()
        }
        snack_checkbox.setOnClickListener {
            updateList()
        }

        // Find the id of calculate button
        calculate_button = findViewById(R.id.calculate_button)

        // When the calculate button is clicked return the function that calculate the sum
        calculate_button.setOnClickListener {
            calculate_total_calorie()
        }

        // Check size of array
        Log.d("size", menuArray.size.toString())

        menu_recycler = findViewById(R.id.burger_recycler)

        viewManager = LinearLayoutManager(this)
        menu_recycler.layoutManager = viewManager

        viewAdapter = RecyclerViewAdapter(menuArray.toTypedArray())
        menu_recycler.adapter = viewAdapter

        viewAdapter.clickLambda = menuClickLambda

    }

}



