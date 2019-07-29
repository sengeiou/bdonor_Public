

package com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.requestForBlood;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.sq.sohel.blooddonor.BR;
import com.sq.sohel.blooddonor.R;
import com.sq.sohel.blooddonor.data.model.others.RequestBloodCardData;
import com.sq.sohel.blooddonor.data.model.others.NameValuePair;
import com.sq.sohel.blooddonor.databinding.FragmentRequestForBloodBinding;
import com.sq.sohel.blooddonor.ui.base.BaseFragment;
import com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.requestForBlood.RequestBloodAdapter;
import com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.requestForBlood.RequestBloodNavigator;
import com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.requestForBlood.RequestBloodViewModel;
import com.sq.sohel.blooddonor.utils.AppUtils;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

//import ValidationModel;


public class RequestBloodFragment extends BaseFragment<FragmentRequestForBloodBinding, RequestBloodViewModel>
        implements RequestBloodNavigator, RequestBloodAdapter.RequestBloodAdapterListener {

    @Inject
    RequestBloodAdapter mRequestBloodAdapter;
    FragmentRequestForBloodBinding mFragmentRequestBloodBinding;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    @Named("RequestBloodFragment")
    ViewModelProvider.Factory mViewModelFactory;
    private RequestBloodViewModel mRequestBloodViewModel;

    private String bloodType = "";
    private Boolean showOnlyMyRequest = false;

    public void setShowOnlyMyRequest(Boolean showOnlyMyRequest) {
        this.showOnlyMyRequest = showOnlyMyRequest;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    static RequestBloodFragment fragment = null;

    public static RequestBloodFragment newInstance() {
        Bundle args = new Bundle();
        fragment = new RequestBloodFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void FilterData(String query) {
        //subscribeToLiveData();
        if (query.isEmpty()) {
            //mRequestBloodViewModel.fetchRequestBloods();
        } else {
            mRequestBloodAdapter.setFilterType(null, AppUtils.SearchType.Simpple_Search);
            mRequestBloodAdapter.getFilter().filter(query);
        }
    }

    public void FilterData(List<NameValuePair> query, AppUtils.SearchType searchType) {
        //subscribeToLiveData();
        if (query.isEmpty()) {
            //mRequestBloodViewModel.fetchRequestBloods();
        } else {
            mRequestBloodAdapter.setFilterType(query, searchType);
            mRequestBloodAdapter.getFilter().filter(null);
        }
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_request_for_blood;
    }

    @Override
    public RequestBloodViewModel getViewModel() {
        mRequestBloodViewModel = ViewModelProviders.of(this, mViewModelFactory).get(RequestBloodViewModel.class);
        return mRequestBloodViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequestBloodViewModel.setNavigator(this);
        mRequestBloodAdapter.setListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("bloodType", this.bloodType);
        outState.putBoolean("showOnlyMyRequest", this.showOnlyMyRequest);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            this.bloodType = savedInstanceState.getString("bloodType");
            this.showOnlyMyRequest = savedInstanceState.getBoolean("showOnlyMyRequest");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshRequestBloodList();
    }

    @Override
    public void refreshRequestBloodList() {
        mRequestBloodViewModel.fetchRequestBloods(this.bloodType, this.showOnlyMyRequest);
    }

    @Override
    public void onRetryClick() {
        mRequestBloodViewModel.fetchRequestBloods(this.bloodType, this.showOnlyMyRequest);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentRequestBloodBinding = getViewDataBinding();
        setUp();
        subscribeToLiveData();
    }

    @Override
    public void updateRequestBlood(List<RequestBloodCardData> requestBloodList) {
        mRequestBloodAdapter.addItems(requestBloodList);
    }

    private void setUp() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentRequestBloodBinding.requestBloodRecyclerView.setLayoutManager(mLayoutManager);
        mFragmentRequestBloodBinding.requestBloodRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mFragmentRequestBloodBinding.requestBloodRecyclerView.setAdapter(mRequestBloodAdapter);
    }

    private void subscribeToLiveData() {
        mRequestBloodViewModel.getRequestBloodListLiveData().observe(this, RequestBloods -> mRequestBloodViewModel.addRequestBloodItemsToList(RequestBloods));
    }

    @Override
    public void dismissDialog() {
        if (bloodType != null) {

        }
    }

    @Override
    public void openLoginActivity() {
        getBaseActivity().openLoginActivity();
    }
}
