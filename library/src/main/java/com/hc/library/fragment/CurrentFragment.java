package com.hc.library.fragment;

import android.support.v4.app.Fragment;
import android.view.ViewGroup;

import com.hc.library.widget.CurrentPage;

/**
 * Created by xc on 2016/9/6.
 */
final class CurrentFragment {
    private Fragment mCurrentFragment;

    CurrentFragment(){

    }

    void setPrimaryItem(ViewGroup container, int position, Object object){
        Fragment newFragment = (Fragment) object;
        if(newFragment instanceof BaseFragment){
            final BaseFragment fragment = (BaseFragment)newFragment;
            if(fragment.isCreated()){
                innerSetItem(fragment);
            }else{
                fragment.setOnCreatedFragmentListener(new BaseFragment.OnCreatedFragmentListener() {
                    @Override
                    public void onCreated() {
                        innerSetItem(fragment);
                    }
                });
            }
        }else {
            innerSetItem(newFragment);
        }
    }

    private void innerSetItem(Fragment newFragment){
        if (!newFragment.equals(mCurrentFragment)) {
            if (mCurrentFragment != null && mCurrentFragment instanceof FragmentToggle) {
                ((FragmentToggle) mCurrentFragment).onLeave(newFragment, mCurrentFragment);
            }

            if (newFragment instanceof FragmentToggle) {
                ((FragmentToggle) newFragment).onEnter(newFragment, mCurrentFragment);
            }

            mCurrentFragment = newFragment;
        }
    }

    Fragment getCurrentPage(){
        return mCurrentFragment;
    }
}
