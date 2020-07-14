package qtc.project.pos.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import qtc.project.pos.R;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.helper.Consts;

public class SlidingImage_Adapter extends PagerAdapter {

    private final LayoutInflater inflater;

    private ArrayList<String> IMAGE_LINK;
    private ArrayList<Integer> IMAGE_RESOURCE;
    private final SliderImageType type;
    private boolean showLoading = true;

    public SlidingImage_Adapter(Context context, SliderImageType type) {
        this.type = type;
        inflater = LayoutInflater.from(context);
    }

    public void setShowLoading(boolean showLoading) {
        this.showLoading = showLoading;
    }

    public void setIMAGE_LINK(ArrayList<String> IMAGE_LINK) {
        this.IMAGE_LINK = IMAGE_LINK;
    }

    public void setIMAGE_RESOURCE(ArrayList<Integer> IMAGE_RESOURCE) {
        this.IMAGE_RESOURCE = IMAGE_RESOURCE;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        if (type == SliderImageType.LINK)
            return IMAGE_LINK != null ? IMAGE_LINK.size() : 1;
        else
            return IMAGE_RESOURCE != null ? IMAGE_RESOURCE.size() : 1;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = imageLayout
                .findViewById(R.id.image);

        View loading_view = imageLayout.findViewById(R.id.loading_view);

        loading_view.setVisibility(showLoading ? View.VISIBLE : View.GONE);

        if (type == SliderImageType.LINK) {
            if (IMAGE_LINK != null && IMAGE_LINK.size() > 0 && !TextUtils.isEmpty(IMAGE_LINK.get(position))) {
                String imageLink = Consts.HOST_API + IMAGE_LINK.get(position);
                AppProvider.getImageHelper().displayImage(imageLink, imageView, loading_view, R.drawable.no_image_full);
            } else {
                imageView.setImageResource(R.drawable.no_image_full);
            }
        } else {
            imageView.setImageResource(IMAGE_RESOURCE.get(position));
        }

        view.addView(imageLayout, 0);


        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


    public enum SliderImageType {
        LINK,
        RESOURCE
    }
}
