package com.example.qrcode.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qrcode.R;
import com.example.qrcode.base.BaseActivity;
import com.example.qrcode.bean.ExpressScanHistory;
import com.example.qrcode.dao.ExpressDBManager;
import com.example.qrcode.utils.Logcat;
import com.example.qrcode.view.FlowLayout;

import java.util.List;

public class ScanExpressActivity extends BaseActivity {
    private static final String TAG = ScanExpressActivity.class.getSimpleName();

    private FlowLayout mFlowLayout;
    private List<ExpressScanHistory> historyList;
    private InputMethodManager imm;
    private int[] mFlowItemBackground = {R.drawable.selector_red_corners_button, R.drawable.selector_yellow_corners_button,
            R.drawable.selector_purple_corners_button, R.drawable.selector_purple_corners_button};

    @Override
    public int getLayoutId() {
        return R.layout.activity_scan_express;
    }

    @Override
    public void onViewCreated(Bundle savedInstanceState) {
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        mFlowLayout = findViewById(R.id.flowlayout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshScanHistory();
    }

    private void refreshScanHistory() {
        historyList = ExpressDBManager.getInstance().queryExpress();

        mFlowLayout.removeAllViews();

        int padding = getResources().getDimensionPixelOffset(R.dimen.flowlayout_item_padding);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = getResources().getDimensionPixelOffset(R.dimen.flowlayout_item_margin);
        params.setMargins(margin, margin, margin, margin);
        for (int size = historyList.size(), i = size - 1; i >= 0; i--) {
            TextView textView = new TextView(this);
            textView.setId(i);
            textView.setPadding(padding, padding, padding, padding);
            textView.setText(historyList.get(i).express_num);
            int n = (int) (Math.random() * mFlowItemBackground.length);
            textView.setBackgroundResource(mFlowItemBackground[n]);
            textView.setOnClickListener(mFlowLayoutClickListener);
            mFlowLayout.addView(textView, params);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scan_express, menu);// 指定Toolbar上的视图文件
        MenuItem searchItem = menu.findItem(R.id.ab_search);
        final SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setInputType(InputType.TYPE_CLASS_NUMBER);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {// 输入完成后，提交时触发的方法，一般情况是点击输入法中的搜索按钮才会触发。表示现在正式提交了
                mSearchView.onActionViewCollapsed();// 收起搜索框
                intent2ExpressDetail(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {// 在输入时触发的方法，当字符真正显示到searchView中才触发，像是拼音，在舒入法组词的时候不会触发
                return true;
            }
        });
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                showSoftInput();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                return true;
            }
        });
        return true;
    }

    private View.OnClickListener mFlowLayoutClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int pos = view.getId();
            ExpressScanHistory history = historyList.get(pos);
            Logcat.d(TAG, "click express num = " + history.express_num);
            intent2ExpressDetail(history.express_num);
        }
    };

    public void intent2ScanExpress(View view) {
        startActivity(new Intent(this, ScanExpressBarCodeActivity.class));
    }

    private void intent2ExpressDetail(String express_num) {
        hideSoftInput();

        Intent intent = new Intent(ScanExpressActivity.this, ExpressDetailActivity.class);
        intent.putExtra("result", express_num);
        startActivity(intent);
    }

    private void showSoftInput() {
        imm.showSoftInput(mFlowLayout, InputMethodManager.SHOW_IMPLICIT);
    }

    private void hideSoftInput() {
        imm.hideSoftInputFromWindow(mFlowLayout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
