package com.softaai.randomimagerenderers;

import android.media.Image;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.softaai.randomimagerenderers.model.RandomImageResponse;
import com.softaai.randomimagerenderers.remote.ResponseManager;
import com.softaai.randomimagerenderers.remote.ResponseManagerDataSourceFactory;

/**
 * Created by Amol Pawar on 19-05-2019.
 * softAai Apps
 */
public class MainViewModel extends ViewModel {

    public LiveData<PagedList<RandomImageResponse>> itemPagedList;
    public LiveData<PageKeyedDataSource<Integer, RandomImageResponse>> liveDataSource;

    public MainViewModel() {
        ResponseManagerDataSourceFactory itemDataSourceFactory = new ResponseManagerDataSourceFactory();
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource();

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(ResponseManager.LIMIT).build();

        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, pagedListConfig))
                .build();
    }

}
