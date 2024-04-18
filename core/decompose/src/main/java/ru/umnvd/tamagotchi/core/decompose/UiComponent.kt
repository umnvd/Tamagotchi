package ru.umnvd.tamagotchi.core.decompose

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface UiComponent<State : Any, Effect : Any> {
    val state: StateFlow<State>
    val effect: Flow<Effect>

    abstract class Base<State : Any, Effect : Any>(
        componentContext: ComponentContext,
        initialState: State,
    ) : ComponentContext by componentContext,
        UiComponent<State, Effect> {
        protected val scope = componentCoroutineScope()

        private val mutableState = MutableStateFlow(initialState)
        private val mutableEffect = Channel<Effect>()

        override val state: StateFlow<State> = mutableState

        override val effect: Flow<Effect> = mutableEffect.receiveAsFlow()

        protected val currentState: State get() = state.value

        protected fun state(update: State.() -> State) {
            mutableState.update(update)
        }

        protected fun effect(effect: Effect) {
            scope.launch {
                mutableEffect.send(effect)
            }
        }
    }
}

@Composable
fun <State : Any, Effect : Any> UiComponent<State, Effect>.collectAsState(
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
): androidx.compose.runtime.State<State> {
    val lifecycleOwner = LocalLifecycleOwner.current

    val stateFlowLifecycleAware = remember(state, lifecycleOwner) {
        state.flowWithLifecycle(lifecycleOwner.lifecycle, lifecycleState)
    }

    // Need to access the initial value to convert to State - collectAsState() suppresses this lint warning too
    @SuppressLint("StateFlowValueCalledInComposition")
    val initialValue = state.value
    return stateFlowLifecycleAware.collectAsState(initialValue)
}

@SuppressLint("ComposableNaming")
@Composable
fun <State : Any, Effect : Any> UiComponent<State, Effect>.collectEffect(
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    onEffect: (suspend (effect: Effect) -> Unit),
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(effect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(lifecycleState) {
            effect.collect { onEffect(it) }
        }
    }
}
