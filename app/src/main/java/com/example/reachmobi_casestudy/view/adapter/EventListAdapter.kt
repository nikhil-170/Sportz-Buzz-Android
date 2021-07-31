package com.example.reachmobi_casestudy.view.adapter


/**
 * Event list adapter to bind the items from the API to the view
 *
 * Author: Nikhil Surya Peteti
 * email: peteti.nikhil@gmail.com
 */
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.reachmobi_casestudy.R
import com.example.reachmobi_casestudy.databinding.EventListAdapterBinding
import com.example.reachmobi_casestudy.model.remote.*
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Response
import kotlin.math.log


class EventListAdapter(retrofitInstance: RetrofitService):RecyclerView.Adapter<EventListAdapter.EventHolder>() {

    var eventSearchItemList = ArrayList<EventSearchData>()
    val retroInstance = retrofitInstance

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : EventListAdapterBinding = DataBindingUtil.inflate(layoutInflater,R.layout.event_list_adapter, parent, false)
        return EventHolder(binding)
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {//passing the object according to the recyclerview position from the list
        holder.bind(eventSearchItemList[position])
    }

    override fun getItemCount(): Int {
        return eventSearchItemList.size
    }

    fun setList(searchItemList: ArrayList<EventSearchData>){
        eventSearchItemList.clear()
        this.eventSearchItemList = searchItemList
    }

    inner class EventHolder(val binding:EventListAdapterBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(eventItem:EventSearchData){ //bind the items from parsed object

            binding.eventLeague.text = eventItem.strLeague
            binding.eventVenue.text = eventItem.strVenue
            /*
            * fetching the idHomeTeam and idAwayTeam to make separate Api call response
            * and binding the fetched values to the view by directly providing the reference
            * */
            try {
                retroInstance.getTeamDataFromApi(eventItem.idHomeTeam.toInt())
                    .enqueue(object : retrofit2.Callback<TeamSearchList> {
                        override fun onResponse(
                            call: Call<TeamSearchList>,
                            response: Response<TeamSearchList>,
                        ) {
                            if (response.isSuccessful) {
                                binding.homeTeamName.text = response.body()?.teams!![0].strTeam
                                Picasso.get().load(response.body()?.teams!![0].strTeamBadge)
                                    .into(binding.eventHomeTeam)
                            }
                        }

                        override fun onFailure(call: Call<TeamSearchList>, t: Throwable) {
                        }
                    })
            }catch (e:NumberFormatException){
                e.printStackTrace()
                Log.d("demo", "bind ${e.message}: ")
            }

            try {
                retroInstance.getTeamDataFromApi(eventItem.idAwayTeam.toInt())
                    .enqueue(object : retrofit2.Callback<TeamSearchList> {
                        override fun onResponse(
                            call: Call<TeamSearchList>,
                            response: Response<TeamSearchList>,
                        ) {
                            if (response.isSuccessful) {
                                binding.awayTeamName.text = response.body()?.teams!![0].strTeam
                                Picasso.get().load(response.body()?.teams!![0].strTeamBadge)
                                    .into(binding.eventAwayTeam)
                            }
                        }

                        override fun onFailure(call: Call<TeamSearchList>, t: Throwable) {
                        }
                    })
            }catch (e:NumberFormatException){
                e.printStackTrace()
                Log.d("demo", "bind ${e.message}: ")
            }


        }
    }
}

