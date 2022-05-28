package com.example.uproject.domain.model

import android.os.Parcelable
import com.example.uproject.data.source.Remote.response.CategoryResponse
import com.example.uproject.data.source.local.entity.CategoryEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Int,
    val name: String,
    val imageUrl: String
): Parcelable

fun CategoryResponse.toCategoryEntity(id: Int) =
    CategoryEntity(
        idCategory  = id,
        name        = this.name,
        image       = this.image
    )

fun CategoryResponse.toCategory(id: Int) =
    Category(id       = id,
             name     = this.name,
             imageUrl = this.image)