package com.example.foodorder

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class FoodDbHelper(context: Context) : SQLiteOpenHelper(context, "FoodDB", null, 2) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE Food(id INTEGER PRIMARY KEY, name TEXT, price INTEGER, description TEXT)")
        db.execSQL("CREATE TABLE Orders(id INTEGER PRIMARY KEY AUTOINCREMENT, foodId INTEGER, quantity INTEGER)")

        // Insert sample food data
        db.execSQL("INSERT INTO Food(id, name, price, description) VALUES (1, 'Pizza', 250, 'Cheesy pizza')")
        db.execSQL("INSERT INTO Food(id, name, price, description) VALUES (2, 'Burger', 150, 'Spicy burger')")
        db.execSQL("INSERT INTO Food(id, name, price, description) VALUES (3, 'Pasta', 200, 'Creamy pasta')")
        db.execSQL("INSERT INTO Food(id, name, price, description) VALUES (4, 'Sandwich', 120, 'Veggie sandwich')")
        db.execSQL("INSERT INTO Food(id, name, price, description) VALUES (5, 'Fries', 80, 'Crispy fries')")
        db.execSQL("INSERT INTO Food(id, name, price, description) VALUES (6, 'Salad', 100, 'Fresh garden salad')")
        db.execSQL("INSERT INTO Food(id, name, price, description) VALUES (7, 'Noodles', 180, 'Spicy noodles')")
        db.execSQL("INSERT INTO Food(id, name, price, description) VALUES (8, 'Taco', 160, 'Mexican taco')")
        db.execSQL("INSERT INTO Food(id, name, price, description) VALUES (9, 'Ice Cream', 90, 'Vanilla ice cream')")
        db.execSQL("INSERT INTO Food(id, name, price, description) VALUES (10, 'Sushi', 300, 'Japanese sushi roll')")
        db.execSQL("INSERT INTO Food(id, name, price, description) VALUES (11, 'Paneer Tikka', 220, 'Grilled paneer cubes')")
        db.execSQL("INSERT INTO Food(id, name, price, description) VALUES (12, 'Chicken Wings', 280, 'Crispy chicken wings')")
        db.execSQL("INSERT INTO Food(id, name, price, description) VALUES (13, 'Falafel Wrap', 150, 'Middle Eastern wrap')")
        db.execSQL("INSERT INTO Food(id, name, price, description) VALUES (14, 'Chole Bhature', 130, 'Punjabi dish')")
        db.execSQL("INSERT INTO Food(id, name, price, description) VALUES (15, 'Biryani', 240, 'Spicy biryani rice')")
        db.execSQL("INSERT INTO Food(id, name, price, description) VALUES (16, 'Dosa', 100, 'South Indian dosa')")
        db.execSQL("INSERT INTO Food(id, name, price, description) VALUES (17, 'Momos', 120, 'Steamed dumplings')")
        db.execSQL("INSERT INTO Food(id, name, price, description) VALUES (18, 'Milkshake', 110, 'Cold chocolate shake')")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Food")
        db.execSQL("DROP TABLE IF EXISTS Orders")
        onCreate(db)
    }
}
