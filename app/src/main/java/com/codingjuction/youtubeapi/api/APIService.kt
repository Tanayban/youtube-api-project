package com.codingjuction.youtubeapi.api

import com.codingjuction.youtubeapi.utils.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIService {
    companion object {
         fun <LPL> createService(cls: Class<LPL>): LPL {
             val retrofit: Retrofit =
                 Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                     .baseUrl(Constant.BASE_URL).build()
             return retrofit.create(cls)
         }

        val youtubeApi = createService(YoutubeApi::class.java)

    }
}