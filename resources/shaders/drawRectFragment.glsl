in vec2 uv;

out vec4 out_Color;

uniform sampler2D texture2d;
uniform vec4 color;

void main (void)
{	
	float min = 0.1;
	float max = 0.9;
	if ((uv.x < min || uv.x > max) && (uv.y < min || uv.y > max))
	{
		out_Color = color;
	}
	else
	{
		out_Color = vec4(0, 0, 0, 0);
	}
}