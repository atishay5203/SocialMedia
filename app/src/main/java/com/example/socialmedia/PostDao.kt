package com.example.socialmedia

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@DelicateCoroutinesApi
class PostDao {
    val db= FirebaseFirestore.getInstance()
    val postCollection= db.collection("posts")
    val auth= Firebase.auth
    fun addPost(inputPost:String)
    {
        GlobalScope.launch {
            val currUserId= auth.currentUser!!.uid
            val userdao= UserDao()
            val user= userdao.getUserById(currUserId).await().toObject(User::class.java)!!
            val currTime= System.currentTimeMillis()
            val post= Post(inputPost,user,currTime)
            postCollection.document().set(post)

            }
        }
    fun getPost(postId:String) : Task<DocumentSnapshot>
    {
        return postCollection.document(postId).get()
    }
    fun updateLikes(postId: String)
    {
        GlobalScope.launch {
            val curruser= auth.currentUser!!.uid
            val post= getPost(postId).await().toObject(Post::class.java)!!
            if(post.likedBy.contains(curruser))
            {
                post.likedBy.remove(curruser)
            }
            else {
                post.likedBy.add(curruser)
            }
            postCollection.document(postId).set(post)

        }
    }


}