/**
 *  
 * 
 */


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

//-----------------------------------------------

public class Game extends Canvas implements Runnable
{
	//-----------------------------------------------
	/**
	 * To be honest, I don't understand why the compiler wants me to include a serial ID
	 * Do some research and find out
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean isRunning = false;
	private Thread thread;
	private Handler handler;
	private Camera camera;
	private SpriteSheet ss;
	
	private BufferedImage level = null; 
	private BufferedImage sprite_sheet = null;
	private BufferedImage floor = null;
	
	//-----------------------------------------------
	
	public Game()
	{
		new Window(1000, 563, "Wizard Game", this);
		start();
		
		handler = new Handler();
		camera = new Camera(0,0);
		this.addKeyListener(new KeyInput(handler));
		
		BufferedImageLoader loader = new BufferedImageLoader();
		level = loader.loadImage("/jupiter_fields.png");
		
		
		
		
		
		sprite_sheet = loader.loadImage("/sprite_sheet2.png");
		
		//handler.addObject(new Wizard(200,200, ID.Player, handler));
		// creates two box objects at the specified locations
		//handler.addObject(new Box(100, 100, ID.Block));
		//handler.addObject(new Box(200, 100, ID.Block)); 
		
		
		ss = new SpriteSheet(sprite_sheet);
		
		floor = ss.grabImage(4, 2, 32, 32);
		
		loadLevel(level);
		
	}
	
	//-----------------------------------------------
	
	private void start()
	{
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	//-----------------------------------------------
	
	private void stop()
	{
		isRunning = false;
		try 
		{
			thread.join();
		} 
		catch (InterruptedException e) 
		{
			
			e.printStackTrace();
		}
	}
	
	//-----------------------------------------------
	/**
	 * This code used a primary loop developed by the guys who made minecraft
	 * This loop is what sustains the games persistance and keeps the game window
	 * on the users desktop, as well as handling relevant fps updating
	 */
	public void run()
	{
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1_000_000_000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		int updates = 0;
		while(isRunning)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1)
			{
				tick();
				//delta--;
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				frames = 0;
				updates = 0;
			}
		}
		stop();
	}
	
	//-----------------------------------------------
	
	public void tick()
	{
		for(int i = 0; i < handler.object.size(); i++)
		{
			if(handler.object.get(i).getId() == ID.Player)
			{
				camera.tick(handler.object.get(i));
			}
		}
		handler.tick();
	}
	
	//-----------------------------------------------
	
	public void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null)
		{
			this.createBufferStrategy(3);  
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		//-----------------------------------
		//g.setColor(Color.blue);
		//g.fillRect(0, 0, 1000, 563);
		
		g2d.translate(-camera.getX(), -camera.getY());
		
		// this double for loop traverses the span of the whole map, and creates floor tiles at the appropriate locations
		for(int xx = 0; xx < 30 * 72; xx += 32)
		{
			for(int yy = 0; yy < 30 * 72; yy += 32)
			{
				g.drawImage(floor, xx, yy, null);
			}
		}
		handler.render(g);
		
		g2d.translate(camera.getX(), camera.getY());
		
		//-----------------------------------
		g.dispose();
		bs.show();
	}
	
	//-----------------------------------------------
	// loading the level
	private void loadLevel(BufferedImage image)
	{
		int w = image.getWidth();
		int h = image.getHeight();
		
		for(int xx = 0; xx < w; xx++)
		{
			for(int yy = 0; yy < h; yy++)
			{
				int pixel = image.getRGB(xx, yy);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				if(red == 255)
					handler.addObject(new Block(xx * 32, yy * 32, ID.Block, ss));
				
				if(blue == 255)
					handler.addObject(new Wizard(xx * 32, yy * 32, ID.Player, handler, ss));
				
			}
		}
	}
	
	//-----------------------------------------------
	
	public static void main(String [] args)
	{
		new Game();
	}
	
	//-----------------------------------------------
}
