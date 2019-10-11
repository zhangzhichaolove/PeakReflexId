package peak.chao.reflexid;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class BindId {
    private static final String TAG = "BindId";

    public static final int OnClick = 0;
    public static final int OnLongClick = 1;

    private BindId() {
        throw new UnsupportedOperationException("不能通过此私有构造创建实例。");
    }


    public static <T> void bind(T target) {
        Activity at = null;
        if (target instanceof Activity) {
            at = (Activity) target;
        } else if (target instanceof Fragment) {
            Fragment fr = (Fragment) target;
            at = fr.getActivity();
        }
        Field[] declaredFields = target.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            //获取此变量上的Id注解
            Id idAnnotation = field.getAnnotation(Id.class);
            //如果此变量有Id注解并且注解传入了ID值
            if (idAnnotation != null) {
                int id = idAnnotation.value();
                if (id != 0) {
                    if (Modifier.toString(field.getModifiers()).equals("private")) {
                        field.setAccessible(true);
                    }
                    try {
                        Object view = at.findViewById(id);
                        if (view != null) {
                            field.set(target, view);
                        } else {
                            String typeName = field.getGenericType().getTypeName();
                            Class<?> clazz = Class.forName(typeName);
                            Constructor<?> cons = clazz.getConstructor(Context.class);
                            //clazz.getConstructors()[0].getGenericParameterTypes();//获取构造器的参数类型,根据不同类型构造创建实例。
                            Object newInstance = cons.newInstance(at);
                            field.set(target, newInstance);
                            Log.e(TAG, field.getName() + " >> ID未找到!");
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    if (Modifier.toString(field.getModifiers()).equals("private")) {
                        field.setAccessible(false);
                    }
                }
            }
        }

        Method[] methods = at.getClass().getDeclaredMethods();
        for (Method method : methods) {
            // 获取该方法上面有没有打这个注解
            OnClick mClick = method.getAnnotation(OnClick.class);
            if (mClick != null) {// 有此注解
                int id = mClick.value();// 获取默认注解值ID
                int type = mClick.type();
                if (id != 0) {
                    //Log.e("TAG", "找到了该ID ：" + id);
                    // 反射执行
                    View view = at.findViewById(id);// 找到这个设置监听的View
                    if (type == OnClick) {// 选择了默认点击事件。
                        view.setOnClickListener(new OnClickListener(at, method
                                .getName()));
                    } else if (type == OnLongClick) {// 长按事件。
                        view.setOnLongClickListener(new OnClickListener(at,
                                method.getName()));
                    }
                }
            }
        }
    }


    public static <T> void bindView(T target, View contentView) {
        Field[] declaredFields = target.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            //获取此变量上的Id注解
            Id idAnnotation = field.getAnnotation(Id.class);
            //如果此变量有Id注解并且注解传入了ID值
            if (idAnnotation != null) {
                int id = idAnnotation.value();
                if (id != 0) {
                    if (Modifier.toString(field.getModifiers()).equals("private")) {
                        field.setAccessible(true);
                    }
                    try {
                        Object view = contentView.findViewById(id);
                        if (view != null) {
                            field.set(target, view);
                        } else {
                            Log.e(TAG, field.getName() + " >> ID未找到!");
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if (Modifier.toString(field.getModifiers()).equals("private")) {
                        field.setAccessible(false);
                    }
                }
            }
        }
    }

    public static void ubBind() {

    }

}
