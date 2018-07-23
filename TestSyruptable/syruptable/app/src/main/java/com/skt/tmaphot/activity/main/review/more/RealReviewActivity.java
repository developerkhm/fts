package com.skt.tmaphot.activity.main.review.more;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.skt.tmaphot.R;

import java.util.ArrayList;

public class RealReviewActivity extends AppCompatActivity {

    private GridView gridview;
    private Point size;

    private int width;
    private int height;
    private Handler handler = new Handler();

    private boolean mLockListView;
    private ArrayList<String> mRowList;

    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realreview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        gridview = (GridView) findViewById(R.id.simpleGridView);

        size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        width = size.x;
        height = size.y;

        mLockListView = false;
        mRowList = new ArrayList<String>();
        mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
        mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
        mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
        mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
        mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
        mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
        mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
        mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
        mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
        mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
        mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
        mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
        mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
        mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
        mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
        mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
        mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
        mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
        mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
        mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
        mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
        mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
        mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
        mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
        mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
        mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
        mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");

        imageAdapter = new ImageAdapter(this);
        gridview.setAdapter(imageAdapter);
        gridview.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub

//                Log.e("GridView", "onScrollStateChanged : " + scrollState);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub
                Log.e("GridView", "firstVisibleItem: " + firstVisibleItem + "\nLastVisibleItem: " + totalItemCount);

                // 현재 가장 처음에 보이는 셀번호와 보여지는 셀번호를 더한값이
                // 전체의 숫자와 동일해지면 가장 아래로 스크롤 되었다고 가정합니다.
                int count = totalItemCount - visibleItemCount;

                if(firstVisibleItem >= count && totalItemCount != 0 && mLockListView == false)
                {
                    addItems(50);
                }


            }
        });

    }//END

    private void addItems(final int size) {
        // 아이템을 추가하는 동안 중복 요청을 방지하기 위해 락을 걸어둡니다.
        mLockListView = true;
        Log.d("grid", "addItems");
        Runnable run = new Runnable() {
            @Override
            public void run() {
                Log.d("grid", "run");

                mRowList.add("https://picksell.co.kr/images/product/128734/1104487a-82c9-41b4-be65-89d3f80088f5.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128734/1104487a-82c9-41b4-be65-89d3f80088f5.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128734/1104487a-82c9-41b4-be65-89d3f80088f5.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128734/1104487a-82c9-41b4-be65-89d3f80088f5.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128734/1104487a-82c9-41b4-be65-89d3f80088f5.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128734/1104487a-82c9-41b4-be65-89d3f80088f5.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128734/1104487a-82c9-41b4-be65-89d3f80088f5.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128734/1104487a-82c9-41b4-be65-89d3f80088f5.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128734/1104487a-82c9-41b4-be65-89d3f80088f5.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128734/1104487a-82c9-41b4-be65-89d3f80088f5.jpg");


                // 모든 데이터를 로드하여 적용하였다면 어댑터에 알리고
                // 리스트뷰의 락을 해제합니다.
//                gridview.notifyDataSetChanged();

//                imageAdapter.notifyDataChanged();
//                gridview.invalidateViews();
//                gridview.setAdapter(imageAdapter);

//                imageAdapter.clearAdapter(); // clear old values
//                adapter.addNewValues(MObjects);
//                adapter.notifyDataChanged();

                imageAdapter.notifyDataSetChanged();
//                gridview.setAdapter(imageAdapter);

                mLockListView = false;
            }
        };

        handler.post(run);
    }

        public class ImageAdapter extends BaseAdapter {

        private Context mContext;

        public ImageAdapter(Context c) {

            mContext = c;
        }

        public int getCount() {
            return mRowList.size();
        }

        public Object getItem(int position) {
            return mRowList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = null;
            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(
                        width / 3, width / 3));
//                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                imageView.setPadding(1, 1, 1, 1);
            } else {

                imageView = (ImageView) convertView;
            }
//            Glide.with(getBaseContext()).load(mThumbIds[position]).into(imageView);

            RequestOptions options = new RequestOptions();
            options.fitCenter();

            Glide.with(getBaseContext())
                    .load(mRowList.get(position))
                    .apply(options)
                    .into(imageView);

            return imageView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.store_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
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
}

