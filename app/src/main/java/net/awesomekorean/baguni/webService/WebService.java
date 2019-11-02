package net.awesomekorean.baguni.webService;

import net.awesomekorean.baguni.collection.CollectionEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface WebService {
    @GET("users/email/{userEmail}")
    Call<User> getUserByEmail(@Path("userEmail") String email);

    @POST("users")
    Call<User> createUser(@Body User user);

    @PATCH("users/login/{userEmail}/{userPassword}")
    Call<User> logInCheck(@Path("userEmail") String email, @Path("userPassword") String password);

    @GET("collections/{userId}/{itemId}")
    Call<Integer> getItem(@Path("userId") int userId, @Path("itemId") String itemId);

    @POST("collections")
    Call<List<Collection>> uploadItems(@Body List<Collection> collections);

    @GET("collections/{dateLastSync}")
    Call<List<CollectionEntity>> sendSyncDates(@Path("dateLastSync") String dateLastSync);

    @PUT("collections")
    Call<List<Collection>> updateItems(@Body List<Collection> collections);


}
