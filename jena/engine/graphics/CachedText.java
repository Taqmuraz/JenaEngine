package jena.engine.graphics;

public final class CachedText implements Text
{
    String text;

    public CachedText(String text)
    {
        this.text = text;
    }

    @Override
    public void accept(TextAcceptor acceptor)
    {
        acceptor.call(text);
    }
}