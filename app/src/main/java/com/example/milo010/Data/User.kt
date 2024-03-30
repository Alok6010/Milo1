package com.example.milo010.Data

data class User(

        val userName : String = "",
        val userEmail : String = "",
        val userProfileImage : String = "",
        val listOfFollowings : List<String> = listOf(),
        val listOfFollowers : List<String> = listOf(),
        val listOfuserPhotos : List<String> = listOf(),
        val uid : String = "",

)