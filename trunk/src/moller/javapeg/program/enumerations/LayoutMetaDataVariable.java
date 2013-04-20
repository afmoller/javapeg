package moller.javapeg.program.enumerations;

public enum LayoutMetaDataVariable {
    FILENAME       ("fileName"),
    DATE           ("date"),
    TIME           ("time"),
    CAMERAMODEL    ("cameraModel"),
    EXPOSURETIME   ("exposureTime"),
    ISOVALUE       ("isoValue"),
    PICTUREWIDTH   ("pictureWidth"),
    PICTUREHEIGHT  ("pictureHeight"),
    APERTUREVALUE  ("apertureValue"),
    FILESIZE       ("fileSize");

    private String value;

    private LayoutMetaDataVariable(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
