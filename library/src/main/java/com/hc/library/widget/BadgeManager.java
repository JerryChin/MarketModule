package com.hc.library.widget;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by xc on 2016/9/12.
 */
public class BadgeManager {
    private Map<Integer,Badge> mBadgeMap;

    private static BadgeManager mInstance;

    public BadgeManager getInstance(){
        if(mInstance == null){
            mInstance = new BadgeManager();
        }

        return mInstance;
    }


    private BadgeManager(){
    }

    public Badge getBadge(int badgeId){
        return mBadgeMap == null ? null : stackGetBadge(mBadgeMap,badgeId);
    }

    private Badge stackGetBadge(@NonNull Map<Integer,Badge>badgeMap, int badgeId){
        Badge badge = badgeMap.get(badgeId);
        if(badge != null){
            return badge;
        }

        Set<Integer> keys = badgeMap.keySet();
        for(int key : keys){
            Badge b = mBadgeMap.get(key);
            if(b.getChildBadges() != null){
                return stackGetBadge(b.getChildBadges(),badgeId);
            }
        }

        return null;
    }

    public void addBadge(Badge badge){
        if(mBadgeMap == null){
            mBadgeMap = new HashMap<>();
        }
        mBadgeMap.put(badge.mBadgeId,badge);
    }

    public void removeBadge(Badge badge){
        if(mBadgeMap != null){
            mBadgeMap.remove(badge.mBadgeId);
        }
    }
}
