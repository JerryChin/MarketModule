package com.hc.library.widget;

import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xc on 2016/9/12.
 */
public abstract class Badge implements BadgeHandler{
    /**
     * 没字的徽章，一般是一个小红点
     * */
    public final static int TYPE_NO_TEXT = 1;

    public final static int TYPE_TEXT = 2;

    @IntDef({TYPE_NO_TEXT,TYPE_TEXT})
    @IntRange(from = 1)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration{}

    private @Duration int mType = TYPE_TEXT;

    public void setType(@Duration int type){
        mType = type;
    }

    protected @Duration int getType(){
        return mType;
    }

    int mBadgeId;

    private Badge mParentBadge;

    public Badge getParent(){
        return mParentBadge;
    }

    protected void setParent(Badge badge){
        mParentBadge = badge;
        mParentBadge.setNum(this.getNum() + mParentBadge.getNum());
    }

    private View mBadgeView;

    public void bindBadgeView(View badgeView){
        mBadgeView = badgeView;
    }

    public View getBadgeView(){
        return mBadgeView;
    }

    Badge(int badgeId){
        mBadgeId = badgeId;
        init();
    }

    private void init(){
    }


    public static Badge create(int badgeId){
        return new BadgeImpl(badgeId);
    }

    private static class BadgeImpl extends Badge{
        private int mNum = 0;
        private boolean mIsShown = false;
        private Map<Integer,Badge> mChildBadges;

        private BadgeImpl(int badgeId) {
            super(badgeId);
        }

        @Override
        public void setNum(int num) {
            int offset = num - mNum;

            mNum = num;

            Badge parent = getParent();

            if(parent != null){
                parent.setNum(getNum() + offset);
            }

            if(num == 0){
                hideBadge();
            }
            setNum(this,mNum);
        }

        @Override
        public int getNum() {
            return mNum;
        }

        private void setNum(Badge badge,int num){
            if(badge.getBadgeView() != null) {
                if (TYPE_TEXT == badge.getType()) {
                    ((TextView) badge.getBadgeView()).setText(String.valueOf(num));
                }
            }
        }

        @Override
        public void hideBadge() {
            mIsShown = false;
            toggleBadge(false);
        }

        @Override
        public void showBadge() {
            mIsShown = true;
            toggleBadge(true);
        }

        private void toggleBadge(boolean isShown){
            if(getBadgeView() != null) {
                getBadgeView().setVisibility(isShown ? View.VISIBLE : View.GONE);
            }
        }

        @Override
        public void addChildBadge(@NonNull Badge badge){
            if(mChildBadges == null){
                mChildBadges = new HashMap<>();
            }

            badge.setParent(this);
            mChildBadges.put(badge.mBadgeId,badge);

            setNum(getNum() + badge.getNum());
        }

        @Override
        public Map<Integer, Badge> getChildBadges() {
            return mChildBadges;
        }

        @Override
        public void removeChildBadge(int badgeId){
            if(mChildBadges != null){
                Badge badge = mChildBadges.remove(badgeId);

                if(badge != null){
                    setNum(getNum() - badge.getNum());
                }
            }
        }

        @Override
        public Badge getChildBadge(int badgeId){
            if(mChildBadges == null){
                return null;
            }

            return mChildBadges.get(badgeId);
        }

        @Override
        public void bindBadgeView(View badgeView) {
            super.bindBadgeView(badgeView);

            setNum(this,mNum);
            toggleBadge(mIsShown);
        }
    }
}
