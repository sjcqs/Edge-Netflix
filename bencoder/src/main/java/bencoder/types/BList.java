package bencoder.types;

import bencoder.BObject;

import java.util.*;

/**
 * Created by satyan on 10/12/17.
 */
public class BList extends BObject implements List<BObject> {
    private List<BObject> list;

    public String encode() {
        return null;
    }

    public int size() {
        return 0;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean contains(Object o) {
        return false;
    }

    public Iterator<BObject> iterator() {
        return null;
    }

    public Object[] toArray() {
        return new Object[0];
    }

    public <T> T[] toArray(T[] ts) {
        return null;
    }

    public boolean add(BObject bObject) {
        return false;
    }

    public boolean remove(Object o) {
        return false;
    }

    public boolean containsAll(Collection<?> collection) {
        return false;
    }

    public boolean addAll(Collection<? extends BObject> collection) {
        return false;
    }

    public boolean addAll(int i, Collection<? extends BObject> collection) {
        return false;
    }

    public boolean removeAll(Collection<?> collection) {
        return false;
    }

    public boolean retainAll(Collection<?> collection) {
        return false;
    }

    public void clear() {

    }

    public BObject get(int i) {
        return null;
    }

    public BObject set(int i, BObject bObject) {
        return null;
    }

    public void add(int i, BObject bObject) {

    }

    public BObject remove(int i) {
        return null;
    }

    public int indexOf(Object o) {
        return 0;
    }

    public int lastIndexOf(Object o) {
        return 0;
    }

    public ListIterator<BObject> listIterator() {
        return null;
    }

    public ListIterator<BObject> listIterator(int i) {
        return null;
    }

    public List<BObject> subList(int i, int i1) {
        return null;
    }
}
