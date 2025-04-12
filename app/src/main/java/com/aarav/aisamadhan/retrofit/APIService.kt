package com.aarav.aisamadhan.retrofit

import ImageGenerationRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface APIService {

    @Headers(
        "Authorization: Bearer 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",
        "Content-Type: application/json"
    )
    @POST("black-forest-labs/FLUX.1-dev")
    fun generateImage(@Body request: ImageGenerationRequest): Call<ResponseBody>

}