package test3.ncxchile.cl.acta;
import android.app.Activity;
import android.app.Fragment;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import test3.ncxchile.cl.login.R;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleCameraIntentFragment extends Fragment {

        private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
        static final int REQUEST_TAKE_PHOTO = 1;
        Button button;
        ImageView imageView;
        Intent photoIntent;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

            final View rootView = inflater.inflate(R.layout.fragment_simple_camera_intent,container, false);

            button = (Button) rootView.findViewById(R.id.button_foto);
            imageView = (ImageView) rootView.findViewById(R.id.imageViewThumbnail);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(photoIntent,CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                }
            });
            return rootView;
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                if (resultCode == Activity.RESULT_OK) {
                    // Mostrar una miniatura de la foto
                    Bitmap bmp = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    // Convert ByteArray to Bitmap::

                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,byteArray.length);

                    // Guardar la foto en un archivo
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                        //createDirectoryAndSaveFile(bitmap, "foto_1.png");
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        System.out.println("Error al crear el archivo: "+ ex);
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                        System.out.println("Uri del archivo:"+Uri.fromFile(photoFile));
                        //startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                    }

                    imageView.setImageBitmap(bitmap);
                    //setPic();
                }
            }
        }

        String mCurrentPhotoPath;

        private File createImageFile() throws IOException {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "PNG_" + timeStamp + "_";
            File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".png",         /* suffix */
                    storageDir      /* directory */
            );

            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = "file:" + image.getAbsolutePath();
            System.out.println(mCurrentPhotoPath);
            return image;
        }

        private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {

            File direct = new File(Environment.getExternalStorageDirectory() + "/Documents/OS1/");

            if (!direct.exists()) {
                File wallpaperDirectory = new File("/sdcard/Documents/OS1/");
                wallpaperDirectory.mkdirs();
            }

            File file = new File(new File("/sdcard/Documents/OS1/"), fileName);
            if (file.exists()) {
                file.delete();
            }
            try {
                FileOutputStream out = new FileOutputStream(file);
                imageToSave.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        private void setPic() {
            // Get the dimensions of the View
            int targetW = imageView.getWidth();
            int targetH = imageView.getHeight();

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
    }