package com.skt.tmaphot.activity.main.store;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skt.tmaphot.BaseApplication;
import com.skt.tmaphot.R;
import com.skt.tmaphot.BaseActivity;
import com.skt.tmaphot.activity.ImageViewPager;
import com.skt.tmaphot.activity.ImageViewPagerActivity;
import com.skt.tmaphot.activity.main.store.review.ReviewItem;
import com.skt.tmaphot.activity.main.store.review.ReviewRecyclerViewAdapter;
import com.skt.tmaphot.activity.main.store.review.ReviewTotalActivity;
import com.skt.tmaphot.activity.main.store.review.SocialReviewItem;
import com.skt.tmaphot.activity.main.store.review.SocialReviewRecyclerViewAdapter;
import com.skt.tmaphot.activity.main.store.review.SocialReviewTotalActivity;
import com.skt.tmaphot.activity.review.ReviewWriteActivity;
import com.skt.tmaphot.common.CommonUtil;

import java.util.ArrayList;
import java.util.List;

public class StoreInfoActivity extends BaseActivity {

    //상단 메인 이미지
    private StoreInfoActivity.SectionsPagerAdapter mSectionsPagerAdapter;
    private ImageViewPager mViewPager;
    private List<String> imageUrlList;
    private TextView imageCounttxt;

    private LinearLayout info_bookmark, info_review, info_evaluation, list_review;

    // 상점 리뷰사진 모음 리스트
    private RecyclerView storeInfoReviewRecyclerView;
    private StoreInfoRecyclerViewDataAdapter storeInfoRecyclerViewDataAdapter;
    private StoreInfoItem storeInfoItem;

    //리뷰리스트
    private RecyclerView reviewRecyclerView;
    private ReviewRecyclerViewAdapter reviewRecyclerViewAdapter;
    private List<ReviewItem> reviewItemList;

