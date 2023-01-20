package jena.opengl;

import com.jogamp.opengl.GL2;

import jena.engine.graphics.GraphicsClipPainter;
import jena.engine.graphics.GraphicsDevice;
import jena.engine.math.Matrix3fPipeline;
import jena.engine.math.Rectf;

public class OpenGLDevice implements GraphicsDevice
{
    GL2 gl;
    Rectf paintArea;

    public OpenGLDevice(GL2 gl, Rectf paintArea)
    {
        this.gl = gl;
        this.paintArea = paintArea;
    }

    @Override
    public void paintRect(Rectf rect, GraphicsClipPainter painter)
    {
        paintArea.accept((px, py, pw, ph) -> rect.accept((x, y, w, h) ->
        {
            OpenGLMatrixPipeline pipeline = new OpenGLMatrixPipeline(gl);
            pipeline.rectScope(a -> a.call(x + px, y + py, w, h), () ->
            {
                painter.paint(new OpenGLClip(gl, pipeline));
            });
            /*gl.glPushMatrix();
            gl.glLoadIdentity();
            gl.glViewport((int)(x + px), (int)(y + py), (int)w, (int)h);
            gl.glOrtho(0, w, h, 0, -1f, 1f);

            new OpenGLClip(gl).fillRect(a -> a.call(0, 0, pw * 0.5f, ph * 0.5f), a -> a.call(50, 100, 100, 255));

            gl.glBegin(GL2.GL_QUADS);

            float n = 100;
            float m = 600;

            Color c = a -> a.call(100, 100, 100, 255);
            new ColorFloatStruct(c).acceptFloats(gl::glColor4f);
            //gl.glColor3f(1f, 1f, 0f);
            gl.glVertex2f(n, n);
            gl.glVertex2f(n, m);
            gl.glVertex2f(m, m);
            gl.glVertex2f(m, n);
            gl.glEnd();
            gl.glPopMatrix();
            */
        }));
    }
}