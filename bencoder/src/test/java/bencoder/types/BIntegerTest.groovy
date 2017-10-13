package bencoder.types

import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by satyan on 10/13/17.
 */
class BIntegerTest extends GroovyTestCase {
    void testEncode() {
        // Example: i3e represents the integer "3"
        // Example: i-3e represents the integer "-3"
        assert new BInteger(3).encode() == "i3e"
        assert new BInteger(-3).encode() == "i-3e"
        assert new BInteger(0).encode() == "i0e"
        assert new BInteger(1024).encode() == "i1024e"
    }

    void testDecode() {
        AtomicInteger index = new AtomicInteger(0)
        assert BInteger.decode("i3e",index) == new BInteger(3)
        assert index.get() == 3
        index.set(0)
        assert BInteger.decode("i-3e",index) == new BInteger(-3)
        assert index.get() == 4
        index.set(0)
        assert BInteger.decode("i0e",index) == new BInteger(0)
        assert index.get() == 3
        index.set(0)
        assert BInteger.decode("i1024e",index)== new BInteger(1024)
        assert index.get() == 6

        shouldFail(IllegalArgumentException){
            index.set(1)
            BInteger.decode("i23e",index)
        }
        shouldFail(IllegalArgumentException){
            index.set(0)
            BInteger.decode("i01e",index)
        }
        shouldFail(IllegalArgumentException){
            index.set(0)
            BInteger.decode("i-0e",index)
        }
        shouldFail(IllegalArgumentException){
            index.set(0)
            BInteger.decode("ie",index)
        }
        shouldFail(IllegalArgumentException){
            index.set(0)
            BInteger.decode("ihelloe",index)
        }
        shouldFail(IllegalArgumentException){
            index.set(0)
            BInteger.decode("i1-12e",index)
        }
    }
}
