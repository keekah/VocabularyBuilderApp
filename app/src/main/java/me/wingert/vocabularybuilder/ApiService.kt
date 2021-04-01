package me.wingert.vocabularybuilder

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

private const val BASE_URL = "http://10.0.2.2:8080/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @GET("vocabulary-words")
    suspend fun getVocabularyWords(): List<VocabWord>

    @Headers("Content-Type: application/json")
    @POST("/new-word")
    suspend fun addWord(@Body newWord: NetworkVocabWord) : VocabWord

    @DELETE("/vocabulary-words")
    suspend fun deleteWord(@Query(value = "word") word: String)

    @Headers("Content-Type: application/json ")
    @PUT("/vocabulary-words")
    suspend fun updateWord(@Body vocabWord: NetworkVocabWord) : VocabWord
}

object Api {
    val retrofitService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}