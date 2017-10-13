package bencoder.types

import bencoder.BObject

import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by satyan on 10/13/17.
 * Test for BList class
 */
class BListTest extends GroovyTestCase {
    void testEncode() {
        BList bList = new BList()
        bList.add(new BString("spam"))
        bList.add(new BString("eggs"))

        assert bList.encode() == "l4:spam4:eggse"
        assert new BList().encode() == "le"

        List<BObject> list = new ArrayList<>();
        list.add(new BString("spam"))
        list.add(new BString("eggs"))
        bList = new BList(list)

        assert bList.encode() == "l4:spam4:eggse"
        assert new BList().encode() == "le"
    }

    void testDecode() {
        AtomicInteger index = new AtomicInteger(0)
        BList list0 = BList.decode("l4:spam4:eggse",index)
        assert index.get() == 14
        assert list0.get(0) == new BString("spam")
        assert list0.get(1) == new BString("eggs")
        index.set(0)
        BList list1 = BList.decode("l4:spam4:eggsl4:spam4:eggsee",index)
        assert index.get() == 28
        assert list1.get(0) == new BString("spam")
        assert list1.get(1) == new BString("eggs")
        assert list1.get(2) == list0
        index.set(0)
        BList list2 = BList.decode("l4:spam4:eggsl4:spam4:eggsei45ee",index)
        assert index.get() == 32
        assert list2.get(0) == new BString("spam")
        assert list2.get(1) == new BString("eggs")
        assert list2.get(2) == list0
        assert list2.get(3) == new BInteger(45)
    }
}
