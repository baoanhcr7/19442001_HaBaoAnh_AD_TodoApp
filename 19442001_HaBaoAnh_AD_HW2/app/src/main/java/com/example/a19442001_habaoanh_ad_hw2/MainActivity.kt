package com.example.a19442001_habaoanh_ad_hw2
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    val cal: Calendar = Calendar.getInstance()
    val tasks = ArrayList<Task>()
    lateinit var adapter : TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = TaskAdapter(this, tasks)
        recyclerView.adapter = adapter
        val format = SimpleDateFormat("dd-MM-yyyy", Locale.ROOT)

        refresh()

        btnAdd.setOnClickListener {
            val builder = AlertDialog.Builder(this)
                .setTitle("Add a new task")
                .setCancelable(true)
            val view = LayoutInflater.from(this).inflate(R.layout.layout_add_task, root, false)
            val editTextDate2: EditText = view.findViewById(R.id.editTextDate2)
            editTextDate2.isFocusable = false
            editTextDate2.setOnClickListener {
                val date = OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, monthOfYear)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    editTextDate2.setText(format.format(cal.time))
                }

                DatePickerDialog(
                    this@MainActivity, date, cal
                        .get(Calendar.YEAR), cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            builder.setView(view)
                .setPositiveButton("Add") { dialog, i ->
                    run {
                        val task = Task()
                        task.task = (view.findViewById(R.id.editTextTextPersonName) as EditText).text.toString()
                        task.deadline = cal.time
                        task.set()
                        refresh()
                    }
                }
            builder.show()
        }
    }

    fun refresh() {
        tasks.clear()
        adapter.notifyDataSetChanged()
        Task.get()
            .addOnSuccessListener { query ->
                val documents = query.documents
                for (doc: DocumentSnapshot in documents) {
                    val task = Task(doc)
                    tasks.add(task)
                    adapter.notifyItemInserted(tasks.size - 1)
                }
            }
    }
}