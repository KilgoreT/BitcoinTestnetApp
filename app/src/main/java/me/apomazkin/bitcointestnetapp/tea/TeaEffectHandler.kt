package me.apomazkin.bitcointestnetapp.tea

interface Effect
interface TeaEffectHandler<Message, out Effect> {
    suspend fun runEffect(
        effect: @UnsafeVariance Effect,
        consumer: (Message) -> Unit
    )
}