package com.example.foodorder

import android.content.ContentValues
import android.database.Cursor
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class PlaceOrderActivity : AppCompatActivity() {

    private lateinit var dbHelper: FoodDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_order)

        dbHelper = FoodDbHelper(this)

        val editFoodName = findViewById<AutoCompleteTextView>(R.id.editFoodName)
        val editDescription = findViewById<AutoCompleteTextView>(R.id.editDescription)
        val editQuantity = findViewById<EditText>(R.id.editQuantity)
        val buttonPlaceOrder = findViewById<Button>(R.id.buttonPlaceOrder)

        // Populate dropdown for food name and description
        val db = dbHelper.readableDatabase
        val foodNameList = mutableListOf<String>()
        val descriptionList = mutableListOf<String>()

        val cursor: Cursor = db.rawQuery("SELECT name, description FROM Food", null)
        if (cursor.moveToFirst()) {
            do {
                foodNameList.add(cursor.getString(0))
                descriptionList.add(cursor.getString(1))
            } while (cursor.moveToNext())
        }
        cursor.close()

        val foodAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, foodNameList)
        val descAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, descriptionList)

        editFoodName.setAdapter(foodAdapter)
        editDescription.setAdapter(descAdapter)

        editFoodName.threshold = 1
        editDescription.threshold = 1

        buttonPlaceOrder.setOnClickListener {
            val foodName = editFoodName.text.toString()
            val description = editDescription.text.toString()
            val quantity = editQuantity.text.toString().toIntOrNull()

            if (foodName.isEmpty() || quantity == null) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val foodCursor: Cursor = db.rawQuery("SELECT id FROM Food WHERE name = ?", arrayOf(foodName))

            if (foodCursor.moveToFirst()) {
                val foodId = foodCursor.getInt(0)
                val values = ContentValues().apply {
                    put("foodId", foodId)
                    put("quantity", quantity)
                }

                val result = db.insert("Orders", null, values)

                if (result != -1L) {
                    Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show()
                    editFoodName.text.clear()
                    editDescription.text.clear()
                    editQuantity.text.clear()
                } else {
                    Toast.makeText(this, "Failed to place order", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Food not found", Toast.LENGTH_SHORT).show()
            }

            foodCursor.close()
        }
    }
}
