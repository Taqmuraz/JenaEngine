package jena.swing;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import jena.engine.input.Key;
import jena.engine.input.Keyboard;

public class SwingKeyboard extends KeySystem implements Keyboard, KeyListener
{
    public SwingKeyboard()
    {
        super(256);
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() < 256) states[e.getKeyCode()] = DOWN_STATE;
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        if (e.getKeyCode() < 256) states[e.getKeyCode()] = UP_STATE;
    }

    @Override
    public Key keyOf(char symbol)
    {
        return new SystemKey((int)Character.toUpperCase(symbol));
    }
}