package com.uits.baseproject.base

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Use object so we have a singleton instance
 *
 * Copyright © 2018 SOFT ONE  CO., LTD
 * Created by PhuQuy on 6/6/19.
 */

object BaseRxBus {

    private val publisher = PublishSubject.create<Any>()

    fun publish(event: Any) {
        publisher.onNext(event)
    }

    // Listen should return an Observable and not the publisher
    // Using ofType we filter only events that match that class type
    fun <T> listen(eventType: Class<T>): Observable<T> = publisher.ofType(eventType)
}


