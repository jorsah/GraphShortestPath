package com.example.navigation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.net.toUri
import androidx.navigation.*
import androidx.navigation.compose.composable

fun NavGraphBuilder.activateNavigationRoutes(
    navController: NavController,
    list: List<NavigationRoute>
) {
    list.forEach { route ->
        when (route) {
            is NavigationRoute.ScreenRoute ->
                composable(
                    route = route.endpoint(),
                    deepLinks = route.deepLinks()
                ) {
                    route.View(navController = navController)
                }
        }
    }
}

@SuppressLint("RestrictedApi")
fun NavController.navigate(
    route: String,
    args: Bundle,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    val routeLink = NavDeepLinkRequest
        .Builder
        .fromUri("android-app://androidx.navigation/$route".toUri())
        .build()

    val deepLinkMatch = graph.matchDeepLink(routeLink)
    if (deepLinkMatch != null) {
        val destination = deepLinkMatch.destination
        val id = destination.id
        navigate(id, args, navOptions, navigatorExtras)
    } else {
        navigate(route, navOptions, navigatorExtras)
    }
}

fun NavController.navigate(destination: NavigationDestination) {
    val route = destination.getRoute().graph().ifEmpty { destination.getRoute().endpoint() }

    navigate(
        route,
        Bundle().apply {
            destination.getPayload()?.let { payload ->
                putParcelable(payload.payloadKey, payload)
            }
        },
        destination.getConfiguration()?.toNavOptions()
    )
}
