package com.ognl.study;


import ognl.MemberAccess;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.Map;

/**
 * @author xuedui.zhao
 * @create 2018-11-30
 */
public class MyMemberAccess implements MemberAccess {
    public boolean allowPrivateAccess;
    public boolean allowProtectedAccess;
    public boolean allowPackageProtectedAccess;

    public MyMemberAccess(boolean allowAllAccess) {
        this(allowAllAccess, allowAllAccess, allowAllAccess);
    }

    public MyMemberAccess(boolean allowPrivateAccess, boolean allowProtectedAccess, boolean allowPackageProtectedAccess) {
        this.allowPrivateAccess = false;
        this.allowProtectedAccess = false;
        this.allowPackageProtectedAccess = false;
        this.allowPrivateAccess = allowPrivateAccess;
        this.allowProtectedAccess = allowProtectedAccess;
        this.allowPackageProtectedAccess = allowPackageProtectedAccess;
    }

    public boolean getAllowPrivateAccess() {
        return this.allowPrivateAccess;
    }

    public void setAllowPrivateAccess(boolean value) {
        this.allowPrivateAccess = value;
    }

    public boolean getAllowProtectedAccess() {
        return this.allowProtectedAccess;
    }

    public void setAllowProtectedAccess(boolean value) {
        this.allowProtectedAccess = value;
    }

    public boolean getAllowPackageProtectedAccess() {
        return this.allowPackageProtectedAccess;
    }

    public void setAllowPackageProtectedAccess(boolean value) {
        this.allowPackageProtectedAccess = value;
    }

    public Object setup(Map context, Object target, Member member, String propertyName) {
        Object result = null;
        if (this.isAccessible(context, target, member, propertyName)) {
            AccessibleObject accessible = (AccessibleObject)member;
            if (!accessible.isAccessible()) {
                result = Boolean.TRUE;
                accessible.setAccessible(true);
            }
        }

        return result;
    }

    public void restore(Map context, Object target, Member member, String propertyName, Object state) {
        if (state != null) {
            ((AccessibleObject)member).setAccessible((Boolean)state);
        }

    }

    public boolean isAccessible(Map context, Object target, Member member, String propertyName) {
        int modifiers = member.getModifiers();
        boolean result = Modifier.isPublic(modifiers);
        if (!result) {
            if (Modifier.isPrivate(modifiers)) {
                result = this.getAllowPrivateAccess();
            } else if (Modifier.isProtected(modifiers)) {
                result = this.getAllowProtectedAccess();
            } else {
                result = this.getAllowPackageProtectedAccess();
            }
        }

        return result;
    }
}
