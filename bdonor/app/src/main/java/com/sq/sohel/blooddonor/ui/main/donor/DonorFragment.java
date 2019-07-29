

package com.sq.sohel.blooddonor.ui.main.donor;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sq.sohel.blooddonor.BR;
import com.sq.sohel.blooddonor.R;
import com.sq.sohel.blooddonor.data.model.others.DonorCardData;
import com.sq.sohel.blooddonor.data.model.others.NameValuePair;
import com.sq.sohel.blooddonor.databinding.FragmentDonorBinding;
import com.sq.sohel.blooddonor.ui.base.BaseFragment;
import com.sq.sohel.blooddonor.ui.becomeDonor.becomeHero.HeroNavigator;
import com.sq.sohel.blooddonor.ui.dialogMessage.DialogView;
import com.sq.sohel.blooddonor.ui.main.MainPagerAdapter;
import com.sq.sohel.blooddonor.ui.main.advanceSearch.AdvanceSearchDialog;
import com.sq.sohel.blooddonor.utils.AppUtils;
import com.sq.sohel.blooddonor.data.model.others.ValidationModel;


//import ValidationModel;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;


public class DonorFragment extends BaseFragment<FragmentDonorBinding, DonorViewModel>
        implements DonorNavigator, HeroNavigator, DonorAdapter.DonorAdapterListener {

    @Inject
    @Named("MainPagerAdapterDonorFragment")
    MainPagerAdapter mPagerAdapter;

    @Inject
    DonorAdapter mDonorAdapter;
    FragmentDonorBinding mFragmentDonorBinding;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    @Named("DonorFragment")
    ViewModelProvider.Factory mViewModelFactory;
    private DonorViewModel mDonorViewModel;



    static DonorFragment fragment = null;

    public static DonorFragment newInstance() {
        Bundle args = new Bundle();
        fragment = new DonorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void FilterData(String query) {
        //subscribeToLiveData();
        if (query.isEmpty()) {
            mDonorViewModel.fetchDonors();
        } else {
            mDonorAdapter.setFilterType(null, AppUtils.SearchType.Simpple_Search);
            mDonorAdapter.getFilter().filter(query);
        }
    }

    public void FilterData(List<NameValuePair> query, AppUtils.SearchType searchType) {
        //subscribeToLiveData();
        if (query.isEmpty()) {
            mDonorViewModel.fetchDonors();
        } else {
            mDonorAdapter.setFilterType(query, searchType);
            mDonorAdapter.getFilter().filter(null);
            if (searchType == AppUtils.SearchType.Advance_Search) {
                LinearLayout linearLayout = getView().findViewById(R.id.linearLayoutAfterSearchSection);
                linearLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_donor;
    }

    @Override
    public DonorViewModel getViewModel() {
        mDonorViewModel = ViewModelProviders.of(this, mViewModelFactory).get(DonorViewModel.class);
        return mDonorViewModel;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDonorViewModel.setNavigator(this);
        mDonorAdapter.setListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshDonorList();
    }

    @Override
    public void refreshDonorList() {
        LinearLayout linearLayout = getView().findViewById(R.id.linearLayoutAfterSearchSection);
        linearLayout.setVisibility(View.GONE);
        mDonorViewModel.fetchDonors();
    }

    @Override
    public void onRedefineSearch() {
        AdvanceSearchDialog mAdvanceSearchDialog = AdvanceSearchDialog.newInstance();
        mAdvanceSearchDialog.show(getFragmentManager(), mPagerAdapter);
    }

    @Override
    public void onRetryClick() {
        mDonorViewModel.fetchDonors();
    }

    @Override
    public void onSync(String donorId) {
        if (!isNetworkConnected()) {
            handleNoNetworkConnection();
        } else {
            mDonorViewModel.DonorSync(donorId);
        }
    }

    @Override
    public void onEdit(String donorId) {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentDonorBinding = getViewDataBinding();
        setUp();
        subscribeToLiveData();
    }

    @Override
    public void updateDonor(List<DonorCardData> donorList) {
        mDonorAdapter.addItems(donorList);
    }

    private void setUp() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentDonorBinding.donorRecyclerView.setLayoutManager(mLayoutManager);
        mFragmentDonorBinding.donorRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mFragmentDonorBinding.donorRecyclerView.setAdapter(mDonorAdapter);
    }

    private void subscribeToLiveData() {
        mDonorViewModel.getDonorListLiveData().observe(this, Donors -> mDonorViewModel.addDonorItemsToList(Donors));
    }

    @Override
    public void closeHero() {
        //
    }

    @Override
    public void openLoginActivity() {
        getBaseActivity().openLoginActivity();
    }
}
