package com.davidlin54.shopicruitsummer2018.views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.davidlin54.shopicruitsummer2018.R;
import com.davidlin54.shopicruitsummer2018.models.Product;
import com.davidlin54.shopicruitsummer2018.models.ProductVariant;
import com.davidlin54.shopicruitsummer2018.presenters.ViewProductPresenter;
import com.davidlin54.shopicruitsummer2018.presenters.ViewProductPresenterImpl;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

public class ViewProductFragment extends Fragment implements ViewProductView {
    private static final String ARG_PRODUCT_ID = "productID";

    private long mProductID;

    private ViewProductPresenter mPresenter;

    private TextView mTitleTextView;
    private TextView mPriceTextView;
    private TextView mDescriptionTextView;
    private TextView mWeightTextView;
    private TextView mVariantLabelTextView;
    private TextView mSpecificationsLabelTextView;
    private TextView mDescriptionLabelTextView;
    private ImageView mImageView;
    private Spinner mVariantSpinner;

    public ViewProductFragment() {
        mPresenter = new ViewProductPresenterImpl(this);
    }

    public static ViewProductFragment newInstance(long productID) {
        ViewProductFragment fragment = new ViewProductFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PRODUCT_ID, productID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProductID = getArguments().getLong(ARG_PRODUCT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_product, container, false);
        mTitleTextView = (TextView) view.findViewById(R.id.tvTitle);
        mPriceTextView = (TextView) view.findViewById(R.id.tvPrice);
        mDescriptionTextView = (TextView) view.findViewById(R.id.tvDescription);
        mWeightTextView = (TextView) view.findViewById(R.id.tvWeight);
        mImageView = (ImageView) view.findViewById(R.id.ivImage);
        mVariantSpinner = (Spinner) view.findViewById(R.id.spVariant);
        mVariantLabelTextView = (TextView) view.findViewById(R.id.tvVariantLabel);
        mSpecificationsLabelTextView = (TextView) view.findViewById(R.id.tvSpecificationsLabel);
        mDescriptionLabelTextView = (TextView) view.findViewById(R.id.tvDescriptionLabel);
        mVariantSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mPresenter.onVariantSelected(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // fetch product from backend
        mPresenter.fetchProduct(mProductID);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void updateProductView(Product product) {
        mTitleTextView.setText(product.getTitle());
        mDescriptionTextView.setText(Html.fromHtml(product.getDescription()));

        // check if item has image, if so grab first
        if (product.getImages() != null && product.getImages().size() > 0) {
            Picasso.with(getContext())
                    .load(product.getImages().get(0).getImageSrc())
                    .into(mImageView);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        for (ProductVariant variant : product.getVariants()) {
            adapter.add(variant.getTitle());
        }
        mVariantSpinner.setAdapter(adapter);
        mVariantSpinner.setSelection(0);

        // change labels to visible
        mDescriptionLabelTextView.setVisibility(View.VISIBLE);
        mSpecificationsLabelTextView.setVisibility(View.VISIBLE);
        mVariantLabelTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void changeVariantDisplayed(ProductVariant variant) {
        mPriceTextView.setText(String.format(getResources().getString(R.string.item_price), NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(variant.getPrice())));
        mWeightTextView.setText(String.format(getResources().getString(R.string.item_weight), variant.getWeight() + " " + variant.getWeightUnit()));
    }
}
