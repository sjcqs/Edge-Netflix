package bencoder.types;

import bencoder.BDecoder;
import bencoder.BObject;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by satyan on 10/12/17.
 * A bencoded dictionary, dictionary is ordered by key in the lexical order
 */
public class BDictionary extends BObject implements Map<BString, BObject> {
    private SortedMap<BString, BObject> value;

    public BDictionary(Map<BString, BObject> map) {
        this.value = new TreeMap<>(new DictionaryCompare());
        for (Entry<BString, BObject> entry : map.entrySet()) {
            this.value.put(entry.getKey(),entry.getValue());
        }
    }

    public BDictionary() {
        this.value = new TreeMap<>(new DictionaryCompare());
    }

    public String encode() {
        String content = "";
        for (Entry<BString, BObject> entry : value.entrySet()) {
            content += entry.getKey().encode() + entry.getValue().encode();
        }
        return "d" + content + "e";
    }

    public static BDictionary decode(String text, final AtomicInteger index) {
        if (text.charAt(index.get()) != 'd'){
            throw new IllegalArgumentException("Invalid format. Dictionary format: d{<bstring><bobject>}e");
        }
        index.set(index.get() + 1);
        BDictionary dictionary = new BDictionary();
        while (text.charAt(index.get()) != 'e'){
            dictionary.put(BString.decode(text,index), BDecoder.decode(text,index));
        }
        return dictionary;
    }

    @Override
    public int size() {
        return value.size();
    }

    @Override
    public boolean isEmpty() {
        return value.isEmpty();
    }

    @Override
    public boolean containsKey(Object o) {
        return value.containsKey(o);
    }

    @Override
    public boolean containsValue(Object o) {
        return value.containsValue(o);
    }

    @Override
    public BObject get(Object o) {
        return value.get(o);
    }

    public BObject get(String str){
        return value.get(new BString(str));
    }

    @Override
    public BObject put(BString bString, BObject bObject) {
        return value.put(bString,bObject);
    }

    public BObject put(String key, BObject value){
        return this.value.put(new BString(key),value);
    }

    @Override
    public BObject remove(Object o) {
        return value.remove(o);
    }

    public BObject remove(String key){
        return value.remove(new BString(key));
    }

    @Override
    public void putAll(Map<? extends BString, ? extends BObject> map) {
        this.value.putAll(map);
    }

    @Override
    public void clear() {
        value.clear();
    }

    @Override
    public Set<BString> keySet() {
        return value.keySet();
    }

    @Override
    public Collection<BObject> values() {
        return value.values();
    }

    @Override
    public Set<Entry<BString, BObject>> entrySet() {
        return value.entrySet();
    }

    private class DictionaryCompare implements Comparator<BString> {
        @Override
        public int compare(BString str0, BString str1) {
            byte[] b0 = str0.getValue().getBytes();
            byte[] b1 = str1.getValue().getBytes();
            int length = b0.length < b1.length ? b0.length : b1.length;
            for (int i = 0; i < length; i++) {
                int compare = Byte.compare(b0[i], b1[i]);
                if (compare != 0){
                    return compare;
                }
            }
            return b0.length - b1.length;
        }
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof BDictionary && ((BDictionary) o).value.equals(value);
    }
}
