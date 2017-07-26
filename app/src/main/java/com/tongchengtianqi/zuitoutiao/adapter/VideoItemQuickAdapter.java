package com.tongchengtianqi.zuitoutiao.adapter;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import com.tongchengtianqi.zuitoutiao.R;
import com.tongchengtianqi.zuitoutiao.entity.VideoFragmentBean;
import com.tongchengtianqi.zuitoutiao.utils.GlideUtils;


public class VideoItemQuickAdapter extends BaseQuickAdapter<VideoFragmentBean, BaseViewHolder> {
    private Context context;
    private JSONObject article;
    private JSONObject jsonObject;


    public VideoItemQuickAdapter(Activity context, JSONObject article) {
            super((List<VideoFragmentBean>) article);

        this.context = context;
        this.article = article;
    }


    @Override
    protected void convert(BaseViewHolder helper, VideoFragmentBean item) {
        if (article != null) {
            helper.setText(R.id.video_title_content_text,article.optString("title"));
            helper.setText(R.id.recycler_item_video_time,article.optString("duration"));
            helper.setText(R.id.video_name_text,article.optString("source_name"));
            helper.setText(R.id.video_comment_name_text,article.optInt("comment_count"));
            ImageView videoImg = helper.getView(R.id.recycler_item_video_img);
            JSONArray videos = article.optJSONArray("videos");
            if (videos!=null) try {
                jsonObject = videos.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            GlideUtils.loadImageViewLoding(context,jsonObject.optString("url"),videoImg,
                    R.drawable.img_news_default, R.drawable.icon_not_find);

        }

    }
}
