package com.hc.library.widget;

import java.util.Map;

/**
 * Created by xc on 2016/9/12.
 */
public interface BadgeHandler {
    Badge getChildBadge(int badgeId);
    void removeChildBadge(int badgeId);
    void addChildBadge(Badge badge);

    Map<Integer,Badge> getChildBadges();

    void setNum(int num);
    int getNum();
    void hideBadge();
    void showBadge();
}
