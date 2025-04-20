package com.example.foodorder

import android.content.Intent
import android.os.Bundle
import android.view.MenuInflater
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var foodDbHelper: FoodDbHelper
    private lateinit var foodList: ArrayList<Food>
    private lateinit var adapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.foodRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        foodDbHelper = FoodDbHelper(this)
        foodList = ArrayList()

        val db = foodDbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Food", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val price = cursor.getInt(2)
                val desc = cursor.getString(3)
                foodList.add(Food(id, name, price, desc))
            } while (cursor.moveToNext())
        }
        cursor.close()

        adapter = FoodAdapter(foodList)
        recyclerView.adapter = adapter

        // 🔹 Toolbar Menu Handling
        val rightIcon: ImageView = findViewById(R.id.right_icon)
        rightIcon.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view)
            val inflater: MenuInflater = popupMenu.menuInflater
            inflater.inflate(R.menu.menu_toolbar, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_food_orders -> {
                        val intent = Intent(this, PlaceOrderActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.menu_all_food -> {
                        val intent = Intent(this, OrderActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }
    }
}

