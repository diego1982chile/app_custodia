package test3.ncxchile.cl.POJO;

/**
 * Created by android-developer on 22-10-2014.
 */
import android.graphics.Bitmap;

public class ImageItem {
    private Bitmap image;
    private String Path;

    public ImageItem(Bitmap image, String title) {
        super();
        this.image = image;
        this.Path = title;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        this.Path = path;
    }
}
