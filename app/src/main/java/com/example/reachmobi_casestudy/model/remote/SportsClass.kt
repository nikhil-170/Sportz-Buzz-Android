package com.example.reachmobi_casestudy.model.remote

import java.util.ArrayList

/**
 * SportzBuzz: model class for Events and Players
 *
 * Author: Nikhil Surya Peteti
 * email: peteti.nikhil@gmail.com
 */

data class EventSearchList(val event: ArrayList<EventSearchData>)
data class TeamSearchList(val teams: ArrayList<TeamSearchData>)
data class EventSearchData(val strEvent: String, val strThumb: String,val idEvent: Int, val strLeague: String,
                           val strVenue:String, val strDescriptionEN: String, val idHomeTeam: String, val idAwayTeam: String)
data class TeamSearchData(val strTeam: String, val strSport: String, val idTeam: Int, val strTeamBadge: String)