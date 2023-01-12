package jena.swing;

import jena.engine.input.Key;

public abstract class KeySystem
{
	protected class SystemKey implements Key
	{
		int code;

		SystemKey(int code)
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

	static final int NONE_STATE = 0;
	static final int DOWN_STATE = 1;
	static final int HOLD_STATE = 2;
	static final int UP_STATE = 3;

	public void updateState()
	{
		for (int i = 0; i < states.length; i++)
		{
			states[i] = switch(states[i])
			{
				case DOWN_STATE -> HOLD_STATE;
				case UP_STATE -> NONE_STATE;
				default -> states[i];
			};
		}
	}
}