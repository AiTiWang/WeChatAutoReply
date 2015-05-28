package com.i4evercai.wechat.autoreply;

import java.util.List;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.i4evercai.utils.LogUtils;

public class AutoReplyService extends AccessibilityService {
	/** 微信的包名*/
	static final String WECHAT_PACKAGENAME = "com.tencent.mm";
	// 声明键盘管理器
	private KeyguardManager mKeyguardManager = null;
	// 声明键盘锁
	private KeyguardLock mKeyguardLock = null;
	// 声明电源管理器
	private PowerManager pm;
	private PowerManager.WakeLock wakeLock;
	private String recText; // 接收到的消息内容
	@Override
	public void onCreate() {
		super.onCreate();
		// 获取电源的服务
		pm = (PowerManager) getSystemService(POWER_SERVICE);
		// 获取系统服务
		mKeyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
	}

	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		final int eventType = event.getEventType();
		// 通知栏事件
		if (eventType == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
			LogUtils.d("通知栏事件，包名：" + event.getPackageName());
			List<CharSequence> texts = event.getText();
			if (!texts.isEmpty()) {
				for (CharSequence t : texts) {
					try {
						String text = String.valueOf(t);
						recText = text.substring(text.indexOf(":")+1);
						LogUtils.d("通知栏内容：" + recText);
						openNotify(event);
						break;
					} catch (Exception e) {
						// TODO: handle exception
					}
					
				}
			}
		} else if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
			autoReply(event);
		}
	}

	@Override
	public void onInterrupt() {

	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void openNotify(AccessibilityEvent event) {
		if (event.getParcelableData() == null
				|| !(event.getParcelableData() instanceof Notification)) {
			return;
		}
		unlock();
		// 以下是精华，将微信的通知栏消息打开
		Notification notification = (Notification) event.getParcelableData();
		PendingIntent pendingIntent = notification.contentIntent;
		try {
			pendingIntent.send();
		} catch (PendingIntent.CanceledException e) {
			e.printStackTrace();
		}
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private void autoReply(AccessibilityEvent event) {
		if ("com.tencent.mm.ui.LauncherUI".equals(event.getClassName()) && !recText.equals("")) {
			// 在聊天界面,去点中红包
			AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
			if (nodeInfo == null) {
				return;
			}
			List<AccessibilityNodeInfo> editInfo = nodeInfo
					.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/r2");
			LogUtils.d("微信：输入框：" + editInfo.size());
			for (AccessibilityNodeInfo info : editInfo) {
				LogUtils.d("微信：输入框：" + info.getClassName());
				if (info != null && info.getClassName().equals("android.widget.EditText")) {
					Bundle arguments = new Bundle();
					arguments.putCharSequence(
							AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
							recText+("\n(Auto Reply By 4evercai)"));
					info.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
				}
			}
			List<AccessibilityNodeInfo> sendBtnInfo = nodeInfo
					.findAccessibilityNodeInfosByText("发送");
			LogUtils.d("微信：发送按钮：" + sendBtnInfo.size());
			for (AccessibilityNodeInfo info : sendBtnInfo) {
				LogUtils.d("微信：发送按钮：" + info.getClassName());

				if (info != null && info.getClassName().equals("android.widget.Button")) {
					LogUtils.d("微信：发送按钮：click");
					info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
				}

			}
			recText = "";
			release();
			backHomeUI();
		}
		
	}
	
	private void unlock(){
		wakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");  
		wakeLock.acquire();  
		//初始化键盘锁，可以锁定或解开键盘锁  
		mKeyguardLock = mKeyguardManager.newKeyguardLock("");    
		//禁用显示键盘锁定  
		mKeyguardLock.disableKeyguard();
	}
	private void release(){
		try {
			wakeLock.release();
		} catch (Exception e) {
		}
	}
	
	private void backHomeUI(){
		Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);  
		  
		mHomeIntent.addCategory(Intent.CATEGORY_HOME);  
		mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK  
		                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);  
		startActivity(mHomeIntent);
	}
}
