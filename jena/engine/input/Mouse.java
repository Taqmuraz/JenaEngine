package jena.engine.input;

import jena.engine.common.Action;
import jena.engine.math.Vector2fAcceptor;

public interface Mouse
{
    void acceptPosition(Vector2fAcceptor acceptor);
    void acceptState(int button, KeyStateAcceptor acceptor);
}