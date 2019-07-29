package com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.requestForBlood;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.sq.sohel.blooddonor.data.model.others.RequestBloodCardData;
import com.sq.sohel.blooddonor.data.model.others.NameValuePair;
import com.sq.sohel.blooddonor.databinding.ItemRequestForBloodEmptyViewBinding;
import com.sq.sohel.blooddonor.databinding.ItemRequestForBloodViewBinding;
import com.sq.sohel.blooddonor.ui.base.BaseViewHolder;
import com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.requestForBlood.requestBloodDialogDetails.RequestBloodDetailsDialog;
import com.sq.sohel.blooddonor.utils.AppLogger;
import com.sq.sohel.blooddonor.utils.AppUtils;
import com.sq.sohel.blooddonor.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import static com.sq.sohel.blooddonor.utils.AppConstants.BLOOD_GROUP_LIST;


public class RequestBloodAdapter extends RecyclerView.Adapter<BaseViewHolder> implements Filterable {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;
    
    private List<RequestBloodCardData> mRequestBloodResponseList;
    private List<RequestBloodCardData> mRequestBloodListOriginal;

    private RequestBloodAdapterListener mListener;
    private ItemFilter mFilter = new ItemFilter();
    AppUtils.SearchType mSearchType = AppUtils.SearchType.Simpple_Search;
    List<NameValuePair> mAdvanceSearchQuery = null;

    public RequestBloodAdapter(List<RequestBloodCardData> requestBloodCardData) {
        this.mRequestBloodResponseList = requestBloodCardData;
        this.mRequestBloodListOriginal = requestBloodCardData;
    }

    @Override
    public int getItemCount() {
        if (mRequestBloodResponseList != null && mRequestBloodResponseList.size() > 0) {
            return mRequestBloodResponseList.size();
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mRequestBloodResponseList != null && !mRequestBloodResponseList.isEmpty()) {
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
                ItemRequestForBloodViewBinding RequestBloodViewBinding = ItemRequestForBloodViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new RequestBloodViewHolder(RequestBloodViewBinding);
            case VIEW_TYPE_EMPTY:
            default:
                ItemRequestForBloodEmptyViewBinding emptyViewBinding = ItemRequestForBloodEmptyViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new EmptyViewHolder(emptyViewBinding);
        }
    }


