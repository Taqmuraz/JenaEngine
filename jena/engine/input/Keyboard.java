package jena.engine.input;

public interface Keyboard
{
    void acceptState(int key, KeyStateAcceptor acceptor);
}