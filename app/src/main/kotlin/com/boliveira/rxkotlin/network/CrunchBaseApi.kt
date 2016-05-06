package com.boliveira.rxkotlin.network

import com.boliveira.rxkotlin.model.Model
import retrofit.http.GET
import retrofit.http.Query
import rx.Observable

interface CrunchBaseApi {
    private object AppValues {
        val userKey = "73b7ad5609571f94bbb2225714501dab"
        val defaultLocation = "london"
        val defaultPage = 1
    }

    @GET("odm-organizations")
    fun organizations(
            @Query("user_key") userKey: String = AppValues.userKey,
            @Query("page") page: Int = AppValues.defaultPage,
            @Query("locations") locations: Array<String> = arrayOf(AppValues.defaultLocation)
    ): Observable<Model.ODMOrganizations>
}
