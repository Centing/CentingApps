package com.c241ps220.centingapps.views.Fragment.BerandaFragment.Article

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val date: String,
    val title: String,
    val publisher: String,
    val urlArticle: String,
    val timelapse:String,
    val image:String
): Parcelable

val articleData = listOf(
    Article(
        date = "07-06-2024",
        title = "Ini Rekomendasi 5 Makanan Murah Meriah Pencegah Stunting dari Kemenkes",
        publisher = "Medcom.id",
        urlArticle = "https://www.medcom.id/gaya/fitness-health/aNr7M82b-ini-rekomendasi-5-makanan-murah-meriah-pencegah-stunting-dari-kemenkes",
        timelapse = "4 jam",
        image = "https://cdn.medcom.id/dynamic/content/2024/06/07/1687793/UC4ThdniX3.jpg?w=500"
    ),Article(
        date = "10-05-2024",
        title = "MPASI Kaya Protein Hewani Cegah Stunting",
        publisher = "Upk Kemenkes",
        urlArticle = "https://upk.kemkes.go.id/new/mpasi-kaya-protein-hewani-cegah-stunting",
        timelapse = "4 jam",
        image = "https://upk.kemkes.go.id/new/imagex/content/64e66a1ec446183636651aa0a844f8c3.jpg"
    ),Article(
        date = "01-06-2024",
        title = "Jika Sudah Terdeteksi, Inilah yang Dilakukan untuk Mengobati Stunting Anak",
        publisher = "GridHealth.id",
        urlArticle = "https://health.grid.id/read/353819585/jika-sudah-terdeteksi-inilah-yang-dilakukan-untuk-mengobati-stunting-anak?page=all",
        timelapse = "4 jam",
        image = "https://asset-a.grid.id/crop/0x0:0x0/700x465/photo/2023/05/11/picsart_23-05-11_09-32-12-771jp-20230511093231.jpg"
    ),Article(
        date = "04-06-2024",
        title = "4 Dampak Stunting yang Terjadi pada Anak",
        publisher = "Halodoc",
        urlArticle = "https://www.halodoc.com/artikel/4-dampak-stunting-yang-terjadi-pada-anak",
        timelapse = "4 jam",
        image = "https://d1vbn70lmn1nqe.cloudfront.net/prod/wp-content/uploads/2022/12/09080153/Perlu-diwaspadai-Ini-X-Dampak-Stunting-pada-Anak-1.jpg.webp"
    )
)