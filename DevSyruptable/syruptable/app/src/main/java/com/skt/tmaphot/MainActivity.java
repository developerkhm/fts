package com.skt.tmaphot;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.skt.tmaphot.client.SyrupWebChromeClient;
import com.skt.tmaphot.client.SyrupWebViewClient;
import com.skt.tmaphot.common.AndroidBridge;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public WebView mWebView;
    public static MainActivity mContext;
    public static final String ENTRY_URL = "https://shop.ordertable.co.kr/intro";

    public static final String HOTPLACE_URL = "https://shop.ordertable.co.kr/hotplace";
    public static final String SHOP_URL = "https://shop.ordertable.co.kr/";
    public static final String SHARE_URL = "https://shop.ordertable.co.kr/share";
    public static final String DELIVERY_URL = "https://shop.ordertable.co.kr/delivery365";

    //ISP앱으로 부터 Call Back을 받기 위한 App스키마
    private final String APP_SCHEMA_URI = "syruptable://";
    //ISP앱에서 취소를 선택했을 때 받는 URI
    private final String APP_SCHEMA_CANCEL_URI = APP_SCHEMA_URI + "ISPCancel/";
    //ISP앱에서 인증을 성공했을 때 받는 URI
    private final String APP_SCHEMA_SUCCESS_URI = APP_SCHEMA_URI + "ISPSuccess/";

    public static final int FILECHOOSER_RESULTCODE = 1;
    public final static int FILECHOOSER_LOLLIPOP_REQ_CODE = 2;
    public ValueCallback<Uri> mUploadMessage = null;
    public ValueCallback<Uri[]> filePathCallbackLollipop;
    public SyrupWebViewClient syrupWebViewClient;
    public BackPressCloseHandler backPressCloseHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = (WebView) findViewById(R.id.activity_main_webview);
        mWebView.addJavascriptInterface(new AndroidBridge(), "MyApp");
        mWebView.setWebViewClient(new SyrupWebViewClient(this, mWebView));
        mWebView.setWebChromeClient(new SyrupWebChromeClient(this, mWebView));

        WebSettings webSettings = mWebView.getSettings();
        // 웹뷰가 캐시를 사용하지 않도록 설정
        // javascript를 실행할 수 있도록 설정
        webSettings.setJavaScriptEnabled(true);
        // 화면에 문서 전체가 보이게 설정
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        // 확대,축소 기능을 사용할 수 있도록 설정
        webSettings.setSupportZoom(false);
        // 웹뷰 텍스트 고정
        webSettings.setTextZoom(100);
        // 쿠키
        CookieSyncManager.createInstance(this);
        // 마쉬멜로우 버젼 이상일 경우
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            // 웹뷰내 https 이미지 나오게 처리 ( 혼합 콘텐츠가 타사 쿠키를 차단할 때 생기는 오류 처리 )
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.setAcceptThirdPartyCookies(mWebView, true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        //자바스크립트의 window.open 허용
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 여러개의 윈도우를 사용할 수 있도록 설정
        webSettings.setSupportMultipleWindows(false);
        // 웹뷰내의 localStorage 사용여부
        webSettings.setDomStorageEnabled(true);
        // 웹뷰내의 위치정보 사용여부
        webSettings.setGeolocationEnabled(true);

        //============================ 임시 추가 및 변경 사항 ========================//
//        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setEnableSmoothTransition(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAppCacheEnabled(true);
        webSettings.setBuiltInZoomControls(false);
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.setScrollbarFadingEnabled(true);
        mWebView.getSettings().setUseWideViewPort(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebView.getSettings().setLoadWithOverviewMode(true);
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        }

        Uri uri = getIntent().getData();

        if (uri != null) {
            OResultPage(uri);
        } else {
            mWebView.loadUrl(HOTPLACE_URL);

        }
        // 뒤로가기 핸들러
        backPressCloseHandler = new BackPressCloseHandler(this);
    }

    /**
     * ISP로부터 받은 URI에 따라 결제 최종 확인 페이지 혹은 결제 요청 전 페이지를 보여준다.  * @param resultUri  ISP로 부터 받은 URI
     */
    private void OResultPage(Uri resultUri) {
        String schemaUrl = resultUri.toString();
        String urlString = null;

        if (schemaUrl.startsWith(APP_SCHEMA_SUCCESS_URI)) { //ISP 인증을 성공한 경우
            urlString = schemaUrl.substring(APP_SCHEMA_SUCCESS_URI.length());
            mWebView.loadUrl(urlString);

        } else if (schemaUrl.startsWith(APP_SCHEMA_CANCEL_URI)) { //ISP앱에서 취소를 선택한 경우
            urlString = schemaUrl.substring(APP_SCHEMA_CANCEL_URI.length());
            mWebView.loadUrl(urlString);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            Uri result = intent == null || requestCode != RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        } else if (requestCode == FILECHOOSER_LOLLIPOP_REQ_CODE) {
            if (filePathCallbackLollipop == null) return;
            filePathCallbackLollipop.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
            filePathCallbackLollipop = null;

        } else if (requestCode == syrupWebViewClient.REQ_KFTC) {
            String strResultCode = intent.getExtras().getString("bankpay_code");
            String strResultValue = intent.getExtras().getString("bankpay_value");
            String strResultEpType = intent.getExtras().getString("hd_ep_type");

            String url = "https://pg1.payletter.com/PGSVC/SmartKFTC/KFTCCallBack.asp";
            String postData = "bankpay_code=" + strResultCode + "&bankpay_value=" + strResultValue + "&hd_ep_type=" + strResultEpType
                    + "&callbackparam1=" + syrupWebViewClient.mCallbackparam1 + "&callbackparam2=" + syrupWebViewClient.mCallbackparam2 + "&callbackparam3=" + syrupWebViewClient.mCallbackparam3 + "&launchmode=android_app";

            mWebView.clearHistory();
            mWebView.postUrl(url, Base64.encodeToString(postData.getBytes(), Base64.DEFAULT).getBytes());

        }
    }

    @Override
    public void onBackPressed() {
        try {
            backPressCloseHandler.onBackPressed();
        } catch (Exception e) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        CookieSyncManager.getInstance().startSync();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.getInstance().stopSync();
        }
    }

    // 디바이스 기기의 back 버튼 인식
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        try {
            if (mWebView.getUrl().equals(HOTPLACE_URL) || mWebView.getUrl().equals(SHOP_URL) || mWebView.getUrl().equals(SHARE_URL) || mWebView.getUrl().equals(DELIVERY_URL)) {
                backPressCloseHandler.onBackPressed();
                return false;
            }
            if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
                mWebView.goBack();
                return false;
            }
        } catch (Exception ex) {

        }
        return super.onKeyDown(keyCode, event);
    }

    public class BackPressCloseHandler {
        private long backKeyPressedTime = 0;
        private Toast toast;

        private Activity activity;

        public BackPressCloseHandler(Activity context) {
            this.activity = context;
        }

        public void onBackPressed() {
            if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                backKeyPressedTime = System.currentTimeMillis();
                showGuide();
                return;
            }

            if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
                toast.cancel();

                Intent t = new Intent(activity, MainActivity.class);
                activity.startActivity(t);

                activity.moveTaskToBack(true);
                activity.finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            }

        }

        public void showGuide() {
            toast = Toast.makeText(activity, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}