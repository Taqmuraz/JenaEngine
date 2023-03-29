package jena.engine.math;

import java.util.Stack;

import jena.engine.common.Action;
import jena.engine.graphics.Transformation;

public class Matrix3fStack implements Matrix3fPipeline
{
    Stack<Matrix3f> stack;

    public Matrix3fStack()
    {
        stack = new Stack<Matrix3f>();
        stack.push(Matrix3fIdentity.identity);
    }

    @Override
    public void accept(Matrix3fAcceptor acceptor)
    {
        stack.peek().accept(acceptor);
    }

    public Matrix3f peek()
    {
        return stack.peek();
    }

    @Override
    public void matrixScope(Transformation transformation, Action action)
    {
        Matrix3f m = transformation.transform(stack.peek());
        stack.push(m);
        action.call();
        stack.pop();
    }
}