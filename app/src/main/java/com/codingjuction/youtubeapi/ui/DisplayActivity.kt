package com.codingjuction.youtubeapi.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.codingjuction.youtubeapi.R
import com.codingjuction.youtubeapi.utils.Constant
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class DisplayActivity : YouTubeBaseActivity() {

    lateinit var youTubePlayerView: YouTubePlayerView

    lateinit var title: TextView

    lateinit var description: TextView

    lateinit var share: Button

    lateinit var views: TextView

    private var mOnInitializedListener: YouTubePlayer.OnInitializedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)
        youTubePlayerView = findViewById(R.id.myoutube_player)
        title = findViewById(R.id.mtitle)
        description = findViewById(R.id.description)
        share = findViewById(R.id.share)
        views = findViewById(R.id.mviews)

        val bundle = intent.extras
        val url = bundle?.getString("url")
        val mtitle = bundle?.getString("title")
        val mdescription = bundle?.getString("description")
        title.text = mtitle
        description.text = mdescription
//        val viewcounts = "https://www.googleapis.com/youtube/v3/videos?part=statistics&id=$url&key=${Constant.GOOGLE_API_KEY}"

        share.setOnClickListener {
            val a = Intent(Intent.ACTION_SEND)
            val strAppLink: String
            strAppLink = try {
                "https://youtu.be/$url"
            } catch (anfe:android.content.ActivityNotFoundException) {
                "https://youtu.be/$url"
            }
            a.setType("text/link")
            val shareBody = (strAppLink)
            a.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(a, "Share Using"))
        }


        mOnInitializedListener = object : YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, p1: YouTubePlayer?, p2: Boolean) {
                p1!!.loadVideo(url)
            }

            override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
                TODO("Not yet implemented")
            }

        }
        youTubePlayerView.initialize(Constant.GOOGLE_API_KEY, mOnInitializedListener)
    }
}