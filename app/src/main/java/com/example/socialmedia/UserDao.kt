package com.example.socialmedia

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class UserDao {
    val db= FirebaseFirestore.getInstance()
    val collection= db.collection("users")
    fun addUser(user: User?)
    {
       GlobalScope.launch (Dispatchers.IO){
           user?.let {
               collection.document(user.uid).set(it)
           }
       }
        }
    fun getUserById(id:String): Task<DocumentSnapshot>
    {
        return collection.document(id).get()
    }
    }
