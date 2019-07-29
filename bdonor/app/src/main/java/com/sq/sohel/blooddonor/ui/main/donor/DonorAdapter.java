package com.sq.sohel.blooddonor.ui.main.donor;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.sq.sohel.blooddonor.data.model.others.DonorCardData;
import com.sq.sohel.blooddonor.data.model.others.NameValuePair;
import com.sq.sohel.blooddonor.databinding.ItemDonorEmptyViewBinding;
import com.sq.sohel.blooddonor.databinding.ItemDonorViewBinding;
import com.sq.sohel.blooddonor.ui.base.BaseViewHolder;
import com.sq.sohel.blooddonor.ui.becomeDonor.BecomeDonorActivity;
import com.sq.sohel.blooddonor.ui.becomeDonor.becomeHero.HeroViewModel;
import com.sq.sohel.blooddonor.ui.main.MainActivity;
import com.sq.sohel.blooddonor.utils.AppLogger;
import com.sq.sohel.blooddonor.utils.AppUtils;
import com.sq.sohel.blooddonor.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.sq.sohel.blooddonor.utils.AppConstants.BLOOD_GROUP_LIST;


public class DonorAdapter extends RecyclerView.Adapter<BaseViewHolder> implements Filterable {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    private List<DonorCardData> mDonorResponseList;
    private List<DonorCardData> mDonorListOriginal;

    private DonorAdapterListener mListener;
    private ItemFilter mFilter = new ItemFilter();
    AppUtils.SearchType mSearchType = AppUtils.SearchType.Simpple_Search;
    List<NameValuePair> mAdvanceSearchQuery = null;

    public DonorAdapter(List<DonorCardData> DonorResponseList) {
        this.mDonorResponseList = DonorResponseList;
        this.mDonorListOriginal = DonorResponseList;
    }

