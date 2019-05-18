package com.softaai.randomimagerenderers;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.pedrogomez.renderers.AdapteeCollection;
import com.pedrogomez.renderers.RVRendererAdapter;
import com.pedrogomez.renderers.RendererBuilder;
import com.softaai.randomimagerenderers.model.RandomImageCollectionGenerator;
import com.softaai.randomimagerenderers.model.RandomImageResponse;
import com.softaai.randomimagerenderers.remote.RandomImagesService;
import com.softaai.randomimagerenderers.remote.ResponseManager;
import com.softaai.randomimagerenderers.renderers.ItemAdapter;
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

    @Nullable
    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout shimmerViewContainer;




    // private RVRendererAdapter<RandomImageResponse> adapter1;

    private ItemAdapter adapter;


    RandomImageCollectionGenerator randomImageCollectionGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindButterKnife();
        //initAdapter();
        initRecyclerView();
        initViewModel();
        //initRecyclerView();
//        updateView();
    }

    private void initViewModel(){
        MainViewModel itemViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        adapter = new ItemAdapter(this);
        showLoadingIndicator(true);
        itemViewModel.itemPagedList.observe(this, new Observer<PagedList<RandomImageResponse>>() {
            @Override
            public void onChanged(@Nullable final PagedList<RandomImageResponse> items) {

                if (shimmerViewContainer.getVisibility() == View.VISIBLE) {
                    showLoadingIndicator(false);
                }
                recyclerView.setVisibility(View.VISIBLE);
                adapter.submitList(items);

            }
        });

        recyclerView.setAdapter(adapter);

    }


    public void showLoadingIndicator(boolean active) {
        if (active) {
            shimmerViewContainer.setVisibility(View.VISIBLE);
            shimmerViewContainer.startShimmerAnimation();
        } else {

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    shimmerViewContainer.stopShimmerAnimation();
                    shimmerViewContainer.setVisibility(View.GONE);
                }
            }, 2000);

        }
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
//        randomImageCollectionGenerator =
//                new RandomImageCollectionGenerator();
//        final AdapteeCollection<RandomImageResponse> randomImageCollection =
//                randomImageCollectionGenerator.generateListAdapteeRandomImageCollection(IMAGE_COUNT);
//        RendererBuilder<RandomImageResponse> rendererBuilder = new RendererBuilder<RandomImageResponse>().withPrototype(
//                new RemovableImageRenderer(new RemovableImageRenderer.Listener() {
//                    @Override public void onRemoveButtonTapped(RandomImageResponse randomImageResponse) {
//                        ArrayList<RandomImageResponse> clonedList =
//                                new ArrayList<>((Collection<? extends RandomImageResponse>) randomImageCollection);
//                        clonedList.remove(randomImageResponse);
//                        adapter.diffUpdate(clonedList);
//                    }
//                })).bind(RandomImageResponse.class, RemovableImageRenderer.class);

        //adapter = new RVRendererAdapter<>(rendererBuilder, randomImageCollection);
        adapter = new ItemAdapter(this);
        //adapter = new ItemAdapter(rendererBuilder, randomImageCollection);
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
