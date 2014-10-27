package test3.ncxchile.cl.POJO;

/**
 * Created by android-developer on 22-10-2014.
 */
import android.graphics.Bitmap;
import android.net.Uri;

public class ImageItem {
    private Bitmap image;
    private Bitmap icon;
    private String Path;
    private Uri uri;

    public ImageItem(Bitmap image, Bitmap icon, Uri uri, String title) {
        super();
        this.image = image;
        this.icon = icon;
        this.uri = uri;
        this.Path = title;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public Uri getUri(){ return uri; }

    public void setUri(Uri uri){ this.uri= uri; }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        this.Path = path;
    }
}
