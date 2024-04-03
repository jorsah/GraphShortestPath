package com.example.navigation

interface NavigationDestination {

    fun getRoute(): NavigationRoute
    fun getPayload(): NavigationPayload? = null
    fun getConfiguration(): NavigationConfiguration? = null
}
