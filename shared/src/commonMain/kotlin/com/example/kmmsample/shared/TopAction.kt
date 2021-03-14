package com.example.kmmsample.shared

import com.example.kmmsample.shared.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.features.json.*
import kotlinx.coroutines.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.logging.Logger

class TopAction {
    interface Callback {
        fun complete(result :QiitaItemEntity)
    }

    private lateinit var callback:Callback

    /**
     * directにアクセスできる
     */
    suspend fun mainButton():Array<QiitaItemEntity> {
        return ApiClient().get<Array<QiitaItemEntity>>(URL)
    }
    
    companion object {
        //githubはリポジトリ作るのがめんどくさいことがわかったw
      //  val URL = "https://api.github.com/users/tomotakasg/repos"
        private const val URL = "https://qiita.com/api/v2/items?page=1&per_page=20"
    }

    data class QiitaItemEntity(
        val rendered_body: String,
        val body: String,
        val coediting: Boolean,
        val comments_count: Int,
        val created_at: String,
        val id: String,
        val likes_count: Int,
        val private: Boolean,
        val reactions_count: Int,
        val title: String,
        val updated_at: String,
        val url: String,
        val page_views_count: Int?
    )
}
