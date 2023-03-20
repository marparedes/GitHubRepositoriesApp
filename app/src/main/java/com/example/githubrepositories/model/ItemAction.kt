package com.example.githubrepositories.model

import androidx.annotation.Keep

@Keep
data class ItemAction(
    var name: String,
    var full_name: String,
    var owner_name : String,
    var owner_profile : String,
    var owner_avatar : String,
    var repo_link : String,
    var description : String,
    var created_date : String,
    var visibility : String,
    var topics : List<String>,
)