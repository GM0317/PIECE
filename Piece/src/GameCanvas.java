import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class GameCanvas extends JPanel implements ComponentListener{
	private static final long serialVersionUID = 5203228742370884076L;
	private Graphics buffer;
	private Image offScreen;
	private Dimension dim;
	private int countNumber = 0;
	private Player player;
	private Attack attack;
	private Laboratory laboratory;
	private Stage stage;
	private Item item;
	private LinkedList<Item> items;
	private static final int MAX_ITEMS = 4;
	public GameCanvas() {
		this.player = new Player();
		this.item = new Item(player);
		this.stage = new Laboratory(player, this);
		this.items = new LinkedList<>();
		spawnNewItem();
		addComponentListener(this);
		addKeyListener(player);
		setFocusable(true);	 //키를 눌렀을 때 동작이 되도록해줌.
		requestFocus(); //키를 눌렀을 때 동작이 되도록해줌.
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {		
			@Override
			public void run() {
				// TODO Auto-generated method stub
				repaint();
				counting();
			}
		}, 0, 1);
	}
	@Override
	public void paint(Graphics g) {
	    initBuffer(); //Offscreen buffer 초기화
	    stage.draw(buffer);
	    player.draw(buffer, this); //player 그리기
	 // Draw all items
        LinkedList<Item> itemsToRemove = new LinkedList<>();  // Items to be removed
        for (Item item : items) {
            item.draw(buffer);

            if (player.getRect().intersects(item.getRect())) {
                itemsToRemove.add(item);  // Add the item to the removal list
                item.onCollision();  // Handle collision logic
            }
        }
        // Remove items that need to be removed
        items.removeAll(itemsToRemove);
        
	    g.drawImage(this.offScreen, 0, 0, this);
    } 	   
	private void spawnNewItem() {
//        Item newItem = new Item();
//        items.add(newItem);
		if (items.size() < MAX_ITEMS) {
			//attack.refillAttacks();
            Item newItem = new Item(player);
            items.add(newItem);
        }
        
    }
	private void initBuffer() {
		this.dim = getSize();
		this.offScreen = createImage(dim.width, dim.height);
		this.buffer = this.offScreen.getGraphics();
	}
	public void counting() {
		this.countNumber++;
		
		// Spawn a new item every 500 counts (adjust as needed)
        if (getCount() % 500 == 0) {
            spawnNewItem();
        }
	}
	public int getCount() {
		return this.countNumber;
	}
	@Override
	public void update(Graphics g) {
		paint(g);
		
	}
	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		initBuffer();
	}
	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
