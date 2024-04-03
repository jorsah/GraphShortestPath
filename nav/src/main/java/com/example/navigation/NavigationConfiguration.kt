package com.example.navigation

import androidx.navigation.NavOptions

data class NavigationConfiguration(
    val singleTop: Boolean = false,
    val clearStack: Boolean = false,
    val popUpTo: NavigationRoute? = null,
    val inclusive: Boolean = false
)

fun NavigationConfiguration.toNavOptions(): NavOptions {
    val navOptionsBuilder = NavOptions.Builder()

    if (singleTop) {
        navOptionsBuilder.setLaunchSingleTop(true)
    }

    if (clearStack) {
        navOptionsBuilder.setPopUpTo(0, true)
    } else {
        popUpTo?.let {
            navOptionsBuilder.setPopUpTo(
                popUpTo.endpoint(),
                inclusive
            )
        }
    }

    return navOptionsBuilder.build()
}
