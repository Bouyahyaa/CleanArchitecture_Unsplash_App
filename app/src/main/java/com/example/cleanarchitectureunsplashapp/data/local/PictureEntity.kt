package com.example.cleanarchitectureunsplashapp.data.local

import androidx.room.*

@Entity(tableName = "pictures", indices = [Index(value = ["id"], unique = true)])
data class PictureEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "uuid") val uuid: Int = 0,
    val id: String,
    val width: String?,
    val height: String?,
    val color: String?,
    val created_at: String?,
    val updated_at: String?,
    @ColumnInfo(name = "description", defaultValue = "No Description") val description: String?,

    /** Urls **/
    val raw: String?,
    val full: String?,
    val regular: String?,
    val thumb: String?,

    /** User **/
    val idUser: String?,
    val username: String?,
    val name: String?,
    // ProfileImage
    val small: String?,
    val medium: String?,
    val large: String?,

    val likes: String?,
) : java.io.Serializable