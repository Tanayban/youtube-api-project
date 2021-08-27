package com.codingjuction.youtubeapi.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class YoutubeResponse: Serializable {
    val kind: String? = null
    val etag: String? = null
    val nextPageToken: String? = null
    val pageInfo: PageInfo? = null
    val items: List<Item> = ArrayList()

    class PageInfo {
        val totalResults = 0
        val resultsPerPage = 0
    }

    class Item {
        val kind: String? = null
        val etag: String? = null
        val snippet: Snippet? = null
        val id: Id? = null
        val statistics: Statistics? = null


        class Id {
            val kind: String? = null
            val videoId: String? = null
        }

        class Statistics {
            val viewCount: String? = null
            val likeCount: String? = null
        }

        class Snippet {
            val likeCount: String? = null
            val publishedAt: String? = null
            val channelId: String? = null
            val title: String? = null
            val description: String? = null
            val channelTitle: String? = null
            val categoryId: String? = null
            val liveBroadcastContent: String? = null
            val thumbnails: Thumbnails? = null
            val tags: List<String> = ArrayList()

            class Thumbnails {
                @SerializedName("default")
                val default: Default? = null

                class Default {
                    val url: String? = null
                    val width = 0
                    val height = 0
                }
            }
        }
    }
}