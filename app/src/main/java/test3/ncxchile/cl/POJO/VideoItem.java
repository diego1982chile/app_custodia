package test3.ncxchile.cl.POJO;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by android-developer on 23-10-2014.
 */
public class VideoItem {
    private Bitmap image;
    private Uri uri;
    private String Path;

    public VideoItem(Bitmap image, Uri uri, String path) {
        super();
        this.image = image;
        this.uri = uri;
        this.Path = path;
    }

    public Bitmap getImage () {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        this.Path = path;
    }
}