    public void addItems(List<RequestBloodCardData> donorList) {
        mRequestBloodResponseList.addAll(donorList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mRequestBloodResponseList.clear();
    }

    public void setListener(RequestBloodAdapterListener listener) {
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
            final ArrayList<RequestBloodCardData> nlist = new ArrayList<>();
            final List<RequestBloodCardData> list = mRequestBloodListOriginal;//.stream().filter(d->d.getAddress().contains(filterString.toLowerCase()));
            String[] filterStringArray;
            if (mSearchType == AppUtils.SearchType.Advance_Search && mAdvanceSearchQuery.size() > 0) {
                for (RequestBloodCardData d : list) {
                    //RequestBloodCardData temp = d;
                    for (NameValuePair bnp : mAdvanceSearchQuery) {
                        switch (bnp.getName()) {
                            case "BloodType":
                                if (d == null) break;
                                if (d.getBloodType().toLowerCase().equals(bnp.getValue().toLowerCase())) {
                                    //nlist.add(d);
                                    //break;
                                    //temp = d;
                                } else {
                                    d = null;
                                }
                                break;
                            case "Gender":
//                                if (d == null) break;
//                                if (d.getGender().toLowerCase().equals(bnp.getValue().toLowerCase())) {
////                                    nlist.add(d);
////                                    break;
//                                    //temp = d;
//                                } else {
//                                    d = null;
//                                }
                                break;
                            case "Address":
                                if (d == null) break;
                                filterStringArray = bnp.getValue().toLowerCase().split("[\\s+]");
                                if (filterStringArray != null && filterStringArray.length > 0) {
                                    for (String s : filterStringArray) {
                                        if (d.getFullAddress().toLowerCase().contains(s)) {
                                            //nlist.add(d);
                                            //break;
                                        } else {
                                            d = null;
                                            break;
                                        }
                                    }
                                }
                                break;
                            case "Age":
                                if (d == null) break;
//                                filterStringArray = bnp.getValue().toLowerCase().split(";");
//                                if (filterStringArray != null && filterStringArray.length == 2) {
//                                    if (Integer.valueOf(d.getAge()) >= Integer.valueOf(filterStringArray[0]) && Integer.valueOf(d.getAge()) <= Integer.valueOf(filterStringArray[1])) {
//                                        //nlist.add(d);
//                                        //break;
//                                    } else {
//                                        d = null;
//                                        break;
//                                    }
//                                }

                                break;
                        }
                    }
                    if (d != null) {
                        nlist.add(d);
                    }
                }

            } else {
                filterStringArray = constraint.toString().toLowerCase().split("\\s+");
                if (filterStringArray != null && filterStringArray.length > 0) {
                    for (RequestBloodCardData d : list) {
                        for (String s : filterStringArray) {
                            if (CommonUtils.contains2(BLOOD_GROUP_LIST, s.toUpperCase())) {
                                if (d.getBloodType().toLowerCase().equals(s.toLowerCase())) {
                                    nlist.add(d);
                                    break;
                                }
                            } else {
//                                if (d.getBloodType().toLowerCase().contains(s) ||
//                                        d.getGender().toLowerCase().contains(s) ||
//                                        d.getContactNumber().toLowerCase().contains(s) ||
//                                        d.getEmail().toLowerCase().contains(s) ||
//                                        d.getAge().toString().toLowerCase().contains(s) ||
//                                        d.getName().toLowerCase().contains(s) ||
//                                        d.getFullAddress().toLowerCase().contains(s)) {
//                                    nlist.add(d);
//                                    break;
//                                }
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
            mRequestBloodResponseList = (List<RequestBloodCardData>) results.values;
            notifyDataSetChanged();
        }
    }

    public interface RequestBloodAdapterListener {

        void onRetryClick();
    }

    public class RequestBloodViewHolder extends BaseViewHolder implements RequestBloodItemViewModel.RequestBloodItemViewModelListener {

        private ItemRequestForBloodViewBinding mBinding;

        private RequestBloodItemViewModel mRequestBloodItemViewModel;

        public RequestBloodViewHolder(ItemRequestForBloodViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            final RequestBloodCardData requestBlood = mRequestBloodResponseList.get(position);
            mRequestBloodItemViewModel = new RequestBloodItemViewModel(requestBlood, this);
            mBinding.setViewModel(mRequestBloodItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings();
        }

        @Override
        public void onItemClick(RequestBloodItemViewModel requestBloodItemViewModel) {
            if (requestBloodItemViewModel != null) {
                try {
//                    Intent intent = new Intent();
//                    intent.setAction(Intent.ACTION_VIEW);
//                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
//                    intent.setData(Uri.parse(RequestBloodUrl));
//                    itemView.getContext().startActivity(intent);
                    RequestBloodDetailsDialog  mRequestBloodDetailsDialog = RequestBloodDetailsDialog.newInstance();
                    mRequestBloodDetailsDialog.show(((AppCompatActivity) itemView.getContext()).getSupportFragmentManager(), requestBloodItemViewModel);
                } catch (Exception e) {
                    AppLogger.d("url error");
                }
            }
        }
    }

    public class EmptyViewHolder extends BaseViewHolder implements RequestBloodEmptyItemViewModel.RequestBloodEmptyItemViewModelListener {

        private ItemRequestForBloodEmptyViewBinding mBinding;

        public EmptyViewHolder(ItemRequestForBloodEmptyViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            RequestBloodEmptyItemViewModel emptyItemViewModel = new RequestBloodEmptyItemViewModel(this);
            mBinding.setViewModel(emptyItemViewModel);
        }

        @Override
        public void onRetryClick() {
            mListener.onRetryClick();
        }
    }
}