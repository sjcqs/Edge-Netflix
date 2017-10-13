package bencoder.types;

import bencoder.BObject;

/**
 * Created by satyan on 10/12/17.
 * A bencoded integer
 */
public class BInteger extends BObject {
    private int value;

    public BInteger(int value) {
        this.value = value;
        setSize(encode().length());
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String encode() {
        return "i" + value + "e";
    }

    public static BInteger decode(String encoded, final int index){
        if (encoded.charAt(index) != 'i'){
            throw new IllegalArgumentException("Index must be the index of the 'i' delimiter");
        }
        final int start = index + 1;
        final int end = encoded.indexOf("e",start);

        final String number = encoded.substring(start,end);
        if (number.matches("(-0|0).+")){
            throw new IllegalArgumentException("Unknown bencoded integer format. (number cannot start with -0 or 0)");
        }
        final int value = Integer.valueOf(number);

        return new BInteger(value);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof BInteger && ((BInteger) o).getValue() == getValue();
    }
}
