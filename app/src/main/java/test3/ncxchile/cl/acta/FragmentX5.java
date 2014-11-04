package test3.ncxchile.cl.acta;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableRow;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import test3.ncxchile.cl.POJO.ImageItem;
import test3.ncxchile.cl.POJO.VideoItem;
import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.session.SessionManager;
import test3.ncxchile.cl.adapters.GridViewAdapter;
import test3.ncxchile.cl.adapters.GridViewVideoAdapter;

/**
 * Created by android-developer on 23-10-2014.
 */
public class FragmentX5 extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_VIDEO_CAPTURE = 2;

    // Elementos legados de FragmentX5
    public CheckBox img, vid, adjuntarImagen, adjuntarVideo;
    public EditText motivo_imgvid;
    public TableRow motivo;
    //////////////////////////////
    Button button,buttonVideo;
    ImageView imageView;
    ArrayList<ImageItem> imageItems = new ArrayList<ImageItem>(15);
    ArrayList<VideoItem> videoItems = new ArrayList<VideoItem>(5);
    GridView imageGridView;
    GridView videoGridView;
    ImageView imageIcon;
    ImageView videoIcon;
    private GridViewAdapter imageGridAdapter;
    private GridViewVideoAdapter videoGridAdapter;
    Intent photoIntent;
    View rootView;
    String mCurrentPath;
    Uri mCurrentUri;
    SessionManager session;

    public FragmentX5 newInstance(int sectionNumber){
        FragmentX5 fragment = new FragmentX5();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment5, container, false);
        motivo = (TableRow) rootView.findViewById(R.id.motivo);
        motivo_imgvid = (EditText) rootView.findViewById(R.id.motivo_imgvid);
        adjuntarImagen= (CheckBox) rootView.findViewById(R.id.adjuntar_imagen);
        adjuntarVideo= (CheckBox) rootView.findViewById(R.id.adjuntar_video);
        imageIcon= (ImageView) rootView.findViewById(R.id.icon);
        videoIcon= (ImageView) rootView.findViewById(R.id.icon_video);

        button = (Button) rootView.findViewById(R.id.button_foto);
        buttonVideo = (Button) rootView.findViewById(R.id.button_video);
        //items.add(new ImageItem(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.placeholder),""));

        ImageItem imagePlaceHolder = new ImageItem(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.photo_placeholder),
                                                   BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.photo_placeholder),
                                                   Uri.parse(getActivity().getPackageName() + R.drawable.video_placeholder),"");

        if(imageItems.size()>0) {
            for(int i=0; i <10; ++i) {
                if(!imageItems.get(i).getPath().toString().equals("")){
                    imageItems.set(i, new ImageItem(imageItems.get(i).getImage(),
                                                    BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.delete_small)
                                                    ,mCurrentUri, mCurrentPath));
                }
                else{
                    imageItems.set(i, imagePlaceHolder);
                }
            }
        }
        else
        {
            for(int i=0; i <10; ++i)
                imageItems.add(imagePlaceHolder);
        }

        VideoItem videoPlaceHolder= new VideoItem(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.video_placeholder),
                                                  BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.photo_placeholder),
                                                  Uri.parse(getActivity().getPackageName() + R.drawable.video_placeholder),"");

        if(videoItems.size()>0) {
            for(int i=0; i <5; ++i) {
                if (!videoItems.get(i).getPath().toString().equals("")) {
                    videoItems.set(i, new VideoItem(videoItems.get(i).getImage(),
                            BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.delete_small)
                            , mCurrentUri, mCurrentPath));
                } else {
                    videoItems.set(i, videoPlaceHolder);
                }
            }
        }
        else
        {
            for(int i=0; i <5; ++i)
                videoItems.add(videoPlaceHolder);
        }

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
                photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri(2));
                startActivityForResult(photoIntent, REQUEST_VIDEO_CAPTURE);
            }
        });

        //imageGridView.setOnItemClickListener(clickImage);
        //videoGridView.setOnItemClickListener(clickVideo);

        adjuntarImagen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b) {
                    motivo.setVisibility(View.VISIBLE);
                }
                else {
                    if(adjuntarVideo.isChecked())
                        motivo.setVisibility(View.GONE);
                }
            }
        });

        adjuntarVideo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b) {
                    motivo.setVisibility(View.VISIBLE);
                }
                else {
                    if(adjuntarImagen.isChecked())
                        motivo.setVisibility(View.GONE);
                }
            }
        });

        session = new SessionManager(getActivity());

        //horizontalListView.setAdapter(new HAdapter());
        return rootView;
    }

    public Uri setImageUri(int media) {
        // Store image in dcim
        //File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".png");
        File storageDir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String imageFileName="";

        switch (media){
            case 1:
                storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+"/OS_1/FOTOS/");
                //timeStamp = "foto_"+contImage;
                if(!storageDir.exists())
                    storageDir.mkdirs();
                imageFileName = timeStamp + ".png";
                break;
            case 2:
                storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+"/OS_1/VIDEOS/");
                //timeStamp = "video_"+contVideo;
                if(!storageDir.exists())
                    storageDir.mkdirs();
                imageFileName = timeStamp + ".mp4";
                break;
        }

        File file = new File(storageDir.getAbsolutePath(), imageFileName);

        Uri imgUri = Uri.fromFile(file);
        System.out.print("imgUri=" + imgUri);
        mCurrentPath = file.getAbsolutePath();
        mCurrentUri = imgUri;
        return imgUri;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                imageItems.set(session.getCantidadFotos(), new ImageItem(decodeFile(mCurrentPath),
                        BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.delete_small)
                        ,mCurrentUri, mCurrentPath));
                session.setCantidadFotos(session.getCantidadFotos() + 1);
                imageGridView.setAdapter(imageGridAdapter);
            } else {
                if(requestCode == REQUEST_VIDEO_CAPTURE) {
                    Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(mCurrentPath,MediaStore.Images.Thumbnails.MINI_KIND);
                    videoItems.set(session.getCantidadVideos(), new VideoItem(bitmap,
                            BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.delete_small)
                            , mCurrentUri, mCurrentPath));
                    session.setCantidadVideos(session.getCantidadVideos() + 1);
                    videoGridView.setAdapter(videoGridAdapter);
                }
                else {
                    super.onActivityResult(requestCode, resultCode, data);
                }
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

            int rotateImage = getCameraPhotoOrientation(getActivity(), mCurrentUri, mCurrentPath);

            if(rotateImage==0)
                return BitmapFactory.decodeFile(path, o2);

            // create a matrix object
            Matrix matrix = new Matrix();
            matrix.postRotate(90); // anti-clockwise by 90 degrees

            // create a new bitmap from the original using the matrix to transform the result
            return Bitmap.createBitmap(BitmapFactory.decodeFile(path, o2) , 0, 0, o2.outWidth, o2.outHeight, matrix, true);

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;

    }

    public void envioDeDatos() {

        boolean boolimg=adjuntarImagen.isChecked();
        boolean boolvid=adjuntarVideo.isChecked();
        String motivo=motivo_imgvid.getText().toString();

        ((MyActivity) getActivity()).recibeDatosFragmentX5(boolimg, boolvid, motivo);
    }

    public boolean validarDatosFragmentFotoVideo(){

        boolean esValido=true;

        if( (!adjuntarImagen.isChecked() || !adjuntarVideo.isChecked()) && motivo_imgvid.getText().toString().equals("") ){
            motivo_imgvid.setError(getString(R.string.error_field_required));
            esValido=false;
        }

        if (adjuntarImagen.isChecked() && session.getCantidadFotos()==0){
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("Error de Fotos/Videos");
            alertDialog.setMessage("Debes sacar al menos una foto");
            alertDialog.setIcon(R.drawable.action_fail_small);

            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();
            esValido=false;
            return esValido;
        }

        if (adjuntarVideo.isChecked() && session.getCantidadVideos()==0){
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("Error de Fotos/Videos");
            alertDialog.setMessage("Debes grabar al menos un video");
            alertDialog.setIcon(R.drawable.action_fail_small);

            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();
            esValido=false;
        }
        return esValido;
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

            System.out.println("Exif orientation: " + orientation);
            System.out.println("Rotate value: " + rotate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }
}
