package com.example.socialmedia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class PostAdapter(options: FirestoreRecyclerOptions<Post>,private val listener:IPostAdapter) :FirestoreRecyclerAdapter<Post,PostAdapter.postViewAdapter>(options) {
    inner class postViewAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val creator = itemView.findViewById<TextView>(R.id.postcreator)
        private val posttime = itemView.findViewById<TextView>(R.id.postTime)
        private val postdata = itemView.findViewById<TextView>(R.id.postData)
        private val postimage = itemView.findViewById<ImageView>(R.id.postImage)
       var likebutton = itemView.findViewById<ImageButton>(R.id.likeButton)
        private val likecount = itemView.findViewById<TextView>(R.id.likeCount)

        fun bindView(model: Post) {
            creator.text = model.user.uName.toString()
            Glide.with(postimage).load(model.user.imageUrl).circleCrop().into(postimage)
            posttime.text = TimeUtils.getTimeAgo(model.time)
            postdata.text = model.data
            likecount.text = model.likedBy.size.toString()
            likebutton.setOnClickListener {
                val auth = Firebase.auth
                val userid = auth.currentUser!!.uid
                if (model.likedBy.contains(userid)) {
                    likebutton.setImageResource(R.drawable.ic_baseline_star_outline_24)

                } else {
                    likebutton.setImageResource(R.drawable.ic_baseline_star_24)
                }

            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): postViewAdapter {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.samplepost, parent, false)

       val holder= postViewAdapter(itemView)
        holder.likebutton.setOnClickListener {
            listener.onLikeClicked(snapshots.getSnapshot(holder.adapterPosition).id)
        }
        return holder
    }

    override fun onBindViewHolder(holder: postViewAdapter, position: Int, model: Post) {
        return holder.bindView(model)
    }

    interface IPostAdapter {
        fun onLikeClicked(postId: String)
    }
}