    //소셜 리뷰리스트
    private RecyclerView socialReviewRecyclerView;
    private SocialReviewRecyclerViewAdapter socialReviewRecyclerViewAdapter;
    private List<SocialReviewItem> socialReviewItemList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baceContext = this;
        setContentView(R.layout.activity_storeinfo_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        // 임시 메인 이미지 큰거만 뽑음
        setData();

        Button reviewMore = (Button)findViewById(R.id.review_more);
        reviewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityStart(new Intent(baceContext, ReviewTotalActivity.class), null);
            }
        });

        Button socialReviewMore = (Button)findViewById(R.id.socialreview_more);
        socialReviewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityStart(new Intent(baceContext, SocialReviewTotalActivity.class), null);
            }
        });

        info_bookmark = (LinearLayout)findViewById(R.id.storeinfo_info_bookmark);
        info_evaluation = (LinearLayout)findViewById(R.id.storeinfo_info_evaluation);

        info_review = (LinearLayout)findViewById(R.id.storeinfo_info_review);
        info_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityStart(new Intent(baceContext, ReviewWriteActivity.class), null);
            }
        });

        list_review = (LinearLayout)findViewById(R.id.review_list_write);
        info_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityStart(new Intent(baceContext, ReviewWriteActivity.class), null);
            }
        });


        mSectionsPagerAdapter = new StoreInfoActivity.SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ImageViewPager) findViewById(R.id.viewpager_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
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


        imageCounttxt = (TextView)findViewById(R.id.viewpager_image_count_txt);

        getIninData();

        setStoreInfo();

        setReview();

        setSocialReview();

    } //END

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

    // 임시 메인 이미지
    private void setData(){
        imageUrlList = new ArrayList<String>();
        imageUrlList.add("http://img.hani.co.kr/imgdb/resize/2018/0319/00503531_20180319.JPG"); //손예진
        imageUrlList.add("https://pbs.twimg.com/media/C3EbdSxWIAAU-6M.jpg");    //수지
        imageUrlList.add("http://www.hotelavia.net/news/photo/201710/1287_2768_5455.jpg"); //이연희
        imageUrlList.add("http://www.mbcsportsplus.com/data/board/attach/2018/04/20180429102237_lnzqycwf.jpg"); //조보아
        imageUrlList.add("https://pbs.twimg.com/media/DIFyDGIUIAEzJZX.jpg");    // 쯔위
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_SECTION_URL = "section_url";

        public PlaceholderFragment() {
        }

        public static StoreInfoActivity.PlaceholderFragment newInstance(int sectionNumber, String sectionUrl) {
            StoreInfoActivity.PlaceholderFragment fragment = new StoreInfoActivity.PlaceholderFragment();
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
            ImageView imageView = (ImageView) rootView.findViewById(R.id.image_view);

//            PhotoViewAttacher mAttacher = new PhotoViewAttacher(imageView);
//            mAttacher.setScaleType(ImageView.ScaleType.FIT_CENTER);
//            mAttacher.setMinimumScale(1f);
            BaseApplication.getInstance().loadImage(getActivity(), getArguments().getString(ARG_SECTION_URL), imageView, false);

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

    public class StoreInfoItem {
        List<String> stroeInfoMainImageList;
        List<String> storeInfoReviewImageList;

        public List<String> getStroeInfoMainImageList() {
            return stroeInfoMainImageList;
        }

        public List<String> getStoreInfoReviewImageList() {
            return storeInfoReviewImageList;
        }

        public StoreInfoItem(List<String> stroeInfoMainImageList, List<String> storeInfoReviewImageList) {
            this.stroeInfoMainImageList = stroeInfoMainImageList;
            this.storeInfoReviewImageList = storeInfoReviewImageList;
        }
    }

    public class StoreInfoItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImgReview = null;

        public StoreInfoItemViewHolder(View itemView) {
            super(itemView);

            if (itemView != null) {
                mImgReview = (ImageView) itemView.findViewById(R.id.storeinfo_recyler_item_image);

//                Drawable drawable= null;
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                    drawable = (Drawable)itemView.getContext().getDrawable(R.drawable.round_main_item);
//                    itemView.setBackground(drawable);
//                    itemView.setClipToOutline(true);
//                }


                itemView.setOnClickListener(this);
            }


        }

        public ImageView getImageView() {
            return mImgReview;
        }

        @Override
        public void onClick(View v) {
//            v.getContext().startActivity(new Intent(v.getContext(), StoreInfoActivity.class));
        }
    }

    public class StoreInfoRecyclerViewDataAdapter extends RecyclerView.Adapter<StoreInfoItemViewHolder> implements View.OnClickListener {
        private Context mContext;
        private List<String> viewItemList;


        public StoreInfoRecyclerViewDataAdapter(StoreInfoItem storeInfoItem) {
            viewItemList = storeInfoItem.getStoreInfoReviewImageList();
        }

        @Override
        public StoreInfoItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            mContext = parent.getContext();

            // Get LayoutInflater object.
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            // Inflate the RecyclerView item layout xml.
            View itemView = layoutInflater.inflate(R.layout.storeinfo_image_review_recycler_item, parent, false);

            // Create and return our customRecycler View Holder object.
            StoreInfoItemViewHolder ret = new StoreInfoItemViewHolder(itemView);
            itemView.setOnClickListener(this);
            return ret;
        }

        @Override
        public void onBindViewHolder(StoreInfoItemViewHolder holder, int position) {
            if (viewItemList != null) {
                // Get car item dto in list.
                String viewItem = viewItemList.get(position);

                if (viewItem != null) {
                    // Set car item title.
                    loadImage(mContext, viewItem, holder.getImageView(), false);
                }
            }
        }

        @Override
        public int getItemCount() {
            int ret = 0;
            if (viewItemList != null) {
                ret = viewItemList.size();
            }
            return ret;
        }

        @Override
        public void onClick(View v) {

//            System.out.println(getPosition());
//            Intent intent = ;
            startActivity(new Intent(StoreInfoActivity.this , ImageViewPagerActivity.class));
        }
    }

