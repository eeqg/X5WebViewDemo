package com.example.az.x5webviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.example.az.x5webviewdemo.utils.X5WebView;

public class X5WebTestActivity extends AppCompatActivity {
	
	private X5WebView mX5WebView;
	
	// private static final String testUrl = "http://soft.imtt.qq.com/browser/tes/feedback.html";
	private static final String testUrl = "http://app.html5.qq.com/navi/index";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		mX5WebView.loadUrl(testUrl);
	}
	
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
