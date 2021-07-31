package com.example.reachmobi_casestudy.model.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Defining Retrofit Library class and setting the BASE_URL
 *
 * Author: Nikhil Surya Peteti
 * email: peteti.nikhil@gmail.com
 */
class RetrofitInstance {

    companion object{
        private const val BASE_URL ="https://www.thesportsdb.com/api/v1/json/1/"

        fun getRetrofitInstance(): Retrofit {

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}