//    private class ReviewItem {
//        private String loginImageUrl;
//        private String nickname;
//        private String content;
//        private String sympathyImage;
//        private String sympathy;
//
//        public ReviewItem(String loginImageUrl, String nickname, String content, String sympathyImage, String sympathy) {
//            this.loginImageUrl = loginImageUrl;
//            this.nickname = nickname;
//            this.content = content;
//            this.sympathyImage = sympathyImage;
//            this.sympathy = sympathy;
//        }
//
//        public String getLoginImageUrl() {
//            return loginImageUrl;
//        }
//
//        public String getNickname() {
//            return nickname;
//        }
//
//        public String getContent() {
//            return content;
//        }
//
//        public String getSympathyImage() {
//            return sympathyImage;
//        }
//
//        public String getSympathy() {
//            return sympathy;
//        }
//    }
//
//    private class ReviewRecyclerViewHolder extends RecyclerView.ViewHolder {
//        public ImageView loginImageView;
//        public TextView nicknameTextview;
//        public TextView contentTextview;
//        public ImageView sympathyImageView;
//        public TextView sympathyTextView;
//
//        public ReviewRecyclerViewHolder(View itemView) {
//            super(itemView);
//            loginImageView = (ImageView) itemView.findViewById(R.id.review_my_image);
//        }
//    }
//
//    private class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewRecyclerViewHolder> {
//
//        private List<ReviewItem> mItems;
//        Context mContext;
//
//        public ReviewRecyclerViewAdapter(List<ReviewItem> reviewItemList) {
//            mItems = reviewItemList;
//        }
//
//        @Override
//        public ReviewRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            mContext = parent.getContext();
//            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//            View itemView = layoutInflater.inflate(R.layout.storeinfo_review_recycler_item, parent, false);
//            ReviewRecyclerViewHolder ret = new ReviewRecyclerViewHolder(itemView);
//            return ret;
//        }
//
//        @Override
//        public void onBindViewHolder(ReviewRecyclerViewHolder holder, final int position) {
//
//            MainApplication.loadResRoundImage(mContext,R.drawable.img_default_user, holder.loginImageView);
////            MainApplication.loadUrlRoundImage(mContext, mItems.get(position).loginImageUrl,holder.loginImageView);
//
//
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(mContext, String.format("%d 선택", position + 1), Toast.LENGTH_SHORT).show();
//                    //////////////////////////////////////////////// 임시 ////////////////////////////////////////////////
//                    startActivity(new Intent(StoreInfoActivity.this, MyBlogActivity.class));
//                    //////////////////////////////////////////////////// 임시 ///////////////////////////////////////////////
//                }
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return mItems.size();
//        }
//    }

