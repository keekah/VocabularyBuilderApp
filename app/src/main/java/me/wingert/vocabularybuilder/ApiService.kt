package me.wingert.vocabularybuilder

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.lang.reflect.Type

// vocabularybuilder.westus.cloudapp.azure.com
// 23.99.68.178
private const val BASE_URL = "http://192.168.1.254:8080/"
private const val AUTHORIZATION = "Authorization"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ApiService {

    @GET("vocabulary-words")
    suspend fun getVocabularyWords(@Header(AUTHORIZATION) token: String): List<VocabWord>

    @DELETE("vocabulary-words")
    suspend fun deleteWord(@Header(AUTHORIZATION) token: String, @Query(value = "word") word: String)

    @Headers("Content-Type: application/json")
    @PUT("vocabulary-words")
    suspend fun updateWord(@Header(AUTHORIZATION) token: String, @Body vocabWord: NetworkVocabWord) : VocabWord
}

object Api {
    val retrofitService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}