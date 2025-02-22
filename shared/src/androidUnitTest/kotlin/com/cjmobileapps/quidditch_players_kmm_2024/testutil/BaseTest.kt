package com.cjmobileapps.quidditch_players_kmm_2024.testutil

import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import kotlin.reflect.KClass
import kotlin.test.BeforeTest

abstract class BaseTest {

    @BeforeTest
    open fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    inline fun <reified T : Any> any() = Mockito.any(T::class.java) ?: createInstance<T>()

    inline fun <reified T : Any> createInstance(): T = createInstance(T::class)

    fun <T : Any> createInstance(kClass: KClass<T>): T = castNull()

    @Suppress("UNCHECKED_CAST")
    private fun <T> castNull(): T = null as T
}
