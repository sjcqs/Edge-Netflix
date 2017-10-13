package bencoder.types;

import bencoder.BDecoder;
import bencoder.BObject;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by satyan on 10/12/17.
 * A bencoded list.
 */
public class BList extends BObject implements List<BObject> {
    private List<BObject> list;

    public BList(List<BObject> list) {
        this.list = new ArrayList<>(list);
    }

    public BList() {
        this.list = new ArrayList<>();
    }

    public String encode() {
        String content = "";
        for (BObject bObject : list) {
            content += bObject.encode();
        }
        return 'l' + content + 'e';
    }

    public static BList decode(String encoded, final AtomicInteger index){
        BList list = new BList();
        if (encoded.charAt(index.get()) != 'l'){
            throw new IllegalArgumentException("Invalid index, list format: l<content>e");
        }
        index.set(index.get() + 1);
        while (encoded.charAt(index.get()) != 'e'){
            BObject object = BDecoder.decode(encoded,index);
            list.add(object);
        }
        index.set(index.get() + 1);
        return list;
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public boolean contains(Object o) {
        return list.contains(o);
    }

    public Iterator<BObject> iterator() {
        return list.iterator();
    }

    public Object[] toArray() {
        return list.toArray();
    }

    public <T> T[] toArray(T[] ts) {
        return list.toArray(ts);
    }

    public boolean add(BObject bObject) {
        return list.add(bObject);
    }

    public boolean remove(Object o) {
        return list.remove(o);
    }

    public boolean containsAll(Collection<?> collection) {
        return list.containsAll(collection);
    }

    public boolean addAll(Collection<? extends BObject> collection) {
        return list.addAll(collection);
    }

    public boolean addAll(int i, Collection<? extends BObject> collection) {
        return list.addAll(i,collection);
    }

    public boolean removeAll(Collection<?> collection) {
        return list.removeAll(collection);
    }

    public boolean retainAll(Collection<?> collection) {
        return list.retainAll(collection);
    }

    public void clear() {
        list.clear();
    }

    public BObject get(int i) {
        return list.get(i);
    }

    public BObject set(int i, BObject bObject) {
        return list.set(i,bObject);
    }

    public void add(int i, BObject bObject) {
        list.add(i,bObject);
    }

    public BObject remove(int i) {
        return list.remove(i);
    }

    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    public ListIterator<BObject> listIterator() {
        return list.listIterator();
    }

    public ListIterator<BObject> listIterator(int i) {
        return list.listIterator(i);
    }

    public List<BObject> subList(int i, int i1) {
        return list.subList(i,i1);
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof BList && ((BList) o).list.equals(list));
    }
}
