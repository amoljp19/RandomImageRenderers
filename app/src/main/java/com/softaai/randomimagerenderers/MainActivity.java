package com.softaai.randomimagerenderers;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pedrogomez.renderers.AdapteeCollection;
import com.pedrogomez.renderers.RVRendererAdapter;
import com.pedrogomez.renderers.RendererBuilder;
import com.softaai.randomimagerenderers.model.RandomImageCollectionGenerator;
import com.softaai.randomimagerenderers.model.RandomImageResponse;
import com.softaai.randomimagerenderers.remote.ResponseManager;
import com.softaai.randomimagerenderers.renderers.RemovableImageRenderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final int IMAGE_COUNT = 30;
    @Nullable
    @BindView(R.id.rv_renderers)
    RecyclerView recyclerView;

    private RVRendererAdapter<RandomImageResponse> adapter;

    RandomImageCollectionGenerator randomImageCollectionGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindButterKnife();
        initAdapter();
        initRecyclerView();
        updateView();
    }

    private void updateView(){
        ResponseManager responseManager = new ResponseManager();
        responseManager.getRandomImages(new ResponseManager.ApiCallback<RandomImageResponse>() {
            @Override
            public void success(List<RandomImageResponse> response) {
                RandomImageCollectionGenerator.RANDOM_IMAGE_INFO.clear();
                for(RandomImageResponse randomImageResponse : response){
                    RandomImageCollectionGenerator.RANDOM_IMAGE_INFO.put(randomImageResponse.getAuthor(), randomImageResponse.getDownloadUrl());
                }

                  adapter.diffUpdate(response);

                System.out.println(response);
            }

            @Override
            public void failure(int responseCode) {
                System.out.println(responseCode);
            }
        });
    }



    /**
     * Initialize RVRendererAdapter
     */
    private void initAdapter() {
        randomImageCollectionGenerator =
                new RandomImageCollectionGenerator();
        final AdapteeCollection<RandomImageResponse> randomImageCollection =
                randomImageCollectionGenerator.generateListAdapteeRandomImageCollection(IMAGE_COUNT);
        RendererBuilder<RandomImageResponse> rendererBuilder = new RendererBuilder<RandomImageResponse>().withPrototype(
                new RemovableImageRenderer(new RemovableImageRenderer.Listener() {
                    @Override public void onRemoveButtonTapped(RandomImageResponse randomImageResponse) {
                        ArrayList<RandomImageResponse> clonedList =
                                new ArrayList<>((Collection<? extends RandomImageResponse>) randomImageCollection);
                        clonedList.remove(randomImageResponse);
                        adapter.diffUpdate(clonedList);
                    }
                })).bind(RandomImageResponse.class, RemovableImageRenderer.class);

        adapter = new RVRendererAdapter<>(rendererBuilder, randomImageCollection);
    }

    /**
     * Initialize ListVideo with our RVRendererAdapter.
     */
    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void bindButterKnife(){
        ButterKnife.bind(this);
    }

}
