package com.snail.iweibo.util;
import android.content.Context;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.Log;

import com.snail.iweibo.widget.URLClickSpan;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SpanUtil
 * Created by alexwan on 16/4/3.
 */
public class SpanUtil {
    // 超链接
    public static final Pattern WEB_URL = Pattern.compile("http://[a-zA-Z0-9+&@#/%?=~_\\-|!:,\\.;]*[a-zA-Z0-9+&@#/%=~_|]");
    // 话题
    public static final Pattern TOPIC_URL = Pattern.compile("#[\\p{Print}\\p{InCJKUnifiedIdeographs}&&[^#]]+#");
    // @
    public static final Pattern MENTION_URL = Pattern.compile("@[\\w\\p{InCJKUnifiedIdeographs}-]{1,26}");
    // 表情
    public static final Pattern EMOTION_URL = Pattern.compile("\\[(\\S+?)\\]");

    public static final String WEB_SCHEME = "http://";
    public static final String TOPIC_SCHEME = "com.snail.iweibo.topic://";
    public static final String MENTION_SCHEME = "com.snail.iweibo://";
    public static SpannableString buildSpan(Context context , String text){
        if(TextUtils.isEmpty(text)){
            return null;
        }
        String content;
        // 是否是纯表情
        if(text.startsWith("[") && text.endsWith("]")){
            content = text + "";
        }else{
            content = text;
        }
        // 格式化超链接
        String link = linkFormat(content);
        Spanned spanned = Html.fromHtml(link);
        // 格式化@和话题内容
        SpannableString ss = SpannableString.valueOf(spanned);
        Linkify.addLinks(ss, WEB_URL, WEB_SCHEME);
        Linkify.addLinks(ss, TOPIC_URL, TOPIC_SCHEME);
        Linkify.addLinks(ss, MENTION_URL, MENTION_SCHEME);
        URLSpan[] urls = ss.getSpans(0 , ss.length() , URLSpan.class);
        // 添加可点击
        for (URLSpan url : urls){
            URLClickSpan clickSpan = new URLClickSpan(context , url.getURL());
            int start = ss.getSpanStart(url);
            int end = ss.getSpanEnd(url);
            ss.removeSpan(url);
            ss.setSpan(clickSpan , start , end , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        // 格式化表情
        return buildEmotion(context , ss);
    }

    /**
     * 格式化超链接
     * @param link link
     * @return String
     */
    private static String linkFormat(String link){
        String format = "<a href=\"%s\">☞ 网页链接</a>";
        Matcher matcher = WEB_URL.matcher(link);
        while (matcher.find()){
            int start = matcher.start();
            int end = matcher.end();
            String replace = link.substring(start , end);
            String src = String.format(format , replace);
            if(!link.contains(src)){
                // 多个超链接"http://"超链接内容
                return link.replace(replace , src);
            }
        }
        return link;
    }
    /**
     * 表情
     * @param context context
     * @param ss ss
     * @return SpannableString
     */
    public static SpannableString buildEmotion(Context context, SpannableString ss){
        Emoticons emoticons = Emoticons.getInstance();
        HashMap<String , Integer> emotions = emoticons.getEmotions();
        Matcher matcher = EMOTION_URL.matcher(ss);
        while (matcher.find()){
            String key = matcher.group(0);
            if(emotions.containsKey(key)){
                int start = matcher.start();
                int end = matcher.end();
                if(end - start < 8){
                    Log.i("StatusListAdapter" , "Spaned - start : " + start + " end : " + end + " key : " + key +
                        " value : " + emotions.get(key) );
                    ImageSpan imageSpan = new ImageSpan(context , emotions.get(key));
                    ss.setSpan(imageSpan , start , end , Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                }
            }
        }
        return ss;
    }
}
