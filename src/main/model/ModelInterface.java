package main.model;

public interface ModelInterface<PK extends java.io.Serializable> {

    void setId(PK id);

    PK getId();

}
