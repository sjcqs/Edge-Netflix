package model;

import java.util.BitSet;

/**
 * Created by satyan on 11/13/17.
 * A fixed fixedSize bitset
 */
public class FixedBitSet extends BitSet{

    private int fixedSize;

    public FixedBitSet(int fixedLength){
        super(fixedLength);
        this.fixedSize = fixedLength;
    }

    @Override
    public int size() {
        return fixedSize;
    }
}
