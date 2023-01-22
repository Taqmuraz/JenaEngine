in vec2 uv;

out vec4 out_Color;

uniform sampler2D texture2d;

void main (void)
{	
	out_Color = texture(texture2d, uv);
}