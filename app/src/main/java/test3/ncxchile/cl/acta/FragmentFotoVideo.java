package test3.ncxchile.cl.acta;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import test3.ncxchile.cl.POJO.ImageItem;
import test3.ncxchile.cl.POJO.VideoItem;
import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.widgets.GridViewAdapter;
import test3.ncxchile.cl.widgets.GridViewVideoAdapter;

/**
 * Created by android-developer on 23-10-2014.
 */
public class FragmentFotoVideo extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    static final int REQUEST_VIDEO_CAPTURE = 1;

    static final int REQUEST_TAKE_PHOTO = 1;
    // Elementos legados de FragmentX5
    public CheckBox img, vid, adjuntar;
    public TextView textMotivo;
    public EditText motivo;
    //////////////////////////////
    Button button,buttonVideo;
    ImageView imageView;
    ArrayList<ImageItem> imageItems = new ArrayList<ImageItem>(15);
    ArrayList<VideoItem> videoItems = new ArrayList<VideoItem>(5);
    GridView imageGridView;
    GridView videoGridView;
    private GridViewAdapter imageGridAdapter;
    private GridViewVideoAdapter videoGridAdapter;
    Intent photoIntent;
    View rootView;
    int cont = 0;

    public FragmentFotoVideo newInstance(int sectionNumber){
        FragmentFotoVideo fragment = new FragmentFotoVideo();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_fotovideo, container, false);
        motivo = (EditText) rootView.findViewById(R.id.motivo);
        adjuntar= (CheckBox) rootView.findViewById(R.id.adjuntar_imagenvideo);
        textMotivo= (TextView) rootView.findViewById(R.id.textMotivo);

        button = (Button) rootView.findViewById(R.id.button_foto);
        buttonVideo = (Button) rootView.findViewById(R.id.button_video);
        //items.add(new ImageItem(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.placeholder),""));

        ImageItem imagePlaceHolder = new ImageItem(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.placeholder), "");
        imageItems.add(imagePlaceHolder);
        imageItems.add(imagePlaceHolder);
        imageItems.add(imagePlaceHolder);
        imageItems.add(imagePlaceHolder);
        imageItems.add(imagePlaceHolder);
        imageItems.add(imagePlaceHolder);
        imageItems.add(imagePlaceHolder);
        imageItems.add(imagePlaceHolder);
        imageItems.add(imagePlaceHolder);
        imageItems.add(imagePlaceHolder);

        VideoItem videoPlaceHolder= new VideoItem(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.placeholder),
                                                  Uri.parse(getActivity().getPackageName() + R.drawable.placeholder),"");
        videoItems.add(videoPlaceHolder);
        videoItems.add(videoPlaceHolder);
        videoItems.add(videoPlaceHolder);
        videoItems.add(videoPlaceHolder);
        videoItems.add(videoPlaceHolder);

        imageGridView = (GridView) rootView.findViewById(R.id.imageViewThumbnail);
        imageGridAdapter = new GridViewAdapter(getActivity(), R.layout.image_item, imageItems);
        imageGridView.setAdapter(imageGridAdapter);

        videoGridView = (GridView) rootView.findViewById(R.id.videoViewThumbnail);
        videoGridAdapter = new GridViewVideoAdapter(getActivity(), R.layout.video_item, videoItems);
        videoGridView.setAdapter(videoGridAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri(1));
                startActivityForResult(photoIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        buttonVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                //photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri(2));
                setImageUri(2);
                startActivityForResult(photoIntent, REQUEST_VIDEO_CAPTURE);
            }
        });

        imageGridView.setOnItemClickListener(clickImage);

        adjuntar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b) {
                    textMotivo.setVisibility(View.VISIBLE);
                    motivo.setVisibility(View.VISIBLE);
                }
                else {
                    textMotivo.setVisibility(View.GONE);
                    motivo.setVisibility(View.GONE);
                }
            }
        });

        //horizontalListView.setAdapter(new HAdapter());
        return rootView;
    }

    public Uri setImageUri(int media) {
        // Store image in dcim
        //File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".png");
        File storageDir;

        storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String imageFileName="";

        switch (media){
            case 1:
                imageFileName = "/OS_1/FOTOS/" + timeStamp + ".png";
                break;
            case 2:
                imageFileName = "/OS_1/VIDEOS/" + timeStamp + ".mp4";
                break;
        }

        File file = new File(storageDir.getAbsolutePath(), imageFileName);

        Uri imgUri = Uri.fromFile(file);
        System.out.print("imgUri=" + imgUri);
        mCurrentPhotoPath = file.getAbsolutePath();
        return imgUri;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                imageItems.set(cont, new ImageItem(decodeFile(mCurrentPhotoPath), mCurrentPhotoPath));
                imageGridView.setAdapter(imageGridAdapter);
                ++cont;
            } else {
                if(requestCode == REQUEST_VIDEO_CAPTURE) {

                    try {
                        AssetFileDescriptor videoAsset = getActivity().getContentResolver().openAssetFileDescriptor(data.getData(), "r");
                        FileInputStream fis = videoAsset.createInputStream();
                        File tmpFile = new File(mCurrentPhotoPath);
                        FileOutputStream fos = new FileOutputStream(tmpFile);

                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = fis.read(buf)) > 0) {
                            fos.write(buf, 0, len);
                        }
                        fis.close();
                        fos.close();
                    } catch (IOException io_e) {
                        System.out.println("Error: "+io_e);
                        // TODO: handle error
                    }

                    //videoItems.set(cont, new ImageItem(decodeFile(mCurrentPhotoPath), mCurrentPhotoPath));
                    videoGridView.setAdapter(videoGridAdapter);
                    ++cont;
                }
                else {
                    super.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
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
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }

    AdapterView.OnItemClickListener clickImage = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            ImageItem item = (ImageItem) adapterView.getItemAtPosition(i);

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

            Bitmap bitmap = BitmapFactory.decodeFile(item.getPath(), bmOptions);

            LayoutInflater factory = LayoutInflater.from(getActivity());
            final View mView = factory.inflate(R.layout.sample, null);
            ImageView mImageView = (ImageView) mView.findViewById(R.id.bigImage);
            mImageView.setImageBitmap(bitmap);
            alertDialog.setView(mView);

            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();
        }
    };

}
