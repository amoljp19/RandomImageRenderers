package com.softaai.randomimagerenderers.model;

import com.pedrogomez.renderers.ListAdapteeCollection;

import java.util.List;

/**
 * Created by Amol Pawar on 15-05-2019.
 * softAai Apps
 */
public class RandomImageCollection extends ListAdapteeCollection<RandomImageResponse> {

    public RandomImageCollection(List<RandomImageResponse> randomImageResponseList) {
        super(randomImageResponseList);
    }
}
