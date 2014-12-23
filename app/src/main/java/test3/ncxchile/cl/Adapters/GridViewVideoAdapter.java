package test3.ncxchile.cl.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;

import test3.ncxchile.cl.POJO.VideoItem;
import test3.ncxchile.cl.acta.FragmentX5;
import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.session.SessionManager;

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
        SessionManager session = new SessionManager(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.path = (TextView) row.findViewById(R.id.textVideo);
            holder.image = (ImageView) row.findViewById(R.id.thumbnail);
            holder.icon = (ImageView) row.findViewById(R.id.icon_video);
            holder.icon.setVisibility(View.VISIBLE);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        final VideoItem item = (VideoItem)data.get(position);
        holder.path.setText(item.getPath());
        //holder.video.setVideoURI(item.getUri());
        holder.image.setImageBitmap(item.getImage());
        holder.icon.setImageBitmap(item.getIcon());

        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Confirmación");
                alertDialog.setMessage("¿Estás seguro de eliminar esta foto?");
                alertDialog.setIcon(R.drawable.luzverde);
                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        File file = new File(item.getPath());
                        boolean deleted = file.delete();
                        if(deleted){
                            remove(item);
                            add(new VideoItem(BitmapFactory.decodeResource(context.getResources(), R.drawable.video_placeholder),
                                    BitmapFactory.decodeResource(context.getResources(), R.drawable.photo_placeholder_small),
                                    Uri.parse(context.getPackageName() + R.drawable.video_placeholder),""));
                            /*
                            insert(new VideoItem(BitmapFactory.decodeResource(context.getResources(), R.drawable.video_placeholder),
                                                 BitmapFactory.decodeResource(context.getResources(), R.drawable.photo_placeholder),
                                                 Uri.parse(context.getPackageName() + R.drawable.video_placeholder),""), position);
                            */                            
                            FragmentX5.contVideos--;
                            //SessionManager session = new SessionManager(context);
                            //session.setCantidadVideos(session.getCantidadVideos() - 1);
                            //imageGridView.setAdapter(imageGridAdapter);
                        }
                    }
                });
                alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // here you can add functions
                    }
                });
                alertDialog.show();
            }
        });

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();

                alertDialog.setTitle(item.getPath());

                LayoutInflater factory = LayoutInflater.from(context);
                final View mView = factory.inflate(R.layout.video_sample, null);
                VideoView mVideoView = (VideoView) mView.findViewById(R.id.bigVideo);
                //System.out.println(item.getUri());
                mVideoView.setVideoURI(item.getUri());
                alertDialog.setView(mView);

                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialog.show();
                mVideoView.start();
            }
        });

        return row;
    }

    static class ViewHolder {
        ImageView image;
        ImageView icon;
        TextView path;
        //VideoView video;
    }
}
