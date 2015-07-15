package my.edu.mmu.capstonevideos;

/**
 * Created by wenjiun on 14/7/2015.
 */
public class CapstoneVideo {

    private String title;
    private int imageId;
    private int videoId;

    public CapstoneVideo(String title, int imageId, int videoId) {
        this.title = title;
        this.imageId = imageId;
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public int getImageId() {
        return imageId;
    }

    public int getVideoId() {
        return videoId;
    }
}
