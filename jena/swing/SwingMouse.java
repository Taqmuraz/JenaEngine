package jena.swing;

import jena.engine.input.Key;
import jena.engine.input.KeySystem;
import jena.engine.input.Mouse;
import jena.engine.math.Vector2f;
import jena.engine.math.Vector2fStruct;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class SwingMouse extends KeySystem implements Mouse, MouseListener, MouseMotionListener
{
    Vector2fStruct position;

    public SwingMouse()
    {
        super(3);
        position = new Vector2fStruct();
    }

    @Override
    public Vector2f position()
    {
        return position;
    }

    @Override
    public Key button(int button)
    {
        return new SystemKey(button);
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        states[e.getButton() - 1] = DOWN_STATE;
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        states[e.getButton() - 1] = UP_STATE;
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        position.x = e.getX();
        position.y = e.getY();
    }
}