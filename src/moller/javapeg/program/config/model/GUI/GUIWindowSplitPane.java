package moller.javapeg.program.config.model.GUI;

public class GUIWindowSplitPane {

    private Integer Location;
    private Integer width;
    private String name;

    public Integer getLocation() {
        return Location;
    }
    public Integer getWidth() {
        return width;
    }
    public String getName() {
        return name;
    }
    public void setLocation(Integer location) {
        Location = location;
    }
    public void setWidth(Integer width) {
        this.width = width;
    }
    public void setId(String name) {
        this.name = name;
    }
}
