/*
 * Copyright (c) 2017. NKSystems
 *
 * Created on : 28-04-2017
 * Author     : Charmy Shah
 *
 * All rights reserved
 */

package nksystems.brainwave;

public class object_about_brainwave {

    private String mText1;
    private String mText2;

    object_about_brainwave(String text1, String text2){
        mText1 = text1;
        mText2 = text2;
    }

    public String getmText1() {
        return mText1;
    }

    public void setmText1(String mText1) {
        this.mText1 = mText1;
    }

    public String getmText2() {
        return mText2;
    }

    public void setmText2(String mText2) {
        this.mText2 = mText2;
    }
}
