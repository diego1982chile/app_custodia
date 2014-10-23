package test3.ncxchile.cl.widgets;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;

import test3.ncxchile.cl.POJO.VideoItem;
import test3.ncxchile.cl.login.R;

/**
 * Created by android-developer on 23-10-2014.
 */
public class GridViewVideoAdapter extends ArrayAdapter {

    private Context context;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();

    public GridViewVideoAdapter(Context context, int layoutResourceId,ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.path = (TextView) row.findViewById(R.id.textVideo);
            holder.image = (ImageView) row.findViewById(R.id.thumbnail);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        VideoItem item = (VideoItem)data.get(position);
        holder.path.setText(item.getPath());
        //holder.video.setVideoURI(item.getUri());
        holder.image.setImageBitmap(item.getImage());
        return row;
    }

    static class ViewHolder {
        ImageView image;
        TextView path;
        //VideoView video;
    }
}
