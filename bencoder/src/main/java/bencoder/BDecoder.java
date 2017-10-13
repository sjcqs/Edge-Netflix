package bencoder;

import bencoder.types.BDictionary;
import bencoder.types.BInteger;
import bencoder.types.BList;
import bencoder.types.BString;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by satyan on 10/12/17.
 * Allows to decode strings
 */
public class BDecoder {

    public static List<BObject> decode(String text){
        AtomicInteger index = new AtomicInteger(0);
        List<BObject> list = new ArrayList<>();
        while (index.get() != list.size()){
            list.add(decode(text,index));
        }
        return list;
    }

    public static BObject decode(String text,final AtomicInteger index){
        switch (text.charAt(index.get())){
            // STRING
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return BString.decode(text, index);
            case 'i':
                return BInteger.decode(text, index);
            case 'l':
                return BList.decode(text, index);
            case 'd':
                return BDictionary.decode(text, index);
            default:
                return null;
        }
    }
}
