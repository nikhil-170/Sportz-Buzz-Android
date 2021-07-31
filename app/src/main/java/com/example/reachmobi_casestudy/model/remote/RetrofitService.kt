package com.example.reachmobi_casestudy.model.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.ArrayList
/**
 * Defining the API endpoints and retrieving the objects as required
 *
 * Author: Nikhil Surya Peteti
 * email: peteti.nikhil@gmail.com
 */


interface RetrofitService {

@GET("lookupteam.php")
fun getTeamDataFromApi(@Query("id") query: Int): Call<TeamSearchList>

@GET("searchevents.php")
 fun getEventDataFromApi(@Query("e") query: String): Call<EventSearchList>

}

