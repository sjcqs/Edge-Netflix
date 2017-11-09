package pseudo_torrent.message;

/**
 * Created by satyan on 11/9/17.
 */
public class BitField extends Message {
    private boolean[] field;

    public BitField(boolean[] field) {
        super(BIT_FIELD_ID);
        this.field = field;
    }

    public boolean[] getField() {
        return field;
    }

    @Override
    public String serialize() {
        return gson.toJson(this);
    }
}