    @Override
    public int getItemCount() {
        if (mDonorResponseList != null && mDonorResponseList.size() > 0) {
            return mDonorResponseList.size();
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mDonorResponseList != null && !mDonorResponseList.isEmpty()) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                ItemDonorViewBinding DonorViewBinding = ItemDonorViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new DonorViewHolder(DonorViewBinding);
            case VIEW_TYPE_EMPTY:
            default:
                ItemDonorEmptyViewBinding emptyViewBinding = ItemDonorEmptyViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new EmptyViewHolder(emptyViewBinding);
        }
    }


    public void addItems(List<DonorCardData> donorList) {
        mDonorResponseList.addAll(donorList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mDonorResponseList.clear();
    }

    public void setListener(DonorAdapterListener listener) {
        this.mListener = listener;
    }

    public void setFilterType(List<NameValuePair> query, AppUtils.SearchType searchType) {
        mSearchType = searchType;
        mAdvanceSearchQuery = query;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            final ArrayList<DonorCardData> nlist = new ArrayList<>();
            final List<DonorCardData> list = mDonorListOriginal;//.stream().filter(d->d.getAddress().contains(filterString.toLowerCase()));
            String[] filterStringArray;
            if (mSearchType == AppUtils.SearchType.Advance_Search && mAdvanceSearchQuery.size() > 0) {
                for (DonorCardData d : list) {
                    boolean shouldBreak = false;
                    int criteriaMatch = 0;
                    for (NameValuePair bnp : mAdvanceSearchQuery) {
                        switch (bnp.getName()) {
                            case "BloodType":
                                if (d.getBloodType().toLowerCase().equals(bnp.getValue().toLowerCase())) {
                                    //nlist.add(d);
                                    //shouldBreak = true;
                                    ++criteriaMatch;
                                    break;
                                    //temp = d;
                                }
                                break;
//                            case "Gender":
//                                if (d == null) break;
//                                if (d.getGender().toLowerCase().equals(bnp.getValue().toLowerCase())) {
//                                    nlist.add(d);
//                                    shouldBreak = true;
//                                    break;
//                                    //temp = d;
//                                }
//                                break;
                            case "Address":
                                filterStringArray = bnp.getValue().toLowerCase().split("[\\s+]");
                                if (filterStringArray != null && filterStringArray.length > 0) {
                                    for (String s : filterStringArray) {
                                        shouldBreak = false;
                                        if (d.getFullAddress().toLowerCase().contains(s) && !shouldBreak) {
                                            //nlist.add(d);
                                            ++criteriaMatch;
                                            shouldBreak = true;
                                            break;
                                        }
                                    }
                                }
                                break;
                            case "City":
                                if (d.getCity().toLowerCase().equals(bnp.getValue().toLowerCase())) {
                                    //nlist.add(d);
                                    ++criteriaMatch;
                                    shouldBreak = true;
                                    break;
                                    //temp = d;
                                }
                                break;
                            case "Country":
                                if (d.getCountry().toLowerCase().equals(bnp.getValue().toLowerCase())) {
                                    //nlist.add(d);
                                    ++criteriaMatch;
                                    shouldBreak = true;
                                    break;
                                    //temp = d;
                                }
                                break;
                            case "Age":
                                filterStringArray = bnp.getValue().toLowerCase().split(";");
                                if (filterStringArray != null && filterStringArray.length == 2) {
                                    if (Integer.valueOf(d.getAge()) >= Integer.valueOf(filterStringArray[0]) && Integer.valueOf(d.getAge()) <= Integer.valueOf(filterStringArray[1])) {
                                        //nlist.add(d);
                                        ++criteriaMatch;
                                        shouldBreak = true;
                                        break;
                                    }
                                }
                                break;
                        }
//                        if (shouldBreak) {
//                            break;
//                        }
//                        if (criteriaMatch == mAdvanceSearchQuery.size()) {
//                            break;
//                        }
                    }
                    if (criteriaMatch == mAdvanceSearchQuery.size()) {
                        nlist.add(d);
                    }
                }

            } else {
                filterStringArray = constraint.toString().toLowerCase().split("\\s+");
                if (filterStringArray != null && filterStringArray.length > 0) {
                    for (DonorCardData d : list) {
                        for (String s : filterStringArray) {
                            if (CommonUtils.contains2(BLOOD_GROUP_LIST, s.toUpperCase())) {
                                if (d.getBloodType().toLowerCase().equals(s.toLowerCase())) {
                                    nlist.add(d);
                                    break;
                                }
                            } else {
                                if (d.getBloodType().toLowerCase().contains(s) ||
                                        d.getGender().toLowerCase().contains(s) ||
                                        d.getContactNumber().toLowerCase().contains(s) ||
                                        d.getEmail().toLowerCase().contains(s) ||
                                        d.getAge().toString().toLowerCase().contains(s) ||
                                        d.getName().toLowerCase().contains(s) ||
                                        d.getFullAddress().toLowerCase().contains(s)) {
                                    nlist.add(d);
                                    break;
                                }
                            }

                        }
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = nlist;
            results.count = nlist.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mDonorResponseList = (List<DonorCardData>) results.values;
            notifyDataSetChanged();
        }
    }

    public interface DonorAdapterListener {

        void onRetryClick();

        void onSync(String donorId);

        void onEdit(String donorId);
    }

    public class DonorViewHolder extends BaseViewHolder implements DonorItemViewModel.DonorItemViewModelListener {

        private ItemDonorViewBinding mBinding;

        private DonorItemViewModel mDonorItemViewModel;

        public DonorViewHolder(ItemDonorViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            final DonorCardData donor = mDonorResponseList.get(position);
            mDonorItemViewModel = new DonorItemViewModel(donor, this);
            mBinding.setViewModel(mDonorItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings();
        }

        @Override
        public void onItemClick(String DonorUrl) {
//            if (DonorUrl != null) {
//                try {
//                    Intent intent = new Intent();
//                    intent.setAction(Intent.ACTION_VIEW);
//                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
//                    intent.setData(Uri.parse(DonorUrl));
//                    itemView.getContext().startActivity(intent);
//                } catch (Exception e) {
//                    AppLogger.d("url error");
//                }
//            }
        }

        @Override
        public void onSync(String donorId) {

            mListener.onSync(donorId);
        }

        @Override
        public void onEdit(String donorEmail) {

            try {
                Intent intent = new Intent();
                //intent.setAction(Intent.ACTION_VIEW);
                //intent.addCategory(Intent.CATEGORY_BROWSABLE);
                //intent.setData(Uri.parse(DonorUrl));
                itemView.getContext().startActivity(BecomeDonorActivity.newIntent(itemView.getContext(), donorEmail));
            } catch (Exception e) {
                AppLogger.d("url error");
            }
            //mListener.onEdit(donorId);
        }
    }

    public class EmptyViewHolder extends BaseViewHolder implements DonorEmptyItemViewModel.DonorEmptyItemViewModelListener {

        private ItemDonorEmptyViewBinding mBinding;

        public EmptyViewHolder(ItemDonorEmptyViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            DonorEmptyItemViewModel emptyItemViewModel = new DonorEmptyItemViewModel(this);
            mBinding.setViewModel(emptyItemViewModel);
        }

        @Override
        public void onRetryClick() {
            mListener.onRetryClick();
        }
    }
}