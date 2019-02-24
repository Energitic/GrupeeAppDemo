package rest;

import model.ApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("random")
    Call<ApiResponse> getDogRandomImage();
}
