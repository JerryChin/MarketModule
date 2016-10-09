package com.hc.library.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hc.library.R;
import com.hc.library.util.PinyinUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by Xiong on 2016/8/14.
 */
public abstract class BasePinYinGroupAdapter<D> extends BaseAdapter{
    private final static int VIEW_TYPE_GROUP = 0;

    private Context mContext;
    private GroupData mGroupData;
    private List<D> mData;


    public BasePinYinGroupAdapter(Context context){
        mContext = context;

        mData = new ArrayList<>();
        mGroupData = new GroupData();

    }

    //获得排序的字段
    public @NonNull abstract String getSortString(D data);

    public Context getContext(){
        return mContext;
    }

    @Override
    public int getCount() {
        return mData.size() + mGroupData.size();
    }

    @Override
    public Object getItem(int position) {
        return mGroupData.getGroupItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 1 + getViewTypeCountAttach();
    }

    @Override
    public int getItemViewType(int position) {
        GroupItem item = mGroupData.getGroupItem(position);
        if(item.mData == null){
            return VIEW_TYPE_GROUP;
        }

        return getItemViewTypeAttach(position,item);
    }

    /**
     * return 返回类型(大于0)
     * */
    public int getItemViewTypeAttach(int position,GroupItem item){
        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GroupItem item = mGroupData.getGroupItem(position);
        int type = item.mData == null ? VIEW_TYPE_GROUP : getItemViewType(position);
        if(type == VIEW_TYPE_GROUP){
            ViewHolder holder;
            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_pinyin_group,parent,false);
                convertView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        return true;
                    }
                });
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            TextView tv = holder.getConvertView(TextView.class);
            tv.setText(String.valueOf(item.mChar));

            return tv;
        }else{
            Object holder;
            if(convertView != null){
                holder = convertView.getTag();
            }else {
                convertView = getView(position, item, type, parent);
                holder = getViewHolder(type,convertView);
                convertView.setTag(holder);
            }

            bindView(holder,item);

            return convertView;
        }
    }

    public abstract View getView(int position,GroupItem item,int type,ViewGroup parent);

    public abstract Object getViewHolder(int type,View convertView);

    public abstract void bindView(Object holder,GroupItem item);

    public int getViewTypeCountAttach(){
        return 1;
    }

    public void addItem(D data) {
        mData.add(data);
        dataAdd(data);
        notifyDataSetChanged();
    }

    public void addItemAll(Collection<D> coll) {
        mData.addAll(coll);
        dataAddAll(coll);
        notifyDataSetChanged();
    }

    public D removeItem(int position) {
        GroupItem item = mGroupData.getGroupItem(position);
        if(item.mData == null){
            return null;
        }
        boolean b = mData.remove(item.mData);
        if(b) {
            dataRemove(item);
            notifyDataSetChanged();
        }
        return item.mData;
    }

    public boolean removeItem(D item) {
        boolean b = mData.remove(item);
        if (b) {
            dataRemove(item);
            notifyDataSetChanged();
        }
        return b;
    }

    public void clearAllItem(){
        mData.clear();
        mGroupData.clear();
    }

    public int getPositionByFilter(char filter){
        return mGroupData.getPositionByFilter(filter);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    private void dataRemove(GroupItem item){
        if(mGroupData.containsKey(item.mChar)){
            ArrayList<D> list = mGroupData.get(item.mChar);
            list.remove(item.mData);
            if(list.size() == 0){
                mGroupData.remove(item.mChar);
            }
        }
    }

    private void dataRemove(D data){
        String py = PinyinUtils.getFullSpell(getSortString(data));
        char letter;
        if(!TextUtils.isEmpty(py)){
            letter = py.charAt(0);
        }else{
            letter = '#';
        }

        GroupItem item = new GroupItem();
        item.mChar = letter;item.mData = data;
        dataRemove(item);
    }

    private void dataRemoveAll(Collection<D> datas){
        if(datas != null){
            for(D d : datas){
                dataRemove(d);
            }
        }
    }

    private void dataAdd(D data){
        String py = PinyinUtils.getFirstSpell(getSortString(data));
        char letter;
        if(!TextUtils.isEmpty(py)){
            letter = Character.toUpperCase(py.charAt(0));
        }else{
            letter = '#';
        }

        if(mGroupData.containsKey(letter)){
            ArrayList<D> list = mGroupData.get(letter);
            int size = list.size();
            for(int i=0;i<size;i++){
                if(PinyinUtils.compare(PinyinUtils.getFirstSpell(getSortString(list.get(i))),py)){
                    list.add(i,data);
                    return;
                }
            }
            list.add(data);
        }else{
            ArrayList<D> list = new ArrayList<>();
            list.add(data);
            mGroupData.put(letter,list);
        }
    }

    private void dataAddAll(Collection<D> datas){
        if(datas != null){
            for(D d : datas){
                dataAdd(d);
            }
        }
    }

    public class GroupItem{
        private char mChar;
        private D mData;

        public char getChar(){
            return mChar;
        }

        public D getData(){
            return mData;
        }
    }

    private class GroupData extends HashMap<Character,ArrayList<D>>{
        private List<Character> mKeys;
        private boolean mChanged = true;

        @Override
        public ArrayList<D> put(Character key, ArrayList<D> value) {
            mChanged = true;
            return super.put(key, value);
        }

        @Override
        public ArrayList<D> remove(Object key) {
            mChanged = true;
            return super.remove(key);
        }

        public GroupItem getGroupItem(int index){
            int i = -1;
            for(char c : getKeys()){
                i++;
                ArrayList<D> list = super.get(c);
                i+=list.size();

                if(i >= index){
                    int off = list.size() - (i - index);
                    GroupItem item = new GroupItem();
                    item.mChar = c;
                    if(off != 0){
                        item.mData = list.get(off - 1);
                    }

                    return item;
                }


            }

            throw new IndexOutOfBoundsException(String.valueOf(index));
        }


        public int getPositionByFilter(char filter){
            int i=0;

            for(char ch : getKeys()){
                if(ch == Character.toUpperCase(filter)){
                    return i;
                }

                i++;
                i+=super.get(ch).size();
            }

            return -1;
        }

        private List<Character> getKeys(){
            if(mChanged) {
                mKeys = new ArrayList<>(super.keySet());
                Collections.sort(mKeys);;
                mChanged = false;
            }

            return mKeys;
        }
    }
}
