package dev.katiebarnett.experiments.holiday.featureflags

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RocketFlagService {

    @GET("/v1/flags/sq26LQ2XYrU9RMLh1GMa")
    fun getChristmasTheme(@Query("env") environment: String = "Production"): Call<FeatureFlag>

    @GET("/v1/flags/{flagId}")
    fun getFeatureFlag(
        @Path("flagId") id: String,
        @Query("env") environment: String = "Production",
    ): Call<FeatureFlag>
}
