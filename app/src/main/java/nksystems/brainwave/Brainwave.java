package nksystems.brainwave;

/**
 * @author  Charmy Shah
 * @date    28-04-2017
 * @version 1.0
 */
public class Brainwave {

    private String mText1;
    private String mText2;

    Brainwave(String text1, String text2){
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
