package com.joko.floexam.screens.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joko.floexam.R;
import com.joko.floexam.model.WeatherResponse;
import com.joko.floexam.screens.details.DetailActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    Context context;
    Activity activity;
    List<WeatherResponse> data;


    public WeatherAdapter(Activity activity, List<WeatherResponse> data) {
        this.activity = activity;
        context = activity.getApplicationContext();
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        this.data = data;
    }

    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_weather, parent, false);
        view.setBackgroundResource(mBackground);
        return new WeatherAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final WeatherAdapter.ViewHolder holder, final int position) {
        final WeatherResponse response = data.get(position);

        if (position%2==0){
            holder.view.setBackgroundColor(Color.parseColor("#ffffff"));
        }else {
            holder.view.setBackgroundColor(Color.parseColor("#F5F5F5"));
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDetailsActivity(response);
            }
        });
        holder.tvTitle.setText(response.getName());
        holder.tvLocation.setText(response.getCoord().toString());
        holder.tvTemperature.setText(response.getMain().toString());
        holder.tvWeather.setText(response.getWeatherSummary());
    }

    @Override
    public int getItemCount() {
        try {
            return data.size();
        } catch (Exception e) {
            return 0;
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        @BindView(R.id.txt_title) TextView tvTitle;
        @BindView(R.id.txt_weather_details)TextView tvWeather;
        @BindView(R.id.txt_weather_temp)TextView tvTemperature;
        @BindView(R.id.txt_weather_location)TextView tvLocation;

        public ViewHolder(View convertView) {
            super(convertView);
            view = convertView;
            ButterKnife.bind(this,view);
        }
    }

    private void goToDetailsActivity(WeatherResponse response){
        EventBus.getDefault().postSticky(response);
        Intent intent = new Intent(activity, DetailActivity.class);
        activity.startActivity(intent);
    }
}
