package test3.ncxchile.cl.acta;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import test3.ncxchile.cl.POJO.ImageItem;
import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.widgets.GridViewAdapter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SimpleCameraIntentFragment extends Fragment {

        private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
        static final int REQUEST_TAKE_PHOTO = 1;
        Button button;
        ImageView imageView;
        ArrayList<ImageItem> items= new ArrayList<ImageItem>(15);
        GridView gridView;
        private GridViewAdapter customGridAdapter;
        Intent photoIntent;
        View rootView;
        int cont=0;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

            rootView = inflater.inflate(R.layout.fragment_simple_camera_intent,container, false);

            button = (Button) rootView.findViewById(R.id.button_foto);
            //items.add(new ImageItem(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.placeholder),""));

            ImageItem placeHolder=new ImageItem(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.placeholder),"");
            items.add(placeHolder);
            items.add(placeHolder);
            items.add(placeHolder);
            items.add(placeHolder);
            items.add(placeHolder);
            items.add(placeHolder);
            items.add(placeHolder);

            gridView = (GridView) rootView.findViewById(R.id.imageViewThumbnail);
            customGridAdapter = new GridViewAdapter(getActivity(), R.layout.image_item, items);
            gridView.setAdapter(customGridAdapter);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
                    startActivityForResult(photoIntent,CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                }
            });

            gridView.setOnItemClickListener(clickImage);

            //horizontalListView.setAdapter(new HAdapter());
            return rootView;
        }

        public Uri setImageUri() {
            // Store image in dcim
            //File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".png");
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "OS_1_" + timeStamp + ".png";
            File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File file= new File(storageDir.getAbsolutePath()+"/",imageFileName);
            Uri imgUri = Uri.fromFile(file);
            System.out.print("imgUri="+imgUri);
            mCurrentPhotoPath = file.getAbsolutePath();
            return imgUri;
        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_CANCELED) {
             if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                items.set(cont,new ImageItem(decodeFile(mCurrentPhotoPath),mCurrentPhotoPath));
                gridView.setAdapter(customGridAdapter);
                ++cont;
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    public Bitmap decodeFile(String path) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, o);
            // The new size we want to scale to
            final int REQUIRED_SIZE = 70;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeFile(path, o2);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;

    }

    String mCurrentPhotoPath;

    private void setPic(View v) {
        // Get the dimensions of the View
        int targetW = v.getWidth();
        int targetH = v.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }

    AdapterView.OnItemClickListener clickImage = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            ImageItem item= (ImageItem)adapterView.getItemAtPosition(i);

            alertDialog.setTitle(item.getPath());

            // Get the dimensions of the View
            int targetW = 200;
            int targetH = 200;

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

            LayoutInflater factory = LayoutInflater.from(getActivity());
            final View mView = factory.inflate(R.layout.sample, null);
            ImageView mImageView= (ImageView)mView.findViewById(R.id.bigImage);
            mImageView.setImageBitmap(bitmap);
            alertDialog.setView(mView);

            //alertDialog.setContentView(imageView);
            //alertDialog.setIcon(R.drawable.luzverde);
            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();
        }
    };

}