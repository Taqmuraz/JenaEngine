package jena.swing;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import jena.engine.input.Key;
import jena.engine.input.Keyboard;

public class SwingKeyboard implements Keyboard, KeyListener
{
	class SwingKey implements Key
	{
		int code;

		SwingKey(int code)
		{
			this.code = code;
		}
		@Override
		public boolean isDown()
		{
			return keyStates[code] == DOWN_STATE;
		}
		@Override
		public boolean isUp()
		{
			return keyStates[code] == UP_STATE;
		}
		@Override
		public boolean isHold()
		{
			return keyStates[code] != NONE_STATE;
		}
	}

	int[] keyStates;

	static final int NONE_STATE = 0;
	static final int DOWN_STATE = 1;
	static final int HOLD_STATE = 2;
	static final int UP_STATE = 3;

	public SwingKeyboard()
	{
		keyStates = new int[256];
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() < 256) keyStates[e.getKeyCode()] = DOWN_STATE;
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() < 256) keyStates[e.getKeyCode()] = UP_STATE;
	}

	public void updateKeys()
	{
		for (int i = 0; i < keyStates.length; i++)
		{
			keyStates[i] = switch(keyStates[i])
			{
				case DOWN_STATE -> HOLD_STATE;
				case UP_STATE -> NONE_STATE;
				default -> keyStates[i];
			};
		}
	}

	@Override
	public Key keyOf(char symbol)
	{
		return new SwingKey((int)Character.toUpperCase(symbol));
	}
}