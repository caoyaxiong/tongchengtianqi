package com.tongchengtianqi.zuitoutiao.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tongchengtianqi.zuitoutiao.R;
import com.tongchengtianqi.zuitoutiao.entity.MultipleBeanBase;
import com.tongchengtianqi.zuitoutiao.utils.GlideUtils;
import com.tongchengtianqi.zuitoutiao.utils.TimeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*参1是数据实体类，参2是ViewHolder*/
public class HomeMultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MultipleBeanBase, BaseViewHolder> {
    Context context;
    private JSONObject bean;

    public HomeMultipleItemQuickAdapter(Context context) {
        super(new ArrayList<MultipleBeanBase>());
        this.context = context;
        addItemType(1, R.layout.recyclerview_item_three_text);  //文字+小图
        addItemType(2, R.layout.recyclerview_item_home_videoview); //视频
        addItemType(3, R.layout.recyclerview_item_one_img);     //一张大图
        addItemType(4, R.layout.recyclerview_item_plain_text); //纯文字
        addItemType(5, R.layout.recyclerview_item_three_img); //三张小图
    }

    /*参2为实体类*/
    @Override
    protected void convert(BaseViewHolder helper, MultipleBeanBase item) {
        int itemType = item.getItemType();
        bean = (JSONObject) item.getObject();
        switch (itemType) {
            //文字+小图
            case 1:
                bindTypeTextAndImgData(helper);
                break;

            //视频
            case 2:
                    bindTypeVideoData(helper);
                break;
            // 大图
            case 3:
                bindTypeOneImgData(helper);
                break;
            //纯文字
            case 4:
                bindTypePlainTextData(helper);
                break;
            // 3张小图
            case 5:
                bindTypeThreeImgData(helper);
                break;
            default:
                break;
        }
    }

