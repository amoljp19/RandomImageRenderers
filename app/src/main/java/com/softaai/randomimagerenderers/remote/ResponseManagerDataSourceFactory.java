package com.softaai.randomimagerenderers.remote;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.softaai.randomimagerenderers.model.RandomImageResponse;

/**
 * Created by Amol Pawar on 19-05-2019.
 * softAai Apps
 */
public class ResponseManagerDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, RandomImageResponse>> itemLiveDataSource = new MutableLiveData<>();


    @Override
    public DataSource<Integer, RandomImageResponse> create() {
        ResponseManager itemDataSource = new ResponseManager();
        itemLiveDataSource.postValue(itemDataSource);
        return itemDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, RandomImageResponse>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}
