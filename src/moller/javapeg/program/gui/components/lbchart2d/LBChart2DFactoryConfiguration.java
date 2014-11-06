package moller.javapeg.program.gui.components.lbchart2d;

public class LBChart2DFactoryConfiguration {

    private String objectTitleText;
    private String[] labelsAxisLabelsTexts;
    private int[] numberOfImages;
    private String labelsAxisTitleText;

    public String getObjectTitleText() {
        return objectTitleText;
    }

    public String[] getLabelsAxisLabelsTexts() {
        return labelsAxisLabelsTexts;
    }

    public int[] getNumberOfImages() {
        return numberOfImages;
    }

    public String getLabelsAxisTitleText() {
        return labelsAxisTitleText;
    }

    public void setObjectTitleText(String objectTitleText) {
        this.objectTitleText = objectTitleText;
    }

    public void setLabelsAxisLabelsTexts(String[] labelsAxisLabelsTexts) {
        this.labelsAxisLabelsTexts = labelsAxisLabelsTexts;
    }

    public void setNumberOfImages(int[] numberOfImages) {
        this.numberOfImages = numberOfImages;
    }

    public void setLabelsAxisTitleText(String labelsAxisTitleText) {
        this.labelsAxisTitleText = labelsAxisTitleText;
    }
}
