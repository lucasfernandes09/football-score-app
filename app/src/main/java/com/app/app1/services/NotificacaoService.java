package com.app.app1.services;

import com.app.app1.model.notificacao.NotificacaoDados;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NotificacaoService {

    @Headers({
            "Authorization:key=AAAA0hNKTyg:APA91bG8YRaYYiBusz-8QF-V96ggVCirutp48R1jsRooK69p_Xg2vtgE9NUj8GyDltPPu0ici1tuLgjsDutWJfWeBc04iu94qs1Gq96VHJUAcx62ysFu2Bob12V_ePlw4-fjrnIzArWl",
            "Content-Type:application/json"
    })
    @POST("send")
    Call<NotificacaoDados> enviarNotificacao(@Body NotificacaoDados notificacaoDados);
}
