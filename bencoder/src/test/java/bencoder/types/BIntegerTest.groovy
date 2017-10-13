package bencoder.types

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
        assert BInteger.decode("i3e",0) == new BInteger(3)
        assert BInteger.decode("i-3e",0) == new BInteger(-3)
        assert BInteger.decode("i0e",0) == new BInteger(0)
        assert BInteger.decode("i1024e",0)== new BInteger(1024)

        shouldFail(IllegalArgumentException){
            BInteger.decode("i23e",1)
        }
        shouldFail(IllegalArgumentException){
            BInteger.decode("i01e",1)
        }
        shouldFail(IllegalArgumentException){
            BInteger.decode("i-0e",1)
        }
        shouldFail(IllegalArgumentException){
            BInteger.decode("ie",1)
        }
    }
}
