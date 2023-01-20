package jena.engine.entity.human;

import jena.engine.entity.FrameEndListener;
import jena.engine.entity.FrameStartListener;
import jena.engine.graphics.GraphicsClipPainter;

public interface HumanState extends GraphicsClipPainter, FrameStartListener, FrameEndListener
{
    void onEnter();
    void onExit();
    HumanState nextState();
}