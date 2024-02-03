import java.awt.Image;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.ImageIcon;

public class Item {
	private Player player;
	private Attack attack;
    private Image bulletImage;
    private int x, y;  // 아이템 위치
    private int width, height;  // 아이템 크기
    public Item(Player player) {
    	//Player player = new Player(); // Create an instance of the Player class
    	this.player = player;
        // 아이템 이미지 로드 (이미지 파일 경로에 맞게 수정 필요)
        ImageIcon icon = new ImageIcon("attack/Bullet.png");
        bulletImage = icon.getImage();

        // 아이템 크기 설정 (이미지 크기에 맞게 수정 필요)
        width = 40;
        height = 30;

        // 랜덤한 위치에 아이템 생성
        Random rand = new Random();
        x = rand.nextInt(GameScreen.WIDTH - width);
        y = rand.nextInt(GameScreen.HEIGHT - height);
    }

    // 아이템 그리기
    public void draw(Graphics g) {
        g.drawImage(bulletImage, x, y, width, height, null);
    }

    // 아이템의 충돌 영역 반환
    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }
    public void update() {
        // 아이템 고유한 로직을 여기에 구현
    }
    // 캐릭터가 아이템을 먹었을 때의 동작 (원하는 동작으로 수정 필요)
    public void onCollision() {
    	player.refillAttacks(); // Increase player's attack count
    }
}
