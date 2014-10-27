package test3.ncxchile.cl.POJO;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by android-developer on 23-10-2014.
 */
public class VideoItem {
    private Bitmap image;
    private Bitmap icon;
    private Uri uri;
    private String Path;

    public VideoItem(Bitmap image, Bitmap icon, Uri uri, String path) {
        super();
        this.image = image;
        this.icon = icon;
        this.uri = uri;
        this.Path = path;
    }

    public Bitmap getImage () {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Bitmap getIcon () {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.image = icon;
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
