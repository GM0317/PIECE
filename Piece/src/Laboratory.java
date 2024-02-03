import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Laboratory extends Stage{
	protected Image bg;
	protected Image object;
	protected Image waste;
	protected Image Tile;
	
	private Rectangle[] tileLine;

	private GameCanvas canvas;
	private Player player;
	public Laboratory(Player player, GameCanvas canvas) {
		this.canvas = canvas;
		this.player = player; //초기화
		
		bg = new ImageIcon("Laboratory/실험실 밑바탕.png").getImage();
		object = new ImageIcon("Laboratory/실험실 오브젝트.png").getImage();
		waste = new ImageIcon("Laboratory/실험실 폐기물.png").getImage();
		Tile = new ImageIcon("Laboratory/실험실 Tile.png").getImage();
	}
	@Override
    public void draw(Graphics g) {
		checkTile();
        // Laboratory를 그리는 논리를 추가
        // 예를 들어, 이미지들을 그리는 작업 등을 수행
        g.drawImage(bg, 0, 0, null);
        g.drawImage(object, 0, 0, null);
        g.drawImage(waste, 0, 0, null);
        g.drawImage(Tile, 0, 0, null);
        // 다른 작업들을 추가로 수행
        //경계선 도면
        drawRedRectangle(g, 0, 0, 30, 310);
        drawRedRectangle(g, 30, 0, 65, 125);
        drawRedRectangle(g, 95, 0, 91, 310);
        drawRedRectangle(g, 186, 0, 186, 185);
        drawRedRectangle(g, 372, 0, 250, 30);
        drawRedRectangle(g, 621, 0, 185, 215);
        drawRedRectangle(g, 434, 155, 185, 30);
        //경계선 도면 바닥
        drawRedRectangle(g, 0, 445, 290, 600);
        drawRedRectangle(g, 290, 350, 156, 600);
        drawRedRectangle(g, 450, 350, 154, 30);
        drawRedRectangle(g, 847, 320, 154, 30);
        drawRedRectangle(g, 445, 475, 160, 600);
        drawRedRectangle(g, 750, 475, 270, 600);
        drawRedRectangle(g, 673, 320, 60, 60);
        drawRedRectangle(g, 609, 350, 60, 30);
        
    }
	public void checkTile() {
		 if (player != null) {
			 tileLine = new Rectangle[]{
		                new Rectangle(0, 0, 30, 310),
		                new Rectangle(30, 0, 65, 125),
		                new Rectangle(95, 0, 91, 310),
		                new Rectangle(186, 0, 186, 185),
		                new Rectangle(372, 0, 250, 30),
		                new Rectangle(621, 0, 185, 215),
		                new Rectangle(434, 155, 185, 30),

		                // 바닥에 해당하는 경계선
		                new Rectangle(0, 445, 290, 600),
		                new Rectangle(290, 350, 156, 600),
		                new Rectangle(450, 350, 154, 30),
		                new Rectangle(847, 320, 154, 30),
		                new Rectangle(445, 475, 160, 600),
		                new Rectangle(750, 475, 270, 600),
		                new Rectangle(673, 320, 60, 60),
		                new Rectangle(609, 350, 60, 30)
		        };
			 	Rectangle playerBox = player.getRect();
			 	
			 	int playerX = player.getX();
			    int playerY = player.getY();

			    // 플레이어의 새로운 위치 계산
			    int newX = playerX;
			    int newY = playerY;

			    // 각 경계와의 충돌을 확인
			    for (Rectangle boundary : tileLine) {
			        if (playerBox.intersects(boundary)) {
			            System.out.println("Ah!");
			            return;
			        }
			    }
			    // 충돌이 없으면 위치를 업데이트
			    player.setX(newX);
			    player.setY(newY);
		 }else {
			 System.out.println("null");
		 }
		    
	}
	private void drawRedRectangle(Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.RED);
        g.drawRect(x, y, width, height);
        g.setColor(Color.BLACK); // 원래 색상으로 돌려놓기 위해 검은색으로 설정
    }

}
