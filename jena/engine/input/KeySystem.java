package jena.engine.input;

import jena.game.FrameEndListener;

public abstract class KeySystem implements FrameEndListener
{
    protected class SystemKey implements Key
    {
        int code;

        public SystemKey(int code)
        {
            this.code = code;
        }
        @Override
        public boolean isDown()
        {
            return states[code] == DOWN_STATE;
        }
        @Override
        public boolean isUp()
        {
            return states[code] == UP_STATE;
        }
        @Override
        public boolean isHold()
        {
            return states[code] != NONE_STATE;
        }
    }

    public KeySystem(int keysNumber)
    {
        states = new int[keysNumber];
    }

    protected int[] states;

    protected static final int NONE_STATE = 0;
    protected static final int DOWN_STATE = 1;
    protected static final int HOLD_STATE = 2;
    protected static final int UP_STATE = 3;

    @Override
    public void onEndFrame()
    {
        for (int i = 0; i < states.length; i++)
        {
            switch(states[i])
            {
                case DOWN_STATE: states[i] = HOLD_STATE; break;
                case UP_STATE: states[i] = NONE_STATE; break;
            };
        }
    }
}