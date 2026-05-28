package com.example.projetgestion1.data.models


import com.google.gson.annotations.SerializedName

// Structure exacte retournée par fakestoreapi GET /users/1 :
// {
//   "id": 1,
//   "email": "john@gmail.com",
//   "username": "johnd",
//   "password": "m38rmF$",
//   "name": { "firstname": "john", "lastname": "doe" },
//   "address": { ... },
//   "phone": "1-570-236-7033"
// }

data class UserProfile(
    val id: Int = 0,
    val username: String = "",
    val email: String = "",
    val phone: String = "",
    val name: UserName = UserName()
)

data class UserName(
    @SerializedName("firstname") val firstname: String = "",
    @SerializedName("lastname")  val lastname: String = ""
)