package com.slimvan.xingyun.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.slimvan.xingyun.R;
import com.slimvan.xingyun.bean.Test;
import com.slimvan.xingyun.http.ApiService;
import com.slimvan.xingyun.http.HttpConfig;
import com.slimvan.xingyun.http.MSubscriber;
import com.slimvan.xingyun.http.RetrofitBuilder;
import com.xingyun.slimvan.fragment.BaseFragment;
import com.xingyun.slimvan.util.LogUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
        Map<String, Object> params = new HashMap<>();
        params.put("name", "slimvan");
        params.put("phoneNumber", "13751154240");
        RetrofitBuilder.build(ApiService.class).
                postRequest(HttpConfig.TEST, params).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new MSubscriber<String>(mContext, true, true) {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.i(TAG, s);
                    }

                    @Override
                    public void errorCallBack(Throwable e) {
                        Log.i(TAG, "error");
                    }
                });

    }



//    private void requestGet() {
//        Map<String, Object> params = new HashMap<>();
//        params.put("users", "slimvan");
//        RetrofitBuilder.build(ApiService.class).getRequest("https://api.github.com/", params).enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                Log.i("onResponse", response.toString());
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Log.i("onFailure", "onFailure");
//            }
//        });
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
