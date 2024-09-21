package com.sumin.vknewsclient.core

interface EventHandler<T> {

    fun obtainEvent(event : T)

}
