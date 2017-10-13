package bencoder.types

/**
 * Created by satyan on 10/13/17.
 */
class BStringTest extends GroovyTestCase {

    final BString res = new BString("spam")
    final String expected = "4:spam"

    void testEncode() {
        assert res.encode() == expected
        assert new BString("").encode() == "0:"
        assert new BString("Hello world !").encode() == "13:Hello world !"
    }

    void testDecode() {
        assert BString.decode(expected,0) == res
        assert BString.decode("0:",0) == new BString("")
    }
}
