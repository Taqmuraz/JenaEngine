in vec2 uv;

out vec4 out_Color;

uniform sampler2D texture2d;
uniform vec4 color;

void main (void)
{
	out_Color = color;
}