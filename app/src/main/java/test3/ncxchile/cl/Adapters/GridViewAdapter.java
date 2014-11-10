package test3.ncxchile.cl.adapters;

/**
 * Created by android-developer on 22-10-2014.
 */
import java.io.File;
import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import test3.ncxchile.cl.POJO.ImageItem;
import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.session.SessionManager;


public class GridViewAdapter extends ArrayAdapter  {

    private Context context;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();

    public GridViewAdapter(Context context, int layoutResourceId,ArrayList data) {
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
            holder.path = (TextView) row.findViewById(R.id.text);
            holder.image = (ImageView) row.findViewById(R.id.img);
            holder.icon = (ImageView) row.findViewById(R.id.icon);
            holder.icon.setVisibility(View.VISIBLE);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        final ImageItem item = (ImageItem)data.get(position);
        holder.path.setText(item.getPath());
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
                            /*
                            insert(new ImageItem(BitmapFactory.decodeResource(context.getResources(), R.drawable.photo_placeholder),
                                                 BitmapFactory.decodeResource(context.getResources(), R.drawable.photo_placeholder),
                                                 Uri.parse(context.getPackageName() + R.drawable.video_placeholder),""),position);
                            */
                            remove(item);

                            add(new ImageItem(BitmapFactory.decodeResource(context.getResources(), R.drawable.photo_placeholder),
                                    BitmapFactory.decodeResource(context.getResources(), R.drawable.photo_placeholder),
                                    Uri.parse(context.getPackageName() + R.drawable.video_placeholder),""));
                            SessionManager session = new SessionManager(context);
                            session.setCantidadFotos(session.getCantidadFotos() - 1);
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
                //System.out.println("id="+v.getId());

                alertDialog.setTitle(item.getPath());

                // Get the dimensions of the View
                int targetW = 400;
                int targetH = 400;

                // Get the dimensions of the bitmap
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bmOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(item.getPath(), bmOptions);
                int photoW = bmOptions.outWidth;
                int photoH = bmOptions.outHeight;

                // Determine how much to scale down the image
                int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

                // Decode the image file into a Bitmap sized to fill the View
                bmOptions.inJustDecodeBounds = false;
                bmOptions.inSampleSize = scaleFactor;
                bmOptions.inPurgeable = true;

                Bitmap bitmap;
                int rotateImage = getCameraPhotoOrientation(context, item.getUri(), item.getPath());

                if(rotateImage==0) {
                    bitmap = BitmapFactory.decodeFile(item.getPath(), bmOptions);
                }
                else {
                    // create a matrix object
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90); // anti-clockwise by 90 degrees

                    // create a new bitmap from the original using the matrix to transform the result
                    bitmap= Bitmap.createBitmap(BitmapFactory.decodeFile(item.getPath(), bmOptions), 0, 0, bmOptions.outWidth, bmOptions.outHeight, matrix, true);
                }

                LayoutInflater factory = LayoutInflater.from(context);
                final View mView = factory.inflate(R.layout.image_sample, null);
                ImageView mImageView = (ImageView) mView.findViewById(R.id.bigImage);
                mImageView.setImageBitmap(bitmap);
                alertDialog.setView(mView);

                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialog.show();
            }
        });

        //row.setTag(holder);
        return row;
    }

    static class ViewHolder {
        TextView path;
        ImageView image;
        ImageView icon;
    }

    public int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath){
        int rotate = 0;
        try {
            context.getContentResolver().notifyChange(imageUri, null);
            File imageFile = new File(imagePath);

            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }

            //System.out.println("Exif orientation: " + orientation);
            //System.out.println("Rotate value: " + rotate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

}