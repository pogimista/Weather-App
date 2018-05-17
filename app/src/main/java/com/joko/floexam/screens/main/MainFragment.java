package com.joko.floexam.screens.main;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.joko.floexam.R;
import com.joko.floexam.model.WeatherResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    //View
    private View view;
    @BindView(R.id.rv_weathers) RecyclerView rvWeathers;
    @BindView(R.id.button_refresh) Button btnRefresh;

    //Helper
    private MainActivity activity;

    //Data
    private WeatherAdapter adapter;
    private List<WeatherResponse> weatherResponses;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        ButterKnife.bind(this,view);

        init();
        return view;
    }

    private void init(){
        initView();
        initData();
    }

    private void initView(){
        rvWeathers.setLayoutManager(new LinearLayoutManager(rvWeathers.getContext()));
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.getLocation();
            }
        });
    }

    private void initData(){
        adapter = new WeatherAdapter(getActivity(),weatherResponses);
        rvWeathers.setAdapter(adapter);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
        weatherResponses = activity.weatherResponses;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter!=null){
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onRefresh() {

    }
}
