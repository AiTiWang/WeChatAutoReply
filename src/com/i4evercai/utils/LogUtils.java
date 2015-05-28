//			                  _oo0oo_
//			                 o8888888o
//			                 88" . "88
//			                 (| -_- |)
//						     0\  =  /0
//						   ___/`___'\___
//						 .' \\|     |// '.
//						/ \\|||  :  |||// \
//					   / _||||| -:- |||||_ \
//					  |   | \\\  _  /// |   |
//					  | \_|  ''\___/''  |_/ |
//					  \  .-\__  '_'  __/-.  /
//					___'. .'  /--.--\  '. .'___
//				  ."" '<  .___\_<|>_/___. '>' "".
//			   | | :  `_ \`.;` \ _ / `;.`/ - ` : | |
//			   \ \  `_.   \_ ___\ /___ _/   ._`  / /
//			====`-.____` .__ \_______/ __. -` ___.`====
//							 `=-----='
//         ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//                    佛祖保佑           永无BUG
//                   
//                     Power   By    4evercai
//         ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

package com.i4evercai.utils;
import android.util.Log;

/**
 * @ClassName: LogUtils
 * @Description: TODO
 * @author 4evercai
 * @date 2014年8月27日 下午2:28:25
 * 
 */

public class LogUtils {
	private static final boolean DEBUG = true;
	private static final String TAG = "LogUtils.java";

	public static void e(String message) {
		if (DEBUG) {
			StackTraceElement[] elements = Thread.currentThread().getStackTrace();
			if (elements.length < 4) {
				Log.e(TAG, "Stack to shallow");
				
			} else {
				String fullClassName = elements[3].getClassName();
				String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
				String methodName = elements[3].getMethodName();
				int lineNumber = elements[3].getLineNumber();
				Log.e(className + "." + methodName + "():" + lineNumber, message);
			}
		}
	}

	public static void d(String message) {
		if (DEBUG) {
			StackTraceElement[] elements = Thread.currentThread().getStackTrace();
			if (elements.length < 3) {
				Log.e(TAG, "Stack to shallow");
			} else {
				String fullClassName = elements[3].getClassName();
				String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
				String methodName = elements[3].getMethodName();
				int lineNumber = elements[3].getLineNumber();
				Log.d(className + "." + methodName + "():" + lineNumber, message);
				
			}
		}
	}

	public static void i(String message) {
		if (DEBUG) {
			StackTraceElement[] elements = Thread.currentThread().getStackTrace();
			if (elements.length < 3) {
				Log.e(TAG, "Stack to shallow");
			} else {
				String fullClassName = elements[3].getClassName();
				String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
				String methodName = elements[3].getMethodName();
				int lineNumber = elements[3].getLineNumber();
				Log.i(className + "." + methodName + "():" + lineNumber, message);
			}
		}
	}

	public static void w(String message) {
		if (DEBUG) {
			StackTraceElement[] elements = Thread.currentThread().getStackTrace();
			if (elements.length < 3) {
				Log.e(TAG, "Stack to shallow");
			} else {
				String fullClassName = elements[3].getClassName();
				String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
				String methodName = elements[3].getMethodName();
				int lineNumber = elements[3].getLineNumber();
				Log.w(className + "." + methodName + "():" + lineNumber, message);
			}
		}
	}

	public static void v(String message) {
		if (DEBUG) {
			StackTraceElement[] elements = Thread.currentThread().getStackTrace();
			if (elements.length < 3) {
				Log.e(TAG, "Stack to shallow");
			} else {
				String fullClassName = elements[3].getClassName();
				String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
				String methodName = elements[3].getMethodName();
				int lineNumber = elements[3].getLineNumber();
				Log.v(className + "." + methodName + "():" + lineNumber, message);
			}
		}
	}

}
