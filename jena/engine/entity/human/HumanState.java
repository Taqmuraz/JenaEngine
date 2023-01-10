package jena.engine.entity.human;

import jena.engine.entity.FrameEndHandler;
import jena.engine.entity.FrameStartHandler;
import jena.engine.graphics.GraphicsClipPainter;

public interface HumanState extends GraphicsClipPainter, FrameStartHandler, FrameEndHandler
{
    void onEnter();
    void onExit();
    HumanState nextState();
}