//    private class SocialReviewItem {
//        private String loginImageUrl;
//        private String nickname;
//        private String content;
//        private String sympathyImage;
//        private String sympathy;
//
//        public SocialReviewItem(String loginImageUrl, String nickname, String content, String sympathyImage, String sympathy) {
//            this.loginImageUrl = loginImageUrl;
//            this.nickname = nickname;
//            this.content = content;
//            this.sympathyImage = sympathyImage;
//            this.sympathy = sympathy;
//        }
//
//        public String getLoginImageUrl() {
//            return loginImageUrl;
//        }
//
//        public String getNickname() {
//            return nickname;
//        }
//
//        public String getContent() {
//            return content;
//        }
//
//        public String getSympathyImage() {
//            return sympathyImage;
//        }
//
//        public String getSympathy() {
//            return sympathy;
//        }
//    }
//
//    private class SocialReviewRecyclerViewHolder extends RecyclerView.ViewHolder {
//        private ImageView loginImageView;
//        private TextView nicknameTextview;
//        private TextView contentTextview;
//        private ImageView sympathyImageView;
//        private TextView sympathyTextView;
//
//        public SocialReviewRecyclerViewHolder(View itemView) {
//            super(itemView);
////            loginImageView = (ImageView) itemView.findViewById(R.id.coupon_recyler_item_image);
//        }
//    }
//
//    private class SocialReviewRecyclerViewAdapter extends RecyclerView.Adapter<SocialReviewRecyclerViewHolder> {
//
//        private List<SocialReviewItem> mItems;
//        Context mContext;
//
//        public SocialReviewRecyclerViewAdapter(List<SocialReviewItem> reviewItemList) {
//            mItems = reviewItemList;
//        }
//
//        @Override
//        public SocialReviewRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            mContext = parent.getContext();
//            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//            View itemView = layoutInflater.inflate(R.layout.storeinfo_socialreview_recycler_item, parent, false);
//            SocialReviewRecyclerViewHolder ret = new SocialReviewRecyclerViewHolder(itemView);
//            return ret;
//        }
//
//        @Override
//        public void onBindViewHolder(SocialReviewRecyclerViewHolder holder, final int position) {
////            MainApplication.loadUrlImage(mContext, mItems.get(position).getLoginImageUrl(), holder.loginImageView);
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(mContext, String.format("%d 선택", position + 1), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return mItems.size();
//        }
//    }


    private void setStoreInfo() {

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        storeInfoReviewRecyclerView = (RecyclerView) findViewById(R.id.storeinfo_review_recycler_view);
        storeInfoReviewRecyclerView.setLayoutManager(layoutManager);
        storeInfoRecyclerViewDataAdapter = new StoreInfoRecyclerViewDataAdapter(storeInfoItem);
        storeInfoReviewRecyclerView.setAdapter(storeInfoRecyclerViewDataAdapter);
        storeInfoReviewRecyclerView.setFocusable(false);
        storeInfoReviewRecyclerView.scrollToPosition(0);
        storeInfoReviewRecyclerView.setNestedScrollingEnabled(false);
        storeInfoReviewRecyclerView.setHasFixedSize(true);
        storeInfoReviewRecyclerView.addItemDecoration(CommonUtil.getInstance().new SpacesItemDecoration(0, 15, 0, 0));


        storeInfoReviewRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                Log.d("YYY", "onScrolled");

                super.onScrolled(recyclerView, dx, dy);

                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                int firstCompleteVisibleItemPosition = -1;
                int lastCompleteVisibleItemPosition = -1;
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();

                if (layoutManager instanceof GridLayoutManager) {
                    GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                    firstCompleteVisibleItemPosition = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
                    lastCompleteVisibleItemPosition = gridLayoutManager.findLastCompletelyVisibleItemPosition();
                } else if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                    firstCompleteVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                    lastCompleteVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                }

                String message = "";

                if (lastCompleteVisibleItemPosition == (totalItemCount - 1 -2)) {
                    Log.d("YYY", "lastCompleteVisibleItemPosition : " + lastCompleteVisibleItemPosition);
                    Log.d("YYY", "totalItemCount : " + (totalItemCount - 1));
                    if (dy > 0 || dx > 0) {

                        if (dy > 0) {
                            Log.d("YYY", "Scroll to top");
                            loadData(false);
                        }


                        if (dx > 0) {
                            Log.d("YYY", "Scroll to left");
                            loadData(false);
                        }
                    }
                }
            }

        });
    }


    private void setReview() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        reviewRecyclerView = (RecyclerView) findViewById(R.id.review_recycler_view);
        reviewRecyclerView.setLayoutManager(layoutManager);
        reviewRecyclerViewAdapter = new ReviewRecyclerViewAdapter(reviewItemList);
        reviewRecyclerView.setAdapter(reviewRecyclerViewAdapter);
        reviewRecyclerView.setFocusable(false);
        reviewRecyclerView.setHasFixedSize(true);
        reviewRecyclerView.addItemDecoration(CommonUtil.getInstance().new SpacesItemDecoration(0, 0, 1, 0));

    }

    private void setSocialReview() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        socialReviewRecyclerView = (RecyclerView) findViewById(R.id.socialreview_recycler_view);
        socialReviewRecyclerView.setLayoutManager(layoutManager);
        socialReviewRecyclerViewAdapter = new SocialReviewRecyclerViewAdapter(socialReviewItemList);
        socialReviewRecyclerView.setAdapter(socialReviewRecyclerViewAdapter);
        socialReviewRecyclerView.setFocusable(false);
        socialReviewRecyclerView.setHasFixedSize(true);
        socialReviewRecyclerView.addItemDecoration(CommonUtil.getInstance().new SpacesItemDecoration(0, 0, 1, 0));socialReviewRecyclerView.addItemDecoration(CommonUtil.getInstance().new SpacesItemDecoration(0, 0, 1, 0));
    }

    private void getIninData() {


        List<String> mainImageURL = new ArrayList<String>();
        mainImageURL.add("https://v-phinf.pstatic.net/20170122_279/14850543400066RidC_JPEG/1485032301509.jpg");
        mainImageURL.add("http://cdnweb01.wikitree.co.kr/webdata/editor/201706/09/img_20170609202443_f65fbbe9.jpg");
        mainImageURL.add("https://t1.daumcdn.net/news/201801/25/tvreport/20180125092134561isje.jpg");
        mainImageURL.add("http://newsmanager2.etomato.com/userfiles/image/%EC%97%94%ED%84%B0%ED%8C%80/%EC%A0%95%ED%95%B4%EC%9A%B1/suzysuzy.jpg");
        mainImageURL.add("http://thumb.mt.co.kr/06/2018/01/2018011114325360998_1.jpg");


        List<String> storeInforeviewImageURL = new ArrayList<String>();
        storeInforeviewImageURL.add("http://1.bp.blogspot.com/-yumuFZdegok/VHNwRS1VqsI/AAAAAAAAFlw/D-RYW3jbWIY/s1600/%EC%A4%91%EA%B5%AD%2B%EA%B5%AC%EC%B1%84%EA%B5%AC%2B%EA%B0%80%EC%9D%84%2B%ED%92%8D%EA%B2%BD.jpg");
        storeInforeviewImageURL.add("http://1.bp.blogspot.com/-yumuFZdegok/VHNwRS1VqsI/AAAAAAAAFlw/D-RYW3jbWIY/s1600/%EC%A4%91%EA%B5%AD%2B%EA%B5%AC%EC%B1%84%EA%B5%AC%2B%EA%B0%80%EC%9D%84%2B%ED%92%8D%EA%B2%BD.jpg");
        storeInforeviewImageURL.add("http://1.bp.blogspot.com/-yumuFZdegok/VHNwRS1VqsI/AAAAAAAAFlw/D-RYW3jbWIY/s1600/%EC%A4%91%EA%B5%AD%2B%EA%B5%AC%EC%B1%84%EA%B5%AC%2B%EA%B0%80%EC%9D%84%2B%ED%92%8D%EA%B2%BD.jpg");
        storeInforeviewImageURL.add("http://1.bp.blogspot.com/-yumuFZdegok/VHNwRS1VqsI/AAAAAAAAFlw/D-RYW3jbWIY/s1600/%EC%A4%91%EA%B5%AD%2B%EA%B5%AC%EC%B1%84%EA%B5%AC%2B%EA%B0%80%EC%9D%84%2B%ED%92%8D%EA%B2%BD.jpg");
        storeInforeviewImageURL.add("http://1.bp.blogspot.com/-yumuFZdegok/VHNwRS1VqsI/AAAAAAAAFlw/D-RYW3jbWIY/s1600/%EC%A4%91%EA%B5%AD%2B%EA%B5%AC%EC%B1%84%EA%B5%AC%2B%EA%B0%80%EC%9D%84%2B%ED%92%8D%EA%B2%BD.jpg");
        storeInforeviewImageURL.add("http://1.bp.blogspot.com/-yumuFZdegok/VHNwRS1VqsI/AAAAAAAAFlw/D-RYW3jbWIY/s1600/%EC%A4%91%EA%B5%AD%2B%EA%B5%AC%EC%B1%84%EA%B5%AC%2B%EA%B0%80%EC%9D%84%2B%ED%92%8D%EA%B2%BD.jpg");
        storeInforeviewImageURL.add("http://1.bp.blogspot.com/-yumuFZdegok/VHNwRS1VqsI/AAAAAAAAFlw/D-RYW3jbWIY/s1600/%EC%A4%91%EA%B5%AD%2B%EA%B5%AC%EC%B1%84%EA%B5%AC%2B%EA%B0%80%EC%9D%84%2B%ED%92%8D%EA%B2%BD.jpg");
        storeInforeviewImageURL.add("http://1.bp.blogspot.com/-yumuFZdegok/VHNwRS1VqsI/AAAAAAAAFlw/D-RYW3jbWIY/s1600/%EC%A4%91%EA%B5%AD%2B%EA%B5%AC%EC%B1%84%EA%B5%AC%2B%EA%B0%80%EC%9D%84%2B%ED%92%8D%EA%B2%BD.jpg");
        storeInforeviewImageURL.add("http://1.bp.blogspot.com/-yumuFZdegok/VHNwRS1VqsI/AAAAAAAAFlw/D-RYW3jbWIY/s1600/%EC%A4%91%EA%B5%AD%2B%EA%B5%AC%EC%B1%84%EA%B5%AC%2B%EA%B0%80%EC%9D%84%2B%ED%92%8D%EA%B2%BD.jpg");


        storeInfoItem = new StoreInfoItem(mainImageURL, storeInforeviewImageURL);


        // dummydata
        reviewItemList = new ArrayList<ReviewItem>();
        reviewItemList.add(new ReviewItem("http://image.chosun.com/sitedata/image/201711/28/2017112802484_0.jpg", "", "", "", ""));
        reviewItemList.add(new ReviewItem("http://image.chosun.com/sitedata/image/201711/28/2017112802484_0.jpg", "", "", "", ""));

        // dummydata
        socialReviewItemList = new ArrayList<SocialReviewItem>();
        socialReviewItemList.add(new SocialReviewItem("http://img.sbs.co.kr/newimg/news/20170622/201061239_1280.jpg", "", "", "", ""));
        socialReviewItemList.add(new SocialReviewItem("http://img.sbs.co.kr/newimg/news/20170622/201061239_1280.jpg", "", "", "", ""));
    }

    private void loadData(final boolean insertDataAtBeginning) {
        Log.d("YYY", "loadData");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int currItemListSize = storeInfoItem.getStoreInfoReviewImageList().size();
                int newItemIndex = 0;
                int loadMoreItemCount = 5;

                // Only add one RecyclerView item.
                for (int i = currItemListSize; i < currItemListSize + loadMoreItemCount; i++) {

                    String newViewItem = "https://www.lwt.co.kr/datas/factory/main_img/006059";


                    if (insertDataAtBeginning) {
                        storeInfoItem.getStoreInfoReviewImageList().add(i - currItemListSize, newViewItem);
                        newItemIndex = 0;
                    } else {
                        storeInfoItem.getStoreInfoReviewImageList().add(newViewItem);
                        newItemIndex = storeInfoItem.getStoreInfoReviewImageList().size() - 1;
                    }
                }

                Log.d("YYY", "loadData END");
                storeInfoRecyclerViewDataAdapter.notifyDataSetChanged();
            }
        });
    }
}

