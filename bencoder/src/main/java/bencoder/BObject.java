package bencoder;

/**
 * Created by satyan on 10/12/17.
 * A interface to define a element that can be bencoded.
 */
public abstract class BObject {
    protected int size;
    public abstract String encode();

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
