in vec2 uv;

out vec4 out_Color;

uniform sampler2D texture2d;
uniform vec4 color;

void main (void)
{
	float r = length(uv - vec2(0.5, 0.5));
	if (r < 0.5)
	{
		out_Color = color;
	}
	else
	{
		out_Color = vec4(0, 0, 0, 0);
	}
}