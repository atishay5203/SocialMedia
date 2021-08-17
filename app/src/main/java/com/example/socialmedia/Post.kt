package com.example.socialmedia

data class Post(val data:String="", val user:User=User(), val time:Long=0L,val likedBy:ArrayList<String> = ArrayList())

