package com.droidcon.composablebank.navigation

object Destinations {
    fun detailRoute(composableName: String): String {
        return "detail/$composableName"
    }

    fun demoRoute(composableName: String): String {
        return "demo/$composableName"
    }
}