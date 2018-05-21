package com.palapol.tsp_sesaminar_team02.remote;

import com.palapol.tsp_sesaminar_team02.model.ResObj;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {
    @GET("index.php/sam/Android_REST_API/get_login/{username}/{password}")
    Call<ResObj> login(@Path("username") String username, @Path("password") String password);


}
