package com.example.a19442001_habaoanh_ad_hw2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TaskAdapter(val context: Context, val tasks: ArrayList<Task>) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_a_task, parent, false)
        return ViewHolder(R.layout.layout_a_task, view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]
        val format = SimpleDateFormat("dd-MM-yyyy", Locale.ROOT)

        try {
            holder.checkBox!!.isChecked = task.done!!
            holder.editTextDate!!.setText(format.format(task.deadline!!))
            holder.item!!.text = task.task
        } catch (ignored : Exception) {}
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    class ViewHolder(layoutId: Int, itemView: View) : RecyclerView.ViewHolder(itemView) {
        var checkBox : CheckBox? = null
        var editTextDate : EditText? = null
        var item : TextView? = null

        init {
            checkBox = itemView.findViewById(R.id.checkBox)
            editTextDate = itemView.findViewById(R.id.editTextDate)
            item = itemView.findViewById(R.id.item)
        }
    }
}