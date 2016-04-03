package com.snail.iweibo.util;
import com.snail.iweibo.R;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 表情资源映射
 * Created by alexwan on 16/4/3.
 */
public class Emoticons {

    // 普通表情
    private HashMap<String, Integer> common = new LinkedHashMap<>();
    // 浪小花
    private HashMap<String, Integer> huahua = new LinkedHashMap<>();
    // Emoji表情
    private HashMap<String, Integer> emotions = new LinkedHashMap<>();

    static class Face {
        private static Emoticons instance = new Emoticons();
    }

    private Emoticons() {
        // common
        common.put("[doge]", R.drawable.d_doge);
        common.put("[馋嘴]", R.drawable.d_chanzui);
        common.put("[打脸]", R.drawable.d_dalian);
        common.put("[顶]", R.drawable.d_ding);
        common.put("[防毒面具]", R.drawable.d_fangdumianju);
        common.put("[肥皂]", R.drawable.d_feizao);
        common.put("[挤眼]", R.drawable.d_jiyan);
        common.put("[男孩儿]", R.drawable.d_nanhaier);
        common.put("[女孩儿]", R.drawable.d_nvhaier);
        common.put("[傻眼]", R.drawable.d_shayan);
        common.put("[神兽]", R.drawable.d_shenshou);
        common.put("[兔子]", R.drawable.d_tuzi);
        common.put("[笑哭]", R.drawable.d_xiaoku);
        common.put("[猪头]", R.drawable.d_zhutou);
        common.put("[给力]", R.drawable.f_geili);
        common.put("[互粉]", R.drawable.f_hufen);
        common.put("[囧]", R.drawable.f_jiong);
        common.put("[萌]", R.drawable.f_meng);
        common.put("[喜]", R.drawable.f_xi);
        common.put("[织]", R.drawable.f_zhi);
        common.put("[不要]", R.drawable.h_buyao);
        common.put("[good]", R.drawable.h_good);
        common.put("[握手]", R.drawable.h_woshou);
        common.put("[作揖]", R.drawable.h_zuoyi);
        common.put("[伤心]", R.drawable.l_shangxin);
        common.put("[飞机]", R.drawable.o_feiji);
        common.put("[干杯]", R.drawable.o_ganbei);
        common.put("[话筒]", R.drawable.o_huatong);
        common.put("[礼物]", R.drawable.o_liwu);
        common.put("[绿丝带]", R.drawable.o_lvsidai);
        common.put("[围脖]", R.drawable.o_weibo);
        common.put("[围观]", R.drawable.o_weiguan);
        common.put("[音乐]", R.drawable.o_yinyue);
        common.put("[照相机]", R.drawable.o_zhaoxiangji);
        common.put("[钟]", R.drawable.o_zhong);
        common.put("[浮云]", R.drawable.w_fuyun);
        common.put("[沙尘暴]", R.drawable.w_shachenbao);
        common.put("[太阳]", R.drawable.w_taiyang);
        common.put("[微风]", R.drawable.w_weifeng);
        common.put("[鲜花]", R.drawable.w_xianhua);
        common.put("[下雨]", R.drawable.w_xiayu);
        common.put("[月亮]", R.drawable.w_yueliang);
        common.put("[喵喵]", R.drawable.d_miao);
        common.put("[挖鼻]", R.drawable.d_wabishi);
        common.put("[泪]", R.drawable.d_lei);
        common.put("[亲亲]", R.drawable.d_qinqin);
        common.put("[晕]", R.drawable.d_yun);
        common.put("[可爱]", R.drawable.d_keai);
        common.put("[花心]", R.drawable.d_huaxin);
        common.put("[汗]", R.drawable.d_han);
        common.put("[衰]", R.drawable.d_shuai);
        common.put("[偷笑]", R.drawable.d_touxiao);
        common.put("[打哈欠]", R.drawable.d_dahaqi);
        common.put("[睡觉]", R.drawable.d_shuijiao);
        common.put("[哼]", R.drawable.d_heng);
        common.put("[可怜]", R.drawable.d_kelian);
        common.put("[右哼哼]", R.drawable.d_youhengheng);
        common.put("[酷]", R.drawable.d_ku);
        common.put("[生病]", R.drawable.d_shengbing);
        common.put("[害羞]", R.drawable.d_haixiu);
        common.put("[怒]", R.drawable.d_nu);
        common.put("[闭嘴]", R.drawable.d_bizui);
        common.put("[钱]", R.drawable.d_qian);
        common.put("[嘻嘻]", R.drawable.d_xixi);
        common.put("[左哼哼]", R.drawable.d_zuohengheng);
        common.put("[委屈]", R.drawable.d_weiqu);
        common.put("[鄙视]", R.drawable.d_bishi);
        common.put("[吃惊]", R.drawable.d_chijing);
        common.put("[吐]", R.drawable.d_tu);
        common.put("[懒得理你]", R.drawable.d_landelini);
        common.put("[思考]", R.drawable.d_sikao);
        common.put("[怒骂]", R.drawable.d_numa);
        common.put("[哈哈]", R.drawable.d_haha);
        common.put("[抓狂]", R.drawable.d_zhuakuang);
        common.put("[爱你]", R.drawable.d_aini);
        common.put("[鼓掌]", R.drawable.d_guzhang);
        common.put("[悲伤]", R.drawable.d_beishang);
        common.put("[嘘]", R.drawable.d_xu);
        common.put("[呵呵]", R.drawable.d_hehe);
        common.put("[微笑]", R.drawable.d_hehe);
        common.put("[太开心]", R.drawable.d_taikaixin);
        common.put("[感冒]", R.drawable.d_ganmao);
        common.put("[黑线]", R.drawable.d_heixian);
        common.put("[失望]", R.drawable.d_shiwang);
        common.put("[阴险]", R.drawable.d_yinxian);
        common.put("[困]", R.drawable.d_kun);
        common.put("[拜拜]", R.drawable.d_baibai);
        common.put("[疑问]", R.drawable.d_yiwen);
        common.put("[神马]", R.drawable.f_shenma);
        common.put("[最右]", R.drawable.d_zuiyou);
        common.put("[赞]", R.drawable.h_zan);
        common.put("[心]", R.drawable.l_xin);
        common.put("[奥特曼]", R.drawable.d_aoteman);
        common.put("[蜡烛]", R.drawable.o_lazhu);
        common.put("[蛋糕]", R.drawable.o_dangao);
        common.put("[弱]", R.drawable.h_ruo);
        common.put("[ok]", R.drawable.h_ok);
        common.put("[耶]", R.drawable.h_ye);
        common.put("[威武]", R.drawable.f_v5);
        common.put("[来]", R.drawable.h_lai);
        common.put("[熊猫]", R.drawable.d_xiongmao);
        common.put("[笑cry]", R.drawable.lxh_xiaocry);
        // huahua
        huahua.put("[笑哈哈]", R.drawable.lxh_xiaohaha);
        huahua.put("[好爱哦]", R.drawable.lxh_haoaio);
        huahua.put("[噢耶]", R.drawable.lxh_oye);
        huahua.put("[偷乐]", R.drawable.lxh_toule);
        huahua.put("[泪流满面]", R.drawable.lxh_leiliumanmian);
        huahua.put("[巨汗]", R.drawable.lxh_juhan);
        huahua.put("[抠鼻屎]", R.drawable.lxh_koubishi);
        huahua.put("[求关注]", R.drawable.lxh_qiuguanzhu);
        huahua.put("[好喜欢]", R.drawable.lxh_haoxihuan);
        huahua.put("[崩溃]", R.drawable.lxh_bengkui);
        huahua.put("[好囧]", R.drawable.lxh_haojiong);
        huahua.put("[震惊]", R.drawable.lxh_zhenjing);
        huahua.put("[别烦我]", R.drawable.lxh_biefanwo);
        huahua.put("[不好意思]", R.drawable.lxh_buhaoyisi);
        huahua.put("[羞嗒嗒]", R.drawable.lxh_xiudada);
        huahua.put("[得意地笑]", R.drawable.lxh_deyidexiao);
        huahua.put("[纠结]", R.drawable.lxh_jiujie);
        huahua.put("[给劲]", R.drawable.lxh_feijin);
        huahua.put("[悲催]", R.drawable.lxh_beicui);
        huahua.put("[甩甩手]", R.drawable.lxh_shuaishuaishou);
        huahua.put("[好棒]", R.drawable.lxh_haobang);
        huahua.put("[瞧瞧]", R.drawable.lxh_qiaoqiao);
        huahua.put("[不想上班]", R.drawable.lxh_buxiangshangban);
        huahua.put("[困死了]", R.drawable.lxh_kunsile);
        huahua.put("[许愿]", R.drawable.lxh_xuyuan);
        huahua.put("[丘比特]", R.drawable.lxh_qiubite);
        huahua.put("[有鸭梨]", R.drawable.lxh_youyali);
        huahua.put("[想一想]", R.drawable.lxh_xiangyixiang);
        huahua.put("[转发]", R.drawable.lxh_zhuanfa);
        huahua.put("[互相膜拜]", R.drawable.lxh_xianghumobai);
        huahua.put("[雷锋]", R.drawable.lxh_leifeng);
        huahua.put("[杰克逊]", R.drawable.lxh_jiekexun);
        huahua.put("[玫瑰]", R.drawable.lxh_meigui);
        huahua.put("[hold住]", R.drawable.lxh_holdzhu);
        huahua.put("[群体围观]", R.drawable.lxh_quntiweiguan);
        huahua.put("[推荐]", R.drawable.lxh_tuijian);
        huahua.put("[赞啊]", R.drawable.lxh_zana);
        huahua.put("[被电]", R.drawable.lxh_beidian);
        huahua.put("[霹雳]", R.drawable.lxh_pili);

        emotions.putAll(huahua);
        emotions.putAll(common);
    }

    public static Emoticons getInstance() {
        return Face.instance;
    }

    public HashMap<String, Integer> getCommon() {
        return this.common;
    }

    public HashMap<String, Integer> getHuahua() {
        return this.huahua;
    }

    public HashMap<String, Integer> getEmotions() {
        return this.emotions;
    }

}
