package tw.teng.hw3.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface APIService {
    @Headers(
        "Content-Type:application/json",
        "Authorization:key=AAAAWyQtFuc:APA91bFuuWZsd6aRRvSHndozM_RYsniAetm9fb2jRaD8nW0n6coGwZ_Tghb8qc1z-PmNh9lRefzhphvGn68bBadd5jBkhoDMkboi33XpacQCaIY9hTf0hru6bV8AIhvro3Ymir3shO2D"
    )
    @POST("fcm/send")
    fun sendNotification(@Body body: Sender?): Call<MyResponse>
}