package jena.engine.graphics;

import jena.engine.common.ActionSingle;
import jena.engine.math.Rectf;

public interface GraphicsDevice
{
    void paintRect(Rectf rect, ActionSingle<GraphicsClip> paint);
}