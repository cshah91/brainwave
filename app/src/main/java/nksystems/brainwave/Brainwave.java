package nksystems.brainwave;

/**
 * @author Charmy Shah
 * @version 1.0
 * @date 28-04-2017
 */
public class Brainwave {

    private String mText1;
    private String mText2;

    /**
     * @param text1
     * @param text2
     */
    Brainwave(String text1, String text2) {
        mText1 = text1;
        mText2 = text2;
    }

    /**
     * @return
     */
    public String getmText1() {
        return mText1;
    }

    /**
     * @param mText1
     */
    public void setmText1(String mText1) {
        this.mText1 = mText1;
    }

    /**
     * @return
     */
    public String getmText2() {
        return mText2;
    }

    /**
     * @param mText2
     */
    public void setmText2(String mText2) {
        this.mText2 = mText2;
    }
}
