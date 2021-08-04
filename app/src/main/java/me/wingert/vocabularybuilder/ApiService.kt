package me.wingert.vocabularybuilder

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


private const val BASE_URL = "http://vocabularybuilder.westus.cloudapp.azure.com:8080/"
private const val AUTHORIZATION = "Authorization"

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