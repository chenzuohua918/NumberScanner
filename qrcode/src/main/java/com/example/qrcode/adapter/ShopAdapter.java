package com.example.qrcode.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qrcode.R;
import com.example.qrcode.bean.Shop;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    private Context mContext;
    private List<Shop> mShopList;

    public ShopAdapter(Context context, List<Shop> shopList) {
        this.mContext = context;
        this.mShopList = shopList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_shop,viewGroup, false);
        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Shop shop = mShopList.get(i);
        viewHolder.mItemShop.setText(shop.shopname);
        viewHolder.mItemPrice.setText(shop.price);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mShopList == null ? 0 : mShopList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mItemShop;
        private TextView mItemPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemShop = itemView.findViewById(R.id.item_shop);
            mItemPrice = itemView.findViewById(R.id.item_price);
        }
    }

    public void setData(List<Shop> shopList) {
        this.mShopList = shopList;
        notifyDataSetChanged();
    }
}
