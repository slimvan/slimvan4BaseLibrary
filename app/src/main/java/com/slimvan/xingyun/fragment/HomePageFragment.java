package com.slimvan.xingyun.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.slimvan.xingyun.R;
import com.slimvan.xingyun.bean.User;
import com.slimvan.xingyun.http.ApiService;
import com.slimvan.xingyun.http.RetrofitBuilder;
import com.xingyun.slimvan.fragment.BaseFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 */
public class HomePageFragment extends BaseFragment {


    @InjectView(R.id.tv_text)
    TextView tvText;

    public HomePageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        ButterKnife.inject(this, view);

//        requestGet();
        requestPost();

        return view;
    }

    private void requestPost() {
        RetrofitBuilder.build(ApiService.class).createUser("slimvan","123456").enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.i("onResponse", response.toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("onFailure", "onFailure");
            }
        });
    }

    private void requestGet() {
        Map<String, Object> params = new HashMap<>();
        params.put("users","slimvan");
        RetrofitBuilder.build(ApiService.class).getUsers(params).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.i("onResponse", response.toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("onFailure", "onFailure");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
