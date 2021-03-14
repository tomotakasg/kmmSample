package com.example.kmmsample.shared


import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

/**
 * https://tech.dely.jp/entry/2020/12/11/000000
 * https://qiita.com/morimorim/items/115f495516336a9e7c4b
 */
class ApiClient {

    suspend inline fun<reified T> get(url: String, params: Map<String, Any> = mapOf()): T {
        val client = HttpClient(Android) {
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
            engine {
                connectTimeout = 100_000
                socketTimeout = 100_000
            }
        }
        val request = GlobalScope.async {
            Thread.sleep(2000) // 通信しててもUIに影響がないことを確認するためのテスト
            val response = client.get<T>(url)
            client.close()
            return@async response
        }
        return request.await()
    }


    @Serializable
    data class Repository(val name: String, @SerialName("html_url") val url: String)
}
