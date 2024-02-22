package me.apomazkin.bitcointestnetapp.tea

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class Tea<State, Message, Effect>(
        initState: State,
        reducer: TeaReducer<State, Message, Effect>,
        initEffects: Set<Effect>,
        private val effectHandlerSet: Set<TeaEffectHandler<Message, Effect>>,
        private val coroutineScope: CoroutineScope,
) : TeaStateHolder<State, Message>,
    TeaReducer<State, Message, Effect> by reducer,
    TeaEffectHandler<Message, Effect> {

    private val _state: MutableStateFlow<State> = MutableStateFlow(initState)

    override val state: StateFlow<State>
        get() = _state.asStateFlow()

    init {
        executeEffect(initEffects)
    }

    override fun accept(message: Message) {
        val (newState, effects) = reduce(_state.value, message)
        _state.value = newState
        executeEffect(effects)
    }

    private fun executeEffect(effects: Set<Effect>) {
        effects.forEach { effect: Effect ->
            coroutineScope.launch {
                runEffect(effect, ::accept)
            }
        }
    }

    override suspend fun runEffect(effect: Effect, consumer: (Message) -> Unit) {
        effectHandlerSet.forEach {
            it.runEffect(effect, consumer)
        }
    }
}