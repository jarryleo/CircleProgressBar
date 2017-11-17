package cn.leo.circleprogressbar.view;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Leo on 2017/11/17.
 */

public class ClickStringUtil {
    /**
     * 给文本框字符设置点击事件
     *
     * @param textView
     * @param textColor 可点击文字颜色
     * @param listener  点击回调（回调参数为点击文字的内容，可根据文字执行不同动作）
     * @param position  可点击文字起始和终止位置，2个一组
     */
    public static void setClickText(TextView textView, final int textColor, final boolean underline, final onTextClickListener listener, int... position) {
        String text = textView.getText().toString();
        SpannableString spannableString = new SpannableString(text);
        if (position.length % 2 != 0) return;
        for (int i = 0; i < position.length; i += 2) {
            int s = position[i];
            int e = position[i + 1];
            final String str = text.substring(s, e);
            //设置下划线文字
            //spannableString.setSpan(new UnderlineSpan(), s, e, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //设置文字的单击事件
            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    if (listener != null) listener.onClick(str);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setColor(textColor); //点击文字颜色
                    ds.setUnderlineText(underline);//false去掉下划线
                }
            }, s, e, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //设置文字的前景色
            //spannableString.setSpan(new ForegroundColorSpan(textColor), s, e, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        textView.setHighlightColor(Color.TRANSPARENT);//设置选中文字高亮颜色
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public interface onTextClickListener {
        void onClick(String text);
    }

}
