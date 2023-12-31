package com.development.gocipes.core.utils

import com.development.gocipes.core.data.remote.response.article.ArticleItem
import com.development.gocipes.core.data.remote.response.technique.TechniqueItem
import com.development.gocipes.core.domain.model.article.Article
import com.development.gocipes.core.domain.model.technique.Technique

fun TechniqueItem.toDomain() = Technique(
    id = id,
    title = title,
    description = description,
    cover = cover,
    source = sumber,
    videoUrl = url
)

fun ArticleItem.toDomain() = Article(
    id = id,
    cover = gambar?.get(0),
    headline = headline,
    penulis = penulis,
    sumber = sumber,
    content = isi
)