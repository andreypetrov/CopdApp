package com.petrodevelopment.copdapp.network;

import com.petrodevelopment.copdapp.util.U;

import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by user on 15-05-12.
 */
public class Retrofit {

    static class Contributor {
        String login;
        int contributions;
    }

    interface GitHub {
        @GET("/repos/{owner}/{repo}/contributors")
        void contributors(
                @Path("owner") String owner,
                @Path("repo") String repo,
                Callback<List<Contributor>> cb
        );
    }

    /**
     * Initialize the retrofit rest adapter.
     */
    public static void initialize() {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("User-Agent", "Retrofit-Sample-App");
            }
        };

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.github.com")
                .setRequestInterceptor(requestInterceptor)
                .build();

        GitHub github = restAdapter.create(GitHub.class);

        // Fetch and print a list of the contributors to this library.
        github.contributors("andreypetrov", "CopdApp", new Callback<List<Contributor>>() {
            @Override
            public void success(List<Contributor> contributors, Response response) {
                for (Contributor contributor : contributors) {
                    U.log(new Retrofit(), contributor.login + " (" + contributor.contributions + ")");
                }
            }

            @Override
            public void failure(RetrofitError error) {
                    U.log(new Retrofit(), "error: " + error.getMessage());
            }
        });



    }
}
