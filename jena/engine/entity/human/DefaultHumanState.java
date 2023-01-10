package jena.engine.entity.human;

import jena.engine.graphics.GraphicsClip;

public class DefaultHumanState implements HumanState
{
    @Override
    public void paint(GraphicsClip clip)
    {

    }

    @Override
    public void onStartFrame()
    {
    }

    @Override
    public void onEndFrame()
    {
    }

    @Override
    public void onEnter()
    {   
    }

    @Override
    public void onExit()
    {
    }

    @Override
    public HumanState nextState()
    {
        return this;
    }
}