package com.example.socialmedia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.coroutines.DelicateCoroutinesApi


@DelicateCoroutinesApi
class PostFragment : Fragment() {





    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        post.setOnClickListener {
            val inputPost= postInput.text.toString()
            if(inputPost.isNotEmpty()) {
                PostDao().addPost(inputPost)
              findNavController().navigate(PostFragmentDirections.actionPostFragmentToPostListFragment())
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

}