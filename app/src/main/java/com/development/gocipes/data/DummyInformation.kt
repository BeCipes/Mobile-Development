package com.development.gocipes.data

import com.development.gocipes.model.Information

object DummyInformation {
    val dummyArticle = listOf(
        Information(
            id = 1,
            name = "Pentingnya Gizi Seimbang bagi tubuh",
            description = "",
            imageUrl = "https://diskes.badungkab.go.id/storage/diskes/image/GIZI%20SEIMBANG.png"
        ),
        Information(
            id = 2,
            name = "Mengapa Gizi Seimbang itu Penting?",
            description = "",
            imageUrl = "https://www.tanotofoundation.org/wp-content/uploads/2022/01/WhatsApp-Image-2022-01-18-at-14.42.44-1-1024x768.jpeg"
        )
    )

    val dummyTechnique = listOf(
        Information(
            id = 1,
            name = "Cara memegang pisau yang mahir",
            description = "",
            imageUrl = "https://www.dictio.id/uploads/db3342/original/3X/2/7/27e5072305accd71a09957761e6e511bfeb183ac.jpg"
        ),
        Information(
            id = 2,
            name = "Cara mengiris bawang agar tidak menangis",
            description = "",
            imageUrl = "https://cdn-brilio-net.akamaized.net/news/2019/03/01/160204/750xauto-12-cara-memotong-bawang-merah-tanpa-bikin-mata-menangis-1903017.jpg"
        )
    )
}