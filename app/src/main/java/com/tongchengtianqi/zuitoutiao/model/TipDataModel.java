package com.tongchengtianqi.zuitoutiao.model;


import java.util.ArrayList;
import java.util.List;

import com.tongchengtianqi.zuitoutiao.easytagdragview.bean.SimpleTitleTip;
import com.tongchengtianqi.zuitoutiao.easytagdragview.bean.Tip;
import com.tongchengtianqi.zuitoutiao.entity.TabTextBean;

public class TipDataModel {
    private static String[] dragTips ={"头条","热点","娱乐","体育","财经","科技","段子","轻松一刻","军事","历史","游戏","时尚","NBA"
            ,"漫画","社会","中国足球","手机"};
    private static String[] addTips ={"数码","移动互联","云课堂","家居","旅游","健康","读书","跑步","情感","政务","艺术","博客"};
    public static List<Tip>  getDragTips(){
        List<Tip> result = new ArrayList<>();
        for(int i=0;i<dragTips.length;i++){
            String temp =dragTips[i];
            SimpleTitleTip tip = new SimpleTitleTip();
            tip.setTip(temp);
            tip.setId(i);
            result.add(tip);
        }
        return result;
    }
    public static List<Tip> getAddTips(List<TabTextBean.DataBean.ChannelsBean> channels, List<String> strings){
        List<Tip> result = new ArrayList<>();
        for(int i=0;i<channels.size();i++){
            String temp =channels.get(i).getCname();
            if(strings.contains(temp)){
                continue;
            }
            SimpleTitleTip tip = new SimpleTitleTip();
            tip.setTip(temp);
            tip.setId(i+dragTips.length);
            result.add(tip);
        }
        return result;
    }
    public static List<Tip> getStringAddTips(List<String> channels){
        List<Tip> result = new ArrayList<>();
        for(int i=0;i<channels.size();i++){
            String temp =channels.get(i);
            SimpleTitleTip tip = new SimpleTitleTip();
            tip.setTip(temp);
            tip.setId(i+dragTips.length);
            result.add(tip);
        }
        return result;
    }
}
