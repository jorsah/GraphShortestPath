package com.example.navigation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

interface NavigationProvider {
    val destination: State<NavigationDestination?>
    val shouldGoBack: State<Boolean>
    val goBackResult: State<NavigationPayload?>
    fun openDestination(destination: NavigationDestination)
    fun goBack(result: NavigationPayload? = null)
    fun lockNavigation(isNavigationLocked: Boolean)
    fun clearGoBack()
    fun clearDestination()
}

class NavigationHolder : NavigationProvider {

    private val _destination = mutableStateOf<NavigationDestination?>(null)
    override val destination: State<NavigationDestination?>
        get() = _destination

    private val _shouldGoBack = mutableStateOf(false)
    override val shouldGoBack: State<Boolean>
        get() = _shouldGoBack

    private val _goBackResult = mutableStateOf<NavigationPayload?>(null)
    override val goBackResult: State<NavigationPayload?>
        get() = _goBackResult

    private var isOpenDestinationLocked: Boolean = false

    override fun openDestination(destination: NavigationDestination) {
        if (isOpenDestinationLocked || _destination.value != null) {
            return
        }

        _destination.value = destination
    }

    override fun goBack(result: NavigationPayload?) {
        _goBackResult.value = result
        _shouldGoBack.value = true
    }

    override fun lockNavigation(isNavigationLocked: Boolean) {
        isOpenDestinationLocked = isNavigationLocked
    }

    override fun clearGoBack() {
        _shouldGoBack.value = false
        _goBackResult.value = null
    }

    override fun clearDestination() {
        _destination.value = null
    }
}
