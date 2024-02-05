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
	
	private boolean down;
	 private boolean jumping = false;
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
		//down();
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
        drawRedRectangle(g, 0, 440, 290, 600);
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
		                new Rectangle(0, 435, 290, 600),
		                new Rectangle(290, 340, 156, 600),
		                new Rectangle(450, 341, 154, 30),
		                new Rectangle(847, 310, 154, 30),
		                new Rectangle(445, 465, 160, 600),
		                new Rectangle(750, 465, 270, 600),
		                new Rectangle(673, 310, 60, 60),
		                new Rectangle(609, 340, 60, 30)
		        };
			 Rectangle playerBox = player.getRect();
			 boolean onGround = checkOnGround(playerBox, tileLine);

		        int playerX = player.getX();
		        int playerY = player.getY();

		        // 플레이어의 새 위치 계산
		        int newX = playerX;
		        int newY = playerY;
		        
		        // 각 경계와의 충돌을 확인
		        for (Rectangle boundary : tileLine) {
		            if (playerBox.intersects(boundary)) {
		                // 충돌이 발생하면 플레이어의 위치를 조정
		            	if (playerX < boundary.x) {
		                    newX = boundary.x - playerBox.width; // 왼쪽 충돌: 왼쪽으로 위치 조정
		                } else if (playerX > boundary.x + boundary.width) {
		                    newX = boundary.x + boundary.width; // 오른쪽 충돌: 오른쪽으로 위치 조정
		                }
		                if (playerY < boundary.y) {
		                    newY = boundary.y - playerBox.height; // 위쪽 충돌: 위로 위치 조정
		                } else if (playerY > boundary.y + boundary.height) {
		                    newY = boundary.y + boundary.height; // 아래쪽 충돌: 아래로 위치 조정
		                }
		                System.out.println("충돌 발생!");
		            }
		        }

		        // 위치 업데이트
		        player.setX(newX);
		        player.setY(newY);
		     // 다운 동작
	            if (!onGround && !player.isJump()) {
	                down();
	            }
		    } else {
		        System.out.println("플레이어가 null입니다.");
		    }
		}
	
	private void drawRedRectangle(Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.RED);
        g.drawRect(x, y, width, height);
        g.setColor(Color.BLACK); // 원래 색상으로 돌려놓기 위해 검은색으로 설정
    }
	private boolean checkOnGround(Rectangle playerBox, Rectangle[] tileLine) {
        for (Rectangle boundary : tileLine) {
            if (playerBox.intersects(boundary) && playerBox.y + playerBox.height <= boundary.y) {
                // 플레이어의 아래쪽이 경계선 위에 있으면서 충돌이 발생하면 바닥에 있다고 판단
                return true;
            }
        }
        return false;
    }
	public void down() {
	    down = true;
	    boolean onGround = false;
	    int playerX = player.getX();
        int playerY = player.getY();
        int newX = playerX;
        int newY = playerY;
	    if (!onGround) {
            player.setY(player.getY() + 1); // 플레이어를 아래로 내림 (중력)
            
        }else if(player.isJump()) {
        	player.setY(player.getY()+ 0);
        }

//	    // 다운 동작을 위한 새로운 스레드 시작
	    new Thread(() -> {
//	        // 다운 동작
//	        while (down) {
//	            int currentY = player.getY();
//	            player.setY(currentY + 0);
//
	            try {
	                Thread.sleep(5);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        
	    }).start();
	}
}
