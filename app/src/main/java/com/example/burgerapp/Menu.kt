package com.example.burgerapp

class Menu (

        var type: String,
        var name: String,
        var calorie: String,
        var imageId: String,
        var description: String,

        // To save the selected food in the list, add a Boolean property in the food model class
        var selected_item: Boolean = false
){

}