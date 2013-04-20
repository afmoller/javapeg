package moller.javapeg.program.datatype;

public class ImageSize implements Comparable<ImageSize>{

    private int height;
    private int width;

    public ImageSize(int height, int width) {
        super();
        this.height = height;
        this.width = width;
    }

    public ImageSize(String imageSizeValueString) {
        String[] parts = imageSizeValueString.split("x");

        this.width = Integer.parseInt(parts[0].trim());
        this.height = Integer.parseInt(parts[1].trim());
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return Integer.toString(this.getWidth()) + " x " + Integer.toString(this.getHeight());
    }

    @Override
    public int compareTo(ImageSize other) {

        long thisImageSize  = this.getWidth() * this.getHeight();
        long otherImageSize = other.getWidth() * other.getHeight();

        if (thisImageSize < otherImageSize) {
            return -1;
        } else if (thisImageSize == otherImageSize) {
            if (this.getWidth() < other.getWidth()) {
                return -1;
            } else if (this.getWidth() == other.getWidth()) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }
}
