package com.liko.baselib.mvp.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liko.baselib.R;

import java.util.List;

/**
 * Created by Liko on 17/11/29.
 */

public class InterAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    private int selectedPosition = 0;

    public InterAdapter(@Nullable List<Integer> data) {
        super(R.layout.item_inter, data);
    }

    public void changeDate(int position) {
        selectedPosition = position % getData().size();
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        if (helper.getLayoutPosition() == selectedPosition) {
            helper.getView(R.id.iv_indicator).setSelected(true);
        } else {
            helper.getView(R.id.iv_indicator).setSelected(false);
        }
    }
}
