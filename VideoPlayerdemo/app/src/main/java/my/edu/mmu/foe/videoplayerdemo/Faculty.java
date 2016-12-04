package my.edu.mmu.foe.videoplayerdemo;

/**
 * A class to represent a particular Faculty in Multimedia University
 */

public class Faculty {

    private String title;

    // the resource ID of the thumbnail image in the drawable-nodpi folder
    private int image;

    // the resource ID of the video in the raw folder
    private int video;

    public Faculty(String title, int image, int video) {
        this.title = title;
        this.image = image;
        this.video = video;
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }

    public int getVideo() {
        return video;
    }
}
