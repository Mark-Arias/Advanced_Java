import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Wizard  extends GameObject
{
	Handler handler;
	
	
	private BufferedImage wizard_image;
	
	public Wizard(int x, int y, ID id, Handler handler, SpriteSheet ss) {
		super(x, y, id, ss);
		this.handler = handler;
		
		wizard_image = ss.grabImage(1, 1, 32, 48);
	}

	
	public void tick()
	{
		x += velX;
		y += velY;
		
		// this method checks if collision has occured
		collision();
		
		// code block below controls movement
		if(handler.isUp()){
			velY = -5;
		} else if(!handler.isDown()) {
			velY = 0;
		}
		
		if(handler.isDown()) velY = 5;
		else if(!handler.isUp()) velY = 0;
		
		if(handler.isRight()) velX = 5;
		else if(!handler.isLeft()) velX = 0;
		
		if(handler.isLeft()) velX = -5;
		else if(!handler.isRight()) velX = 0;
		
	}
	
	private void collision()
	{
		for(int i = 0; i < handler.object.size(); i++)
		{
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.Block)
			{
				if(getBounds().intersects(tempObject.getBounds()))
				{
					x += velX * -1;
					y += velY * -1;
				}
			}
		}
	}

	
	public void render(Graphics g) {
		//g.setColor(Color.BLACK);
		//g.fillRect(x, y, 32, 48);
		g.drawImage(wizard_image, x, y, null);
	}

	
	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 48);
		
	}

}
