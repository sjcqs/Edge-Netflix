package bencoder.types

import java.util.concurrent.atomic.AtomicInteger

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
        AtomicInteger i0 = new AtomicInteger(0)
        AtomicInteger i1 = new AtomicInteger(0)
        assert BString.decode(expected, i0) == res
        assert i0.get() == 6
        assert BString.decode("0:",i1) == new BString("")
        assert i1.get() == 2
    }
}
