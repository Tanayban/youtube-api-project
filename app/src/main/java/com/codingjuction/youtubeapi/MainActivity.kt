package com.codingjuction.youtubeapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingjuction.youtubeapi.adapter.YoutubeAdapter
import com.codingjuction.youtubeapi.api.APIService
import com.codingjuction.youtubeapi.models.YoutubeResponse
import com.codingjuction.youtubeapi.utils.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val TAG = "SearchFragment"

    private var lastSearched = ""
    private  var lastToken: String? = ""

    lateinit var mrecyclerview: RecyclerView

    lateinit var youtubeAdapter: YoutubeAdapter

    lateinit var searchField: EditText

    lateinit var search_btn: Button

    lateinit var loadMore: Button

    lateinit var mProgressbar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchField = findViewById(R.id.metSearch)
        mrecyclerview = findViewById(R.id.mvideoSearch)
        search_btn = findViewById(R.id.searchbtn)
        mProgressbar = findViewById(R.id.msearch_progress)
        loadMore = findViewById(R.id.more)
        setupRecyclerView()

        search_btn.setOnClickListener {
            val search_term = searchField.text.toString()
            if(search_term.isNotEmpty()) {
                search(search_term, false)
            }
        }

        loadMore.setOnClickListener {
            val search_term = searchField.text.toString()
            if(search_term.isNotEmpty()) {
                search(search_term, true)
            }
        }

    }

    private fun search(query: String, more: Boolean) {
        var query = query
        mProgressbar.visibility = View.VISIBLE
        var searchType = "video"
        if (query != null) {
            if (query.startsWith("#")) {
                searchType = "video"
                query = query.substring(1)
            } else if (query.startsWith("@")) {
                searchType = "channel"
                query = query.substring(1)
            }
        }
        if (!more) {
            lastSearched = query
            lastToken = ""
        }
        val youtubeResponseCall: Call<YoutubeResponse> = APIService.youtubeApi.searchVideo(
            query,
            searchType,
            Constant.GOOGLE_API_KEY,
           //
            "snippet,id,statistics",
            "10",
            lastToken
        )!!
        youtubeResponseCall.enqueue(object : Callback<YoutubeResponse?> {
            override fun onResponse(call: Call<YoutubeResponse?>, response: Response<YoutubeResponse?>) {
                mProgressbar.visibility = View.GONE
                if (response.isSuccessful) {
                    val body = response.body()!!
                    val items = body.items
                    lastToken = body.nextPageToken
                    if (more) {
                        youtubeAdapter.addAll(items)
                    } else {
                        youtubeAdapter.replaceWith(items)
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<YoutubeResponse?>, t: Throwable) {
                mProgressbar.visibility = View.GONE
                Log.e(TAG, "onFailure: ", t)
            }
        })
    }

    private fun setupRecyclerView() {
        youtubeAdapter = YoutubeAdapter(this)
        mrecyclerview.apply {
            adapter = youtubeAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}