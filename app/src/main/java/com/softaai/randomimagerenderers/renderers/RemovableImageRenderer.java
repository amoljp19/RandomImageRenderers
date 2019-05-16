package com.softaai.randomimagerenderers.renderers;

import com.softaai.randomimagerenderers.R;
import com.softaai.randomimagerenderers.model.RandomImageResponse;

import butterknife.OnClick;

/**
 * Created by Amol Pawar on 16-05-2019.
 * softAai Apps
 */
public class RemovableImageRenderer extends RandomImageRenderer{

    private Listener removeItemListener;

    public interface Listener {

        void onRemoveButtonTapped(RandomImageResponse randomImageResponse);
    }

    public RemovableImageRenderer(Listener removeItemListener) {
        this.removeItemListener = removeItemListener;
    }


    @OnClick(R.id.tv_date) void clickOnDelete() {
        removeItemListener.onRemoveButtonTapped(getContent());
    }

}
