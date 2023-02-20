package dev.katiebarnett.experiments.jcrefresh.data

import dev.katiebarnett.experiments.jcrefresh.model.Fox
import retrofit2.Call
import retrofit2.http.GET

interface FoxService {
    @GET(".")
    fun getRandomFox(): Call<Fox>
}