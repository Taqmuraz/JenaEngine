in vec2 position;
in vec2 texcoord;

out vec2 uv;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main ()
{
	uv = texcoord;
	gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1);
}