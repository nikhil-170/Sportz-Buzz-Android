package com.example.reachmobi_casestudy.viewModel
/**
 * Search Fragment View Model in order to control the view on the search fragment
 *
 * Author: Nikhil Surya Peteti
 * email: peteti.nikhil@gmail.com
 */

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.reachmobi_casestudy.R
import com.example.reachmobi_casestudy.model.remote.*
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import java.util.ArrayList

class SearchFragmentViewModel(application: Application): AndroidViewModel(application){

    var eventSearchList: MutableLiveData<ArrayList<EventSearchData>> // implementing Live data to maintain the consistency of the data
    val retroInstance = RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
    val TAG:String = "demo"


    init {
        eventSearchList = MutableLiveData()
    }

    fun getRecyclerListDataObserver(): MutableLiveData<ArrayList<EventSearchData>> {
        return eventSearchList
    }

     fun getDataFromApi(searchString: String, context: Context) {

         //making the API call based on the enterred text
         val call = retroInstance.getEventDataFromApi(searchString)

         //making the call back to get the response of the API Call
         call.enqueue(object :retrofit2.Callback<EventSearchList>{
                 override fun onResponse(call: Call<EventSearchList>, response: Response<EventSearchList>) {
                     if (response.isSuccessful && response.body() != null){
                         eventSearchList.postValue(response.body()?.event)
                     }else{
                         // showing a toast if the returned list is null
                         //Toast.makeText(context,R.string.noeventsitems, Toast.LENGTH_SHORT).show()
                     }
                 }
                 override fun onFailure(call: Call<EventSearchList>, t: Throwable) {
                     eventSearchList.postValue(null)
                 }

             })
     }
}