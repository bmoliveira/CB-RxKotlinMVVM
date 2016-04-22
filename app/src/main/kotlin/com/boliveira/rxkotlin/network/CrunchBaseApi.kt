package com.boliveira.rxkotlin.network

import com.boliveira.rxkotlin.model.Model
import retrofit.http.GET
import retrofit.http.Query
import rx.Observable

interface CrunchBaseApi {

    companion object {
        private val userKey = "73b7ad5609571f94bbb2225714501dab"
    }

    @GET("odm-organizations")
    fun organizations(
            @Query("user_key") userKey: String = Companion.userKey,
            @Query("page") page: Int = 1): Observable<Model.ODMOrganizations>
}
