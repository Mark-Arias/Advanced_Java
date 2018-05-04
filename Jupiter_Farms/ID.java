/*
 * This enumeration is a list of the different types of objects that might
 * exist in the game at any given point, and allow us to distinguish between
 * the different types of objects, and to therefore create code that reacts according to the 
 * appropriate object type
 */
public enum ID 
{
	Player(),
	Block(),
	Crate(),
	Bullet(),
	Enemy();
}
