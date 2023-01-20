package jena.opengl;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

import jena.engine.input.Key;
import jena.engine.input.KeySystem;
import jena.engine.input.Keyboard;

public class OpenGLKeyboard extends KeySystem implements Keyboard, KeyListener
{
    public OpenGLKeyboard()
    {
        super(256);
    }
    
    @Override
    public Key keyOf(char symbol)
    {
        return new SystemKey((int)Character.toUpperCase(symbol));
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int code = e.getKeyCode();
        if (code < 256 && !e.isAutoRepeat()) states[code] = DOWN_STATE;
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        int code = e.getKeyCode();
        if (code < 256 && !e.isAutoRepeat()) states[code] = UP_STATE;
    }
}