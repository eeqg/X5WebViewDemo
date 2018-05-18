package com.example.az.x5webviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.example.az.x5webviewdemo.utils.X5WebView;
import com.tencent.smtt.export.external.interfaces.HttpAuthHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class X5WebTestActivity extends AppCompatActivity {
	
	private X5WebView mX5WebView;
	
	// private static final String testUrl = "http://soft.imtt.qq.com/browser/tes/feedback.html";
	private static final String testUrl = "http://app.html5.qq.com/navi/index";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
		//
		// //设置成全屏，不然有些游戏进不去
		//   /*set it to be no title*/
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// /*set it to be full screen*/
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// 		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// getWindow().setFormat(PixelFormat.TRANSLUCENT);
		// try {
		// 	if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
		// 		getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
		// 				android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
		// 	}
		// } catch (Exception ignored) {
		// }
		
		setContentView(R.layout.activity_x5_web_test);
		
		initHardwareAccelerate();
		observeContent();
	}
	
	/**
	 * 启用硬件加速
	 */
	private void initHardwareAccelerate() {
		try {
			if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
				getWindow()
						.setFlags(
								android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
								android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
			}
		} catch (Exception e) {
		}
	}
	
	private void observeContent() {
		mX5WebView = findViewById(R.id.x5WebView);
		Log.d("test_wp", "加载了x5内核?: " + (mX5WebView.getX5WebViewExtension() != null));
		mX5WebView.setWebViewClient(client);
		mX5WebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onReceivedTitle(WebView webView, String s) {
				super.onReceivedTitle(webView, s);
				Log.d("test_wp", "onReceivedTitle()--title = " + s);
			}
			
			@Override
			public void onProgressChanged(WebView webView, int progress) {
				super.onProgressChanged(webView, progress);
				Log.d("test_wp", "onProgressChanged()--progress = " + progress);
			}
		});
		
		mX5WebView.loadUrl(testUrl);
	}
	
	private WebViewClient client = new WebViewClient() {
		/**
		 * 防止加载网页时调起系统浏览器
		 */
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
		
		public void onReceivedHttpAuthRequest(WebView webview, HttpAuthHandler httpAuthHandlerhost, String host,
		                                      String realm) {
			boolean flag = httpAuthHandlerhost.useHttpAuthUsernamePassword();
		}
		
		@Override
		public void onPageFinished(WebView webView, String url) {
			super.onPageFinished(webView, url);
			Log.d("test_wp", "onPageFinished()--s = " + url);
		}
		
		@Override
		public void onReceivedError(WebView webView, int i, String s, String s1) {
			System.out.println("***********onReceivedError ************");
			super.onReceivedError(webView, i, s, s1);
		}
		
		@Override
		public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
			System.out.println("***********onReceivedHttpError ************");
			super.onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
		}
	};
	
	/**
	 * 返回键监听
	 *
	 * @param keyCode
	 * @param event
	 * @return
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mX5WebView != null && mX5WebView.canGoBack()) {
				mX5WebView.goBack();
				return true;
			} else {
				return super.onKeyDown(keyCode, event);
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	@Override
	protected void onDestroy() {
		//释放资源
		if (mX5WebView != null)
			mX5WebView.destroy();
		super.onDestroy();
	}
}
