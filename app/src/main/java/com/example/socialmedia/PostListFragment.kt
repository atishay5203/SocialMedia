package com.example.socialmedia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.android.synthetic.main.fragment_post_list.*


@DelicateCoroutinesApi
class PostListFragment : Fragment(),PostAdapter.IPostAdapter {

private lateinit var postDao: PostDao
private lateinit var postAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        postDao= PostDao()

        val postcollection= postDao.postCollection
        val query= postcollection.orderBy("time",Query.Direction.DESCENDING)
        val recyclerViewOptions= FirestoreRecyclerOptions.Builder<Post>().setQuery(query,Post::class.java).build()
        postAdapter= PostAdapter(recyclerViewOptions,this)


        with(tasks_list) {
            layoutManager = LinearLayoutManager(activity)
            adapter = postAdapter
        }


        addTask.setOnClickListener{
            findNavController().navigate(PostListFragmentDirections.actionPostListFragmentToPostFragment())

        }

    }

    override fun onStart() {
        super.onStart()
        postAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        postAdapter.stopListening()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_list, container, false)
    }

    override fun onLikeClicked(postId: String) {
        postDao.updateLikes(postId)
    }

}