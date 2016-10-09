package com.hc.library.util;

import java.util.Collection;

/**
 * Created by xc on 2016/8/6.
 */
public interface ItemList<D,R extends ItemList> {
    R addItem(D data);
    R addItem(int position, D item);
    R addItemAll(Collection<D> coll);
    R addItemAll(int position, Collection<D> coll);
    D removeItem(int position);
    boolean removeItem(D item);
}
