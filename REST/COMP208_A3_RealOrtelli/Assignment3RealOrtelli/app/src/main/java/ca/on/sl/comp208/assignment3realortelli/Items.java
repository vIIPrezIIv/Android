package ca.on.sl.comp208.assignment3realortelli;

import android.graphics.Bitmap;

/**
 * Created by Ray on 2017-04-09.
 */

public class Items {

    /**
     * Variables
     */
    private Bitmap image;
    private String title;
    private int entityID;

    /**
     * Constructor
     * @param image
     * @param title
     * @param entityID
     */
    public Items(Bitmap image, String title, int entityID)
    {
        this.image = image;
        this.title = title;
        this.entityID = entityID;
    }

    public int getEntityID() {
        return entityID;
    }

    public void setEntityID(int entityID) {
        this.entityID = entityID;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
