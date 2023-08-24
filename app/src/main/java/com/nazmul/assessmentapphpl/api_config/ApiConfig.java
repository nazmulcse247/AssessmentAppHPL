package com.nazmul.assessmentapphpl.api_config;

import com.nazmul.assessmentapphpl.model.Registration;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiConfig {

    @POST("AssessmentAndroid/api/Registration")
    Single<String> userLogIn(@Body Registration registration);



}