    //视频
    private void bindTypeVideoData(BaseViewHolder helper){
        //给条目的子控件添加点击事件监听器
        helper.addOnClickListener(R.id.recycler_item_delete_img_videoview);
        helper.setText(R.id.home_video_title_content_text, bean.optString("title"));
        helper.setText(R.id.recycler_item_name_text_videoview, bean.optString("source_name"));
        helper.setText(R.id.recycler_item_comment_text_videoview, "" + bean.optString("comment_count"));
        helper.setText(R.id.recycler_item_time_video_view, TimeUtils.createTimeToString(bean.optLong("create_time")));
        ImageView videoViewImg = helper.getView(R.id.recycler_item_videoview_img);
        JSONArray thumbnailsVideoView = bean.optJSONArray("videos");
        JSONArray thumbnailsVideoViewImg = bean.optJSONArray("thumbnails");
        if (thumbnailsVideoView != null) {
            JSONObject jsonObject = null;
            try {
                jsonObject = thumbnailsVideoView.getJSONObject(0);
                helper.setText(R.id.recycler_item_videoview_time, TimeUtils.getDuration((int)
                        jsonObject.optLong("duration")));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (thumbnailsVideoViewImg != null) {
            JSONObject jsonObject = null;
            try {
                jsonObject = thumbnailsVideoViewImg.getJSONObject(0);
                GlideUtils.loadImageViewLoding(context, jsonObject.optString("url"), videoViewImg,
                        R.drawable.img_news_default, R.drawable.icon_not_find);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //纯文字
    private void bindTypePlainTextData(BaseViewHolder helper) {
        helper.addOnClickListener(R.id.recycler_item_delete_img_plain_text);
        if (bean.optString("summary").equals("") && bean.optString("content").equals("")) {
            helper.setText(R.id.recycler_item_plain_text, bean.optString("title"));
        } else if (bean.optString("content").equals("") && bean.optString("title").equals("")) {
            helper.setText(R.id.recycler_item_plain_text, bean.optString("summary"));
        } else {
            helper.setText(R.id.recycler_item_plain_text, bean.optString("content"));
        }
        helper.setText(R.id.recycler_item_name_text_plain_text, bean.optString("source_name"));
        helper.setText(R.id.recycler_item_comment_text_plain_text, "" + bean.optString("comment_count"));
        helper.setText(R.id.recycler_item_time_plain_text, TimeUtils.createTimeToString(bean.optLong("create_time")));
    }

    //文字+小图
    private void bindTypeTextAndImgData(BaseViewHolder helper) {
        helper.addOnClickListener(R.id.recycler_item_delete_img_first);
        helper.setText(R.id.recycler_item_describe_text_first, bean.optString("title"));
        helper.setText(R.id.recycler_item_real_time_first, bean.optString("summary"));
        helper.setText(R.id.recycler_item_name_text_first, bean.optString("source_name"));
        helper.setText(R.id.recycler_item_comment_text_first, "" + bean.optString("comment_count"));
        helper.setText(R.id.recycler_item_time_first, TimeUtils.createTimeToString(bean.optLong("create_time")));
        ImageView mImageView = helper.getView(R.id.recycler_item_first_img);
        JSONArray thumbnailsTextAndData = bean.optJSONArray("thumbnails");
        if (thumbnailsTextAndData != null) {
            JSONObject jsonObject = null;
            try {
                jsonObject = thumbnailsTextAndData.getJSONObject(0);
                String urlParse = jsonObject.optString("url") + "&width=320&height=240";
                if (jsonObject != null) {
                    GlideUtils.loadImageViewLoding(context, urlParse, mImageView, R.drawable.
                            img_news_default, R.drawable.icon_not_find);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //三张小图
    private void bindTypeThreeImgData(BaseViewHolder helper) {
        helper.addOnClickListener(R.id.recycler_item_delete_img_third);
        helper.setText(R.id.recycler_item_describe_text_third, bean.optString("title"));
        helper.setText(R.id.recycler_item_real_time_third, bean.optString("summary"));
        helper.setText(R.id.recycler_item_name_text_third, bean.optString("source_name"));
        helper.setText(R.id.recycler_item_comment_text_third, "" + bean.optInt("comment_count"));
        helper.setText(R.id.recycler_item_time_third, TimeUtils.
                createTimeToString(bean.optLong("create_time")));
        ImageView mThirdImgOne = helper.getView(R.id.recycler_item_third_img_1);
        ImageView mThirdImgTwo = helper.getView(R.id.recycler_item_third_img_2);
        ImageView mThirdImgThree = helper.getView(R.id.recycler_item_third_img_3);
        JSONArray thumbnails = bean.optJSONArray("thumbnails");
        if (thumbnails != null && thumbnails.length() >= 3) {
            for (int i = 0; i < thumbnails.length(); i++) {
                try {
                    JSONObject jsonObject = thumbnails.getJSONObject(i);
                    if (jsonObject != null && i == 0) {
                        String urlParse0 = jsonObject.optString("url") + "&width=320&height=240";
                        GlideUtils.loadImageViewLoding(context, urlParse0,
                                mThirdImgOne, R.drawable.img_news_default, R.drawable.icon_not_find);
                    } else if (jsonObject != null && i == 1) {
                        String urlParse1 = jsonObject.optString("url") + "&width=320&height=240";
                        GlideUtils.loadImageViewLoding(context, urlParse1,
                                mThirdImgTwo, R.drawable.img_news_default, R.drawable.icon_not_find);
                    } else if (jsonObject != null && i == 2) {
                        String urlParse2 = jsonObject.optString("url") + "&width=320&height=240";
                        GlideUtils.loadImageViewLoding(context, urlParse2,
                                mThirdImgThree, R.drawable.img_news_default, R.drawable.icon_not_find);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //一张大图
    private void bindTypeOneImgData(BaseViewHolder helper) {
        helper.addOnClickListener(R.id.recycler_item_delete_img_videoview);
        helper.setText(R.id.title_content_text, bean.optString("title"));
        helper.setText(R.id.recycler_item_real_time_videoview, bean.optString("summary"));
        helper.setText(R.id.recycler_item_name_text_videoview, bean.optString("source_name"));
        helper.setText(R.id.recycler_item_comment_text_videoview, "" + bean.optInt("comment_count"));
        helper.setText(R.id.recycler_item_time_videoview, TimeUtils.createTimeToString(bean.optLong("create_time")));
        ImageView mOneVideoViewPlay = helper.getView(R.id.recycler_item_one_videoview_play);
        JSONArray thumbnailsTextAndData = bean.optJSONArray("thumbnails");
        if (thumbnailsTextAndData != null) {
            JSONObject jsonObject = null;
            try {
                jsonObject = thumbnailsTextAndData.getJSONObject(0);
                String urlParseBig = jsonObject.optString("url") + "&width=640&height=240";
                if (jsonObject != null) {
                    GlideUtils.loadImageViewLoding(context, urlParseBig, mOneVideoViewPlay, R.drawable.
                            img_news_default, R.drawable.icon_not_find);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

