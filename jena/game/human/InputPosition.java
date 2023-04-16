package jena.game.human;

import jena.engine.math.FieldVector2f;
import jena.engine.math.ValueFloat;
import jena.engine.math.ValueFloatStruct;
import jena.engine.math.Vector2f;
import jena.engine.math.Vector2fAcceptor;
import jena.engine.math.Vector2fAdd;
import jena.engine.math.Vector2fMul;
import jena.engine.math.Vector2fStruct;
import jena.game.DeltaTime;
import jena.game.FrameStartListener;
import jena.game.Time;

public class InputPosition implements Vector2f, FrameStartListener
{
    Vector2f movement;
    Vector2fStruct total;
    Vector2f origin;
    Vector2f fieldPoint;
    ValueFloat deltaTime;

    public InputPosition(Vector2f input, Vector2f origin, FieldVector2f walkField)
    {
        this.movement = new Vector2fMul(input, new ValueFloatStruct(3f));
        this.total = new Vector2fStruct();
        this.origin = origin;
        deltaTime = new DeltaTime(new Time());
        fieldPoint = walkField.project(new Vector2fAdd(total, origin));
    }

    @Override
    public void onStartFrame()
    {
        movement.accept((x, y) -> deltaTime.accept(dt ->
        {
            total.x += x * dt;
            total.y += y * dt;
        }));
        fieldPoint.accept((x, y) -> origin.accept((ox, oy) ->
        {
            total.x = x - ox;
            total.y = y - oy;
        }));
    }

    @Override
    public void accept(Vector2fAcceptor acceptor) 
    {
        total.accept(acceptor);
    }
}