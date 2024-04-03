package com.example.uikit.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.example.navigation.navigate
import com.example.uikit.utills.OnLifecycleEvent


@Composable
fun BaseScreen(
    viewModel: BaseViewModel<*>,
    navController: NavController,
    onLeave: (() -> Unit)? = null,
    ignoreStatusBarPadding: Boolean = false,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .navigationBarsPadding()
            .imePadding()
            .then(
                if (ignoreStatusBarPadding) {
                    Modifier
                } else {
                    Modifier.statusBarsPadding()
                }
            )
    ) {
        content()
    }


    /**
     * Behavior for goBackResult navigation
     */
    LaunchedEffect(Unit) {
        navController.currentBackStackEntry?.savedStateHandle?.let { currentSaveStateHandle ->
            viewModel.currentSavedStateHandle = currentSaveStateHandle
        }
    }

    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_START -> {
                viewModel.onUIEvent(LifecycleUIEvent.OnStart)
            }

            Lifecycle.Event.ON_RESUME -> {
                viewModel.onUIEvent(LifecycleUIEvent.OnResume)
            }

            Lifecycle.Event.ON_PAUSE -> {
                viewModel.onUIEvent(LifecycleUIEvent.OnPause)
            }

            Lifecycle.Event.ON_STOP -> {
                viewModel.onUIEvent(LifecycleUIEvent.OnStop)
            }

            else -> {
                // Do nothing
            }
        }
    }

    if (viewModel.shouldGoBack.value) {
        onLeave?.invoke()
        viewModel.goBackResult.value?.let {
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set(it.payloadKey, it)
        }
        navController.navigateUp()
        viewModel.onUIEvent(HandledBackUIEvent)
    }

    viewModel.destination.value?.let {
        viewModel.onUIEvent(HandledDestinationUIEvent)
        onLeave?.invoke()
        navController.navigate(it)
    }
}
