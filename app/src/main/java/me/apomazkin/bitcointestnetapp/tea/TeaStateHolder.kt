package me.apomazkin.bitcointestnetapp.tea

import kotlinx.coroutines.flow.StateFlow

interface TeaStateHolder<State, Message> {

    val state: StateFlow<State>
    fun accept(message: Message)
}