package com.skt.tmaphot.activity.main.store;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.skt.tmaphot.MainApplication;
import com.skt.tmaphot.R;


import java.util.ArrayList;
import java.util.List;



public class StoreInfoImageViewActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private StoreInfoImageViewPager mViewPager;
    private List<String> imageUrlList;
    private TextView imageCounttxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_storeinfo_image_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);


        setData();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (StoreInfoImageViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        // 나중에 클릭시 번들에서 위치 값을 가져와서 세팅해야 된다.
        // 시작은 0번부터
        // mViewPager.setCurrentItem(2);


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageCounttxt.setText( (mViewPager.getCurrentItem() + 1) +"/"+ imageUrlList.size());
                    }
                });
            }

            @Override
            public void onPageSelected(int position)
            {

            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });


        imageCounttxt = (TextView)findViewById(R.id.storeinfo_image_count_txt);

    } //END


    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_SECTION_URL = "section_url";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber, String sectionUrl) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString(ARG_SECTION_URL, sectionUrl);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.storeinfo_image_fragment, container, false);
            PhotoView imageView = (PhotoView) rootView.findViewById(R.id.photo_view);
            PhotoViewAttacher mAttacher = new PhotoViewAttacher(imageView);
            mAttacher.setScaleType(ImageView.ScaleType.FIT_CENTER);
//            mAttacher.setMinimumScale(1f);
            MainApplication.loadUrlImage(getActivity(), getArguments().getString(ARG_SECTION_URL), imageView);

            return rootView;
        }

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            if (isVisibleToUser) {

            } else {

            }
            super.setUserVisibleHint(isVisibleToUser);
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {

            super(fm);

        }

        @Override
        public Fragment getItem(int position) {

//          return PlaceholderFragment.newInstance(position + 1);
            return PlaceholderFragment.newInstance(position, imageUrlList.get(position));
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return imageUrlList.size();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void setData(){
        imageUrlList = new ArrayList<String>();
        imageUrlList.add("http://img.hani.co.kr/imgdb/resize/2018/0319/00503531_20180319.JPG"); //손예진
        imageUrlList.add("https://pbs.twimg.com/media/C3EbdSxWIAAU-6M.jpg");    //수지
        imageUrlList.add("http://www.hotelavia.net/news/photo/201710/1287_2768_5455.jpg"); //이연희
        imageUrlList.add("http://www.mbcsportsplus.com/data/board/attach/2018/04/20180429102237_lnzqycwf.jpg"); //조보아
        imageUrlList.add("https://pbs.twimg.com/media/DIFyDGIUIAEzJZX.jpg");    // 쯔위
    }
}

