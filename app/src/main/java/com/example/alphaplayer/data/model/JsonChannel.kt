//package com.example.alphaplayer.data.model
//import kotlinx.serialization.Serializable
//
//@Serializable
//data class JsonChannel(
//    val id: String? = null,
//    val link2: String? = null,
//    val img: String? = null,
//    val genre: String? = null,
//    val lang: String? = null,
//    val name: String? = null,
//    val catchup: String? = null,
//    val author: String? = null,
//
//    // Replace this with the actual field name if different
//    val url: String? = null
//)
//
//


package com.example.alphaplayer.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiChannel(

    val id: String = "",
    val title: String = "",
    val imageUrl: String = "",
    val videoUrl: String = "",

    val epgId: String = "",
    val cookie: String = "",
    val userAgent: String = "",
    val referer: String = "",
    val origin: String = "",
    val clearkeyLicenseKeyId: String = ""

)
