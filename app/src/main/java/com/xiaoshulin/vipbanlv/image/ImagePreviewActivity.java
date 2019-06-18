package com.xiaoshulin.vipbanlv.image;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xiaoshulin.vipbanlv.R;


import java.util.ArrayList;


@Route(path = "/image/preview")
public class ImagePreviewActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {


    public ArrayList<String> images;

    public int position;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);

        Intent intent = getIntent();
        images = intent.getStringArrayListExtra("images");
        position = intent.getIntExtra("position", 0);
        if (this.images == null) {
            this.images = new ArrayList<>();
        }
        mViewPager = findViewById(R.id.image_preview_pager);
        mViewPager.addOnPageChangeListener(this);

        ImagePreviewAdapter adapter = new ImagePreviewAdapter(this);
        adapter.setData(this.images);
        mViewPager.setAdapter(adapter);

        onPageSelected(position < this.images.size() ? position : 0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
