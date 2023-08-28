package edu.put.inf151764.data.db.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItemEntity (
    val objectType: String,

    @PrimaryKey(autoGenerate = false) val objectId: String,

    val subType: String,

    val collId: String,

    val name: String,

    val yearPublished: Int,

    val image: String,

    val thumbnail: String,

    val numPlays: Int

)
