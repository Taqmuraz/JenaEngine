in vec2 uv;

out vec4 out_Color;

uniform sampler2D textureSampler;

void main (void)
{	
	out_Color = texture(textureSampler, uv);
}