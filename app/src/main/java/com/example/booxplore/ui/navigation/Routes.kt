package com.example.booxplore.ui.navigation

object Routes {
    const val HOME = "home"
    const val DETAIL = "detail/{bookId}"
    const val FAVORITES = "favorites"
}

fun detailRoute(bookId: String) = "detail/$bookId"