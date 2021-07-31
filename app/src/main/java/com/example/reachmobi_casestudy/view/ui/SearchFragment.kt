package com.example.reachmobi_casestudy.view.ui

/**
 * Search Fragment
 *
 * Author: Nikhil Surya Peteti
 * email: peteti.nikhil@gmail.com
 */
import android.graphics.drawable.ClipDrawable.VERTICAL
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reachmobi_casestudy.databinding.FragmentSearchBinding
import com.example.reachmobi_casestudy.viewModel.SearchFragmentViewModel
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.reachmobi_casestudy.R
import com.example.reachmobi_casestudy.model.remote.*
import com.example.reachmobi_casestudy.view.adapter.EventListAdapter
import com.mancj.materialsearchbar.MaterialSearchBar
import retrofit2.Call
import retrofit2.Response
import java.util.ArrayList



class SearchFragment: Fragment() {

    val TAG:String = "demo"
    lateinit var mBinding:FragmentSearchBinding
    private lateinit var mSearchFragnmentVM: SearchFragmentViewModel
    lateinit var eventsAdapter: EventListAdapter

    //fetching the view model and the mbinding resource onCreateView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {

            mBinding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_search, container, false)
            mSearchFragnmentVM = SearchFragmentViewModel(activity?.application!!)
            mBinding.searchViewModel = mSearchFragnmentVM
            mBinding.lifecycleOwner = this
            

            initRecyclerView()
            createData()
            return mBinding.root
    }

    //setting the layout of the recyclerview and it adapter
    private fun initRecyclerView() {
        mBinding.eventsDisplayList.apply {
            layoutManager = LinearLayoutManager(activity?.applicationContext)
            eventsAdapter = EventListAdapter(mSearchFragnmentVM.retroInstance)
            adapter = eventsAdapter
        }
    }

    fun createData(){

        var localEventsList:ArrayList<EventSearchData> = ArrayList()
        mSearchFragnmentVM.getRecyclerListDataObserver().observe(viewLifecycleOwner, Observer<ArrayList<EventSearchData>> {
            if(it!= null){
                localEventsList = it
                eventsAdapter.setList(localEventsList)
                eventsAdapter.notifyDataSetChanged() // notifying the adapter to change at the end of the flow of the search
            }else{
                Toast.makeText(activity?.applicationContext, R.string.noeventsitems, Toast.LENGTH_SHORT).show()
            }
        })

        //searchbar textchanging listener to pas the typer text to the viewmodel inorder make the API call
        mBinding.searchBar.addTextChangeListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(mBinding.searchBar.text == ""){
                    mBinding.eventsDisplayList.visibility = View.INVISIBLE
                    mBinding.beginText.visibility = View.VISIBLE
                    localEventsList.clear()
                    eventsAdapter.setList(localEventsList)

                }else{
                    mBinding.eventsDisplayList.visibility = View.VISIBLE
                    mBinding.beginText.visibility = View.INVISIBLE
                }
                mSearchFragnmentVM.getDataFromApi(mBinding.searchBar.text, activity?.applicationContext!!)
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

    }
}