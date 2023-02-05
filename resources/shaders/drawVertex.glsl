in vec2 position;
in vec2 texcoord;

out vec2 uv;
uniform mat3 transform;

void main ()
{
	uv = texcoord;
	vec3 pos = transform * vec3(position.x, position.y, 1);
	gl_Position = vec4(pos.x, pos.y, 0, 1);
}