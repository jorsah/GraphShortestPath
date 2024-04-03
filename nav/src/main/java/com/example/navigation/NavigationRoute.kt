package com.example.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink

sealed class NavigationRoute {

    abstract fun endpoint(): String

    /**
     * Should fill if Route will be open in some Graph flow.
     * Then, navigation opening Graph with payload of route.
     */
    open fun graph(): String = ""

    open fun deepLinks(): List<NavDeepLink> = emptyList()

    abstract class ScreenRoute : NavigationRoute() {
        @Composable
        abstract fun View(navController: NavController)
    }
}
