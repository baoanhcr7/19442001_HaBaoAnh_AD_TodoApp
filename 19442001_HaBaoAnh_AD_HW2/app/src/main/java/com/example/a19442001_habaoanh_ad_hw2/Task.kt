package com.example.a19442001_habaoanh_ad_hw2

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class Task() {
    var id: String? = null
    var task: String? = null
    var deadline: Date? = null
    var done: Boolean? = false

    constructor(task: String, deadline: Date, done: Boolean) : this() {
        this.task = task
        this.deadline = deadline
        this.done = done
    }

    constructor(doc: DocumentSnapshot) : this() {
        id = doc.id
        task = doc.getString("task")
        deadline = doc.getDate("deadline")
        done = doc.getBoolean("done")
    }

    fun set() {
        if (id != null) {
            Firebase.firestore.collection("todolist").document(id!!)
                .set(
                    hashMapOf(
                        "task" to task,
                        "deadline" to deadline,
                        "done" to done
                    )
                )
        } else {
            Firebase.firestore.collection("todolist")
                .add(
                    hashMapOf(
                        "task" to task,
                        "deadline" to deadline,
                        "done" to done
                    )
                )
        }
    }

    companion object {
        fun get() : Task<QuerySnapshot> {
            return Firebase.firestore.collection("todolist").get()
        }

        fun getRecent() : Task<QuerySnapshot> {
            // Get 8 newest products
            return Firebase.firestore.collection("todolist")
                .orderBy("deadline", Query.Direction.DESCENDING)
                .get()
        }

        fun get(id : String) : Task<DocumentSnapshot> {
            return Firebase.firestore.collection("todolist").document(id).get()
        }
    }
}