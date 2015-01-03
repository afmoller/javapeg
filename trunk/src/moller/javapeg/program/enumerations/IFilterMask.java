package moller.javapeg.program.enumerations;

public interface IFilterMask {

    public String getMask();

    public String name();

    public int getTriggerValue();

    @Override
    public boolean equals(Object obj);

    @Override
    public int hashCode();

}
