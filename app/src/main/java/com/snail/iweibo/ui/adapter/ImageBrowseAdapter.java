package com.snail.iweibo.ui.adapter;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils.ScaleType;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.snail.iweibo.R;
import com.snail.iweibo.widget.fresco.ZoomableDraweeView;

import java.util.List;

/**
 * ImageBrowseAdapter
 * Created by alexwan on 16/4/21.
 */
public class ImageBrowseAdapter extends PagerAdapter {

    private List<String> urls;
    private Context context;

    public ImageBrowseAdapter(Context context, List<String> urls) {
        this.context = context;
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return urls == null ? 0 : urls.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        final String url = urls.get(position);
        ZoomableDraweeView image = (ZoomableDraweeView) LayoutInflater.from(context)
                                                                      .inflate(R.layout.item_image_layout, container, false);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(UriUtil.parseUriOrNull(url))
                                                  .build();
        DraweeController ctrl = Fresco.newDraweeControllerBuilder()
                                      .setTapToRetryEnabled(true)
                                      .setImageRequest(request)
                                      .build();
        GenericDraweeHierarchy hierarchy =
            new GenericDraweeHierarchyBuilder(context.getResources())
                .setActualImageScaleType(ScaleType.FIT_CENTER)
                .build();
        image.setController(ctrl);
        image.setHierarchy(hierarchy);
        container.addView(image);
        return image;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    public Context getContext() {
        return context;
    }
}
