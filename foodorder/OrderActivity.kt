package com.example.foodorder

import android.app.AlertDialog
import android.database.Cursor
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class OrderActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var dbHelper: FoodDbHelper
    private lateinit var adapter: OrderAdapter
    private var orders = mutableListOf<Order>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_orders)

        dbHelper = FoodDbHelper(this)
        listView = findViewById(R.id.orderListView)

        loadOrders()

        adapter = OrderAdapter()
        listView.adapter = adapter
    }

    private fun loadOrders() {
        orders.clear()

        val db = dbHelper.readableDatabase
        val query = """
            SELECT Orders.id, Food.name, Food.description, Food.price, Orders.quantity
            FROM Orders
            JOIN Food ON Orders.foodId = Food.id
        """.trimIndent()

        val cursor: Cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            val desc = cursor.getString(2)
            val price = cursor.getInt(3)
            val qty = cursor.getInt(4)
            val totalPrice = price * qty

            orders.add(Order(id, name, desc, qty, totalPrice))
        }
        cursor.close()
    }

    inner class OrderAdapter : BaseAdapter() {
        override fun getCount(): Int = orders.size
        override fun getItem(position: Int): Any = orders[position]
        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup?): android.view.View {
            val view = layoutInflater.inflate(R.layout.order_item, parent, false)
            val order = orders[position]

            view.findViewById<TextView>(R.id.foodName).text = "Food: ${order.name}"
            view.findViewById<TextView>(R.id.description).text = "Description: ${order.description}"
            view.findViewById<TextView>(R.id.quantity).text = "Quantity: ${order.quantity}"
            view.findViewById<TextView>(R.id.totalPrice).text = "Total: ₹${order.totalPrice}"

            view.findViewById<Button>(R.id.deleteButton).setOnClickListener {
                AlertDialog.Builder(this@OrderActivity)
                    .setTitle("Delete Order")
                    .setMessage("Are you sure you want to delete this order?")
                    .setPositiveButton("Yes") { _, _ ->
                        val db = dbHelper.writableDatabase
                        db.delete("Orders", "id=?", arrayOf(order.id.toString()))
                        loadOrders()
                        notifyDataSetChanged()
                        Toast.makeText(this@OrderActivity, "Order deleted", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("No", null)
                    .show()
            }

            return view
        }
    }

    data class Order(val id: Int, val name: String, val description: String, val quantity: Int, val totalPrice: Int)
}
