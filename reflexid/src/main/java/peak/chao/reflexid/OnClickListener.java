package peak.chao.reflexid;

import android.view.View;

import java.lang.reflect.Method;

public class OnClickListener implements View.OnClickListener, View.OnLongClickListener {
    /**
     * 反射中要被调用方法的对象,通过构造方法进来
     */
    private Object receiver = null;
    /**
     * 点击事件的方法名字
     */
    private String clickMethodName = "";

    public OnClickListener(Object receiver, String clickMethodName) {
        this.receiver = receiver;
        this.clickMethodName = clickMethodName;
    }


    @Override
    public void onClick(View v) {
        Method method = null;
        try {// 不传递View监听
            method = receiver.getClass().getDeclaredMethod(clickMethodName);// 根据
            if (method != null) {
                // 调用该方法
                method.invoke(receiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {// 传递View监听
            if (method == null) {
                method = receiver.getClass().getMethod(clickMethodName,
                        View.class);
                if (method != null) {
                    method.invoke(receiver, v);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onLongClick(View v) {
        Method method = null;
        try {// 不传递View监听
            method = receiver.getClass().getDeclaredMethod(clickMethodName);// 根据
            if (method != null) {
                // 调用该方法
                method.invoke(receiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {// 传递View监听
            if (method == null) {
                method = receiver.getClass().getMethod(clickMethodName,
                        View.class);
                if (method != null) {
                    method.invoke(receiver, v);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
