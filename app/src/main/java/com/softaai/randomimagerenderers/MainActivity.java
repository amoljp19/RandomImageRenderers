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
import com.softaai.randomimagerenderers.model.RandomImageCollectionGenerator;
import com.softaai.randomimagerenderers.model.RandomImageResponse;
import com.softaai.randomimagerenderers.renderers.ItemAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.rv_renderers)
    RecyclerView recyclerView;

    @Nullable
    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout shimmerViewContainer;

    private ItemAdapter adapter;


    RandomImageCollectionGenerator randomImageCollectionGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindButterKnife();
        initRecyclerView();
        initViewModel();
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

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void bindButterKnife(){
        ButterKnife.bind(this);
    }

}
