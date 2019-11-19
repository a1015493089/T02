package com.sp.sp01.provider;

import com.alibaba.fastjson.JSON;
import com.sp.sp01.dto.AccessTokenDTO;
import com.sp.sp01.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class GithubProvider {
    public  String getAccessToken(AccessTokenDTO accessTokenDTO){
         MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String s=response.body().string();
            System.out.println(s);
            String[] split = s.split("&");
            String[] split1 = split[0].split("=");
            System.out.println(split1[1]);
            return split1[1];
        } catch (Exception e) {
               e.printStackTrace();
        }
        return  null;

    }
    public GithubUser getUser(String accessToken)  {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String s=response.body().string();
            GithubUser githubUser = JSON.parseObject(s, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
                 }
        return  null;
    }
}