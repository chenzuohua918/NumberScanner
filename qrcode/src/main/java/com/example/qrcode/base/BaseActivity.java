package com.example.qrcode.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.qrcode.R;

public abstract class BaseActivity extends AppCompatActivity {
    private FrameLayout mMainContent;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = getLayoutInflater().inflate(R.layout.activity_base, null);
        View view = getLayoutInflater().inflate(getLayoutId(), null);
        mMainContent = rootView.findViewById(R.id.fl_main_content);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mMainContent.addView(view, params);
        setContentView(rootView);

        mToolbar = rootView.findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        onViewCreated(savedInstanceState);
    }

    public abstract int getLayoutId();

    public abstract void onViewCreated(Bundle savedInstanceState);

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();// 真正实现回退功能的代码
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
