package com.nazmul.assessmentapphpl.repository;

import static com.nazmul.assessmentapphpl.api_client.RetrofitClient.GET_RETROFIT_CLIENT;

import com.nazmul.assessmentapphpl.api_config.ApiConfig;
import com.nazmul.assessmentapphpl.model.Registration;

import io.reactivex.Single;

public class RegistrationRepository {

    public Single<String> getRegistration(Registration registration) {
        ApiConfig apiConfig = GET_RETROFIT_CLIENT().create(ApiConfig.class);
        return apiConfig.userLogIn(registration);
    }
}
