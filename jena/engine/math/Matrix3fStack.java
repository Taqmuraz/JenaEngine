package jena.engine.math;

import java.util.Stack;

import jena.engine.common.Action;

public class Matrix3fStack implements Matrix3fPipeline
{
    Stack<Matrix3f> stack;

    public Matrix3fStack()
    {
        stack = new Stack<Matrix3f>();
        stack.push(Matrix3fIdentity.identity);
    }

    @Override
    public Matrix3f peek()
    {
        return stack.peek();
    }

    @Override
    public void matrixScope(Matrix3f matrix, Action action)
    {
        stack.push(matrix);
        action.call();
        stack.pop();
    }
}