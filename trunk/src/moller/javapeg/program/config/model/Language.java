package moller.javapeg.program.config.model;

public class Language {

    private Boolean automaticSelection;
    private String gUILanguageISO6391;

    public Boolean getAutomaticSelection() {
        return automaticSelection;
    }
    public String getgUILanguageISO6391() {
        return gUILanguageISO6391;
    }
    public void setAutomaticSelection(Boolean automaticSelection) {
        this.automaticSelection = automaticSelection;
    }
    public void setgUILanguageISO6391(String gUILanguageISO6391) {
        this.gUILanguageISO6391 = gUILanguageISO6391;
    }
}
