package com.example.qrcode.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qrcode.R;
import com.example.qrcode.bean.ExpressDynamic;

import java.util.List;

public class ExpressDynamicAdapter extends RecyclerView.Adapter<ExpressDynamicAdapter.ViewHolder> {
    private Activity mActivity;
    private List<ExpressDynamic> data;

    public ExpressDynamicAdapter(Activity activity, List<ExpressDynamic> data) {
        this.mActivity = activity;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mActivity).inflate(R.layout.item_express_dynamic, viewGroup, false);
        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ExpressDynamic dynamic = data.get(i);
        if (dynamic.date.contains(" ")) {
            String[] splits = dynamic.date.split(" ");
            if (splits.length > 1) {
                viewHolder.mItemTime.setText(splits[1]);
                viewHolder.mItemDate.setText(splits[0]);
            } else if (splits.length == 1) {
                viewHolder.mItemTime.setText(splits[0]);
            }
        }
        viewHolder.mItemDynamic.setText(dynamic.dynamic);

        if (i == 0) {
            viewHolder.mItemTime.setTextColor(mActivity.getColor(R.color.color_222));
            viewHolder.mItemDate.setTextColor(mActivity.getColor(R.color.color_939393));
            viewHolder.mItemDynamic.setTextColor(mActivity.getColor(R.color.color_494949));

            int radius = mActivity.getResources().getDimensionPixelOffset(R.dimen.express_dynamic_dot_red_radius);
            viewHolder.mItemDot.setWidth(radius);
            viewHolder.mItemDot.setHeight(radius);
            viewHolder.mItemDot.setBackgroundResource(R.drawable.shape_dot_red);

            viewHolder.mLineTop.setVisibility(View.INVISIBLE);
            viewHolder.mLineBottom.setVisibility(View.VISIBLE);
        } else {
            int color = mActivity.getColor(R.color.color_c2c2c2);
            viewHolder.mItemTime.setTextColor(color);
            viewHolder.mItemDate.setTextColor(color);
            viewHolder.mItemDynamic.setTextColor(color);

            int radius = mActivity.getResources().getDimensionPixelOffset(R.dimen.express_dynamic_dot_gray_radius);
            viewHolder.mItemDot.setWidth(radius);
            viewHolder.mItemDot.setHeight(radius);
            viewHolder.mItemDot.setBackgroundResource(R.drawable.shape_dot_gray);

            if (i == getItemCount() - 1) {
                viewHolder.mLineTop.setVisibility(View.VISIBLE);
                viewHolder.mLineBottom.setVisibility(View.INVISIBLE);
            } else {
                viewHolder.mLineTop.setVisibility(View.VISIBLE);
                viewHolder.mLineBottom.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mItemTime, mItemDate, mItemDot, mItemDynamic;
        private View mLineTop, mLineBottom;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemTime = itemView.findViewById(R.id.item_time);
            mItemDate = itemView.findViewById(R.id.item_date);
            mItemDot = itemView.findViewById(R.id.item_dot);
            mItemDynamic = itemView.findViewById(R.id.item_dynamic);
            mLineTop = itemView.findViewById(R.id.line_top);
            mLineBottom = itemView.findViewById(R.id.line_bottom);
        }
    }
}
