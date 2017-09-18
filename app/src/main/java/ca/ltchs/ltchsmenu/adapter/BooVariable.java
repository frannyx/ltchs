package ca.ltchs.ltchsmenu.adapter;

/**
 * Created by sabinashiwji on 2017-07-27.
 */

//class for listener boolean
public class BooVariable {
    private boolean boo = false;
    private ChangeListener listener;

    public BooVariable(){}

    public boolean isBoo() {
        return boo;
    }

    public void setBoo(boolean boo) {
        this.boo = boo;
        if (listener != null) listener.onChange();
    }

    public ChangeListener getListener() {
        return listener;
    }

    public void setListener(ChangeListener listener) {
        this.listener = listener;
    }

    public interface ChangeListener {
        void onChange();
    }



}
