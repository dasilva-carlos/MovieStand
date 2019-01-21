package com.dasilvacarlos.moviesstand.data.workers.recommendations


interface RecommendationsProvider {
    fun subscribeToRecommendations(quantity: Int)
    fun unsubscribe()
}