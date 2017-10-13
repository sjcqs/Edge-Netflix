package bencoder.types;

import bencoder.BObject;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by satyan on 10/12/17.
 * A bencoded string
 */
public class BString extends BObject {
    private String value;

    public BString(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String encode() {
        return value.length() + ":" + value;
    }

    public static BString decode(String encoded, final AtomicInteger index){
        final int separator = encoded.indexOf(':',index.get());
        final int length = Integer.valueOf(encoded.substring(index.get(),separator));
        index.set(separator + 1);
        String value = encoded.substring(index.get(),index.get() + length);
        index.set(index.get() + length);
        return new BString(value);
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof BString && value.equals(((BString) o).getValue()));
    }
}
