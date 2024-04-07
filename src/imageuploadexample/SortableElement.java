package imageuploadexample;

import javax.swing.ImageIcon;

public class SortableElement implements Comparable<SortableElement> {
    private ImageIcon imageIcon;
    private int size;

    public SortableElement(ImageIcon imageIcon, int size) {
        this.imageIcon = imageIcon;
        this.size = size;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public int getSize() {
        return size;
    }

    // For comparing the sizes
    @Override
    public int compareTo(SortableElement other) {

        return Integer.compare(other.size, this.size);
    }
}
