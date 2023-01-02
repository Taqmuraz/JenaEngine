package jena.engine.graphics;

public interface Color
{
    void acceptBytes(ColorBytesAcceptor acceptor);
    void acceptInts(ColorIntsAcceptor acceptor);
    void acceptFloats(ColorFloatsAcceptor acceptor);
}
