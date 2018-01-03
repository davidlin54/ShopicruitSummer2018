package com.davidlin54.shopicruitsummer2018.views;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.davidlin54.shopicruitsummer2018.presenters.ProductListPresenter;
import com.davidlin54.shopicruitsummer2018.presenters.ProductListPresenterImpl;
import com.davidlin54.shopicruitsummer2018.R;
import com.davidlin54.shopicruitsummer2018.models.Product;
import com.davidlin54.shopicruitsummer2018.models.Products;

import java.util.ArrayList;
import java.util.List;

public class ProductListFragment extends Fragment implements ProductListView{

    private OnProductClickListener mListener;
    private ProductListPresenter mPresenter;
    private ProductRecyclerViewAdapter mAdapter;
    private List<Product> mProducts;

    public ProductListFragment() {
        mPresenter = new ProductListPresenterImpl(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        mProducts = new ArrayList<>();
        mAdapter = new ProductRecyclerViewAdapter(mProducts, mListener);
        recyclerView.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        // do not auto measure item size so that full width is clickable
        linearLayoutManager.setAutoMeasureEnabled(false);
        recyclerView.setLayoutManager(linearLayoutManager);

        // add divider
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        // get searchview
        SearchView searchView = (SearchView) view.findViewById(R.id.svSearch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mAdapter.getFilter().filter(s);
                return true;
            }
        });

        mPresenter.fetchProducts();

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProductClickListener) {
            mListener = (OnProductClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void updateProductList(Products products) {
        mProducts.addAll(products.getProducts());
        mAdapter.notifyDataSetChanged();
    }

    public interface OnProductClickListener {
        void onProductClick(long itemId);
    }
}
