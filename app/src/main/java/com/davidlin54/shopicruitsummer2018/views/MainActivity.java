package com.davidlin54.shopicruitsummer2018.views;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.davidlin54.shopicruitsummer2018.R;
import com.davidlin54.shopicruitsummer2018.models.Product;

public class MainActivity extends AppCompatActivity implements ProductListFragment.OnProductClickListener {

    private Fragment mProductListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProductListFragment = new ProductListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, mProductListFragment);
        transaction.commit();
    }

    @Override
    public void onProductClick(long itemId) {
        Fragment viewProductFragment = ViewProductFragment.newInstance(itemId);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, viewProductFragment);
        transaction.addToBackStack(null);
        transaction.hide(mProductListFragment);
        transaction.commit();

        showBackButton(true);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
            showBackButton(false);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showBackButton(boolean isShown) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(isShown);
    }
}
