package bencoder.types

/**
 * Created by satyan on 10/13/17.
 */
class BStringTest extends GroovyTestCase {

    void testEncode() {
        assert new BString("spam").encode() == "4:spam"
    }

    void testDecode() {

    }
}
