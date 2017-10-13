package bencoder.types

import bencoder.BObject

import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by satyan on 10/13/17.
 * Test for BDictionary
 */
class BDictionaryTest extends GroovyTestCase {
    void testEncode() {
        BDictionary dico0, dico1, dico2, dico3
        final Map<BString,BObject> map = new HashMap<BString, BObject>()
        // WITH A EXISTING MAP
        //d3:cow3:moo4:spam4:eggse represents the dictionary { "cow" => "moo", "spam" => "eggs" }
        map.put(new BString("cow"), new BString("moo"))
        map.put(new BString("spam"), new BString("eggs"))
        dico0 = new BDictionary(map)
        assert dico0.encode() == "d3:cow3:moo4:spam4:eggse"

        //d4:spaml1:a1:bee represents the dictionary { "spam" => [ "a", "b" ] }
        map.clear()
        final BList list = new BList()
        list.add(new BString("a"))
        list.add(new BString("b"))
        map.put(new BString("spam"), list)
        dico1 = new BDictionary(map)
        assert dico1.encode() == "d4:spaml1:a1:bee"

        //d9:publisher3:bob17:publisher-webpage15:www.example.com18:publisher.location4:homee
        // represents { "publisher" => "bob", "publisher-webpage" => "www.example.com", "publisher.location" => "home" }
        map.clear()
        map.put(new BString("publisher"), new BString("bob"))
        map.put(new BString("publisher-webpage"), new BString("www.example.com"))
        map.put(new BString("publisher.location"), new BString("home"))
        dico2 = new BDictionary(map)
        assert dico2.encode() == "d9:publisher3:bob17:publisher-webpage15:www.example.com18:publisher.location4:homee"

        //de represents an empty dictionary {}
        map.clear()
        dico3 = new BDictionary()
        assert dico3.encode() == "de"

        // ADDING ELEMENT TO THE MAP
        //d3:cow3:moo4:spam4:eggse represents the dictionary { "cow" => "moo", "spam" => "eggs" }
        dico0 = new BDictionary()
        dico0.put(new BString("cow"), new BString("moo"))
        dico0.put(new BString("spam"), new BString("eggs"))
        assert dico0.encode() == "d3:cow3:moo4:spam4:eggse"

        //d4:spaml1:a1:bee represents the dictionary { "spam" => [ "a", "b" ] }
        dico1 = new BDictionary()
        dico1.put(new BString("spam"), list)
        assert dico1.encode() == "d4:spaml1:a1:bee"

        //d9:publisher3:bob17:publisher-webpage15:www.example.com18:publisher.location4:homee
        // represents { "publisher" => "bob", "publisher-webpage" => "www.example.com", "publisher.location" => "home" }
        dico2 = new BDictionary()
        dico2.put(new BString("publisher"), new BString("bob"))
        dico2.put(new BString("publisher-webpage"), new BString("www.example.com"))
        dico2.put(new BString("publisher.location"), new BString("home"))
        assert dico2.encode() == "d9:publisher3:bob17:publisher-webpage15:www.example.com18:publisher.location4:homee"

        dico2 = new BDictionary()
        dico2.put("publisher", new BString("bob"))
        dico2.put("publisher-webpage", new BString("www.example.com"))
        dico2.put("publisher.location", new BString("home"))
        assert dico2.encode() == "d9:publisher3:bob17:publisher-webpage15:www.example.com18:publisher.location4:homee"

        //de represents an empty dictionary {}
        dico3 = new BDictionary()
        assert dico3.encode() == "de"
    }

    void testDecode() {

        //d3:cow3:moo4:spam4:eggse represents the dictionary { "cow" => "moo", "spam" => "eggs" }
        AtomicInteger index = new AtomicInteger(0)
        final BDictionary dico0 = BDictionary.decode("d3:cow3:moo4:spam4:eggse",index)
        assert dico0.get("cow") == new BString("moo")
        assert dico0.get("spam") == new BString("eggs")
        assert dico0.encode() == "d3:cow3:moo4:spam4:eggse"

        //d4:spaml1:a1:bee represents the dictionary { "spam" => [ "a", "b" ] }
        index.set(0)
        final BList list = new BList()
        list.add(new BString("a"))
        list.add(new BString("b"))
        final BDictionary dico1 = BDictionary.decode("d4:spaml1:a1:bee",index)
        BList list1 = (BList) dico1.get("spam")
        assert list1 == list
        assert list1.get(0) == new BString("a")
        assert list1.get(1) == new BString("b")
        assert dico1.encode() == "d4:spaml1:a1:bee"

        //d9:publisher3:bob17:publisher-webpage15:www.example.com18:publisher.location4:homee
        // represents { "publisher" => "bob", "publisher-webpage" => "www.example.com", "publisher.location" => "home" }
        index.set(0)
        final BDictionary dico2 = BDictionary.decode(
                "d9:publisher3:bob17:publisher-webpage15:www.example.com18:publisher.location4:homee",index
        )
        assert dico2.get("publisher") == new BString("bob")
        assert dico2.get("publisher-webpage") == new BString("www.example.com")
        assert dico2.get("publisher.location") == new BString("home")
        assert dico2.encode() == "d9:publisher3:bob17:publisher-webpage15:www.example.com18:publisher.location4:homee"

        //de represents an empty dictionary {}
        index.set(0)
        final BDictionary dico3 = BDictionary.decode("de",index)
        assert dico3.isEmpty()
        assert dico3.encode() == "de"
    }
}
