package me.apomazkin.bitcointestnetapp.tea

typealias ReducerResult<State, Effect> = Pair<State, Set<Effect>>

interface TeaReducer<State, Message, Effect> {
    fun reduce(state: State, message: Message): ReducerResult<State, Effect>
}