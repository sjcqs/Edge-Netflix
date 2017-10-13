package bencoder.types;

import bencoder.BObject;

/**
 * Created by satyan on 10/12/17.
 * A bencoded string
 */
public class BString extends BObject {
    private String value;

    public BString(final String value) {
        this.value = value;
        setSize(encode().length());
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

    public static BString decode(String encoded, final int start){
        int separatorIndex = encoded.indexOf(':',start);
        int length = Integer.valueOf(encoded.substring(start,separatorIndex));
        String value = encoded.substring(separatorIndex + 1,separatorIndex + 1 + length);
        return new BString(value);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof BString && value.equals(((BString) o).getValue());
    }
}
