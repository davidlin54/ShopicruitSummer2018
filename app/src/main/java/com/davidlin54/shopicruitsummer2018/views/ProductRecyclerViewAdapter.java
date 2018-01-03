package com.davidlin54.shopicruitsummer2018.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.davidlin54.shopicruitsummer2018.R;
import com.davidlin54.shopicruitsummer2018.models.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder> implements Filterable {

    private final List<Product> mValues;
    private List<Product> mFilteredValues;
    private final ProductListFragment.OnProductClickListener mListener;

    private Context mContext;

    public ProductRecyclerViewAdapter(List<Product> items, ProductListFragment.OnProductClickListener listener) {
        mValues = items;
        mFilteredValues = null;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_product, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Product product = mFilteredValues == null ? mValues.get(position) : mFilteredValues.get(position);
        holder.mItem = product;
        holder.mTitleView.setText(product.getTitle());
        holder.mDescriptionView.setText(Html.fromHtml(product.getDescription()));

        // check if item has image, if so grab first
        if (product.getImages() != null && product.getImages().size() > 0) {
            Picasso.with(mContext)
                    .load(product.getImages().get(0).getImageSrc())
                    .into(holder.mIconImageView);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onProductClick(holder.mItem.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFilteredValues == null ? mValues.size() : mFilteredValues.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<Product> filteredProducts = null;
                if (charSequence.length() == 0) {
                    filteredProducts = mValues;
                } else {
                    // filter by title and description
                    filteredProducts = new ArrayList<>();
                    for (Product product : mValues) {
                        if (product.getTitle().toLowerCase().contains(charSequence.toString().toLowerCase()) ||
                                product.getDescription().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                            filteredProducts.add(product);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredProducts;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredValues = (List<Product>) filterResults.values;
                ProductRecyclerViewAdapter.this.notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mIconImageView;
        public final TextView mTitleView;
        public final TextView mDescriptionView;
        public Product mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIconImageView = (ImageView) view.findViewById(R.id.ivIcon);
            mTitleView = (TextView) view.findViewById(R.id.tvTitle);
            mDescriptionView = (TextView) view.findViewById(R.id.tvDescription);
        }
    }
}
