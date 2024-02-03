import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

public class PlayerHp {
    private int hp;
    private Image Heart6;
    private Image Heart5;
    private Image Heart4;
    private Image Heart3;
    private Image Heart2;
    private Image Heart1;
    private Image Heart0;

    public PlayerHp() { // Player를 받는 생성자 추가
        this.hp = 300; // 체력을 500으로 초기화
        Heart6 = new ImageIcon("Hp/Heart6.png").getImage(); // 이미지 파일 가져오기
        Heart5 = new ImageIcon("Hp/Heart5.png").getImage();
        Heart4 = new ImageIcon("Hp/Heart4.png").getImage();
        Heart3 = new ImageIcon("Hp/Heart3.png").getImage();
        Heart2 = new ImageIcon("Hp/Heart2.png").getImage();
        Heart1 = new ImageIcon("Hp/Heart1.png").getImage();
        Heart0 = new ImageIcon("Hp/Heart0.png").getImage();
    }
    
    public void decreaseHp(int amount) { // Hp 감소시키기
        hp -= amount;
        statueSound(new File("Sound/damaged7.wav"));
        if (hp < 0) {
            hp = 0;
            System.out.println("hp감소");
           
        }
        
    }
    public void returnHP(int num) {
    	hp=num;
    }
    
    public int getHp() {
        return hp; // 플레이어 체력을 반환
    }
    private void statueSound(File file) {
		Clip clip = null;
		try {
		clip = AudioSystem.getClip();
		clip.open(AudioSystem.getAudioInputStream(file));
		clip.start();
		} catch (Exception e) {
		e.printStackTrace();
		}
		
	}
	public void draw(Graphics g) {
            if (hp >= 300) {
                g.drawImage(Heart6, 18, 18, 98, 30, null); // hp이미지 생성
            } else if (hp >= 250) {
                g.drawImage(Heart5, 18, 18, 98, 30, null); // hp 이미지 생성
            } else if (hp >= 200) {
            	g.drawImage(Heart4, 18, 18, 98, 30, null); // hp 이미지 생성
            } else if (hp >= 150) {
            	g.drawImage(Heart3, 18, 18, 98, 30, null); // hp 이미지 생성
            } else if (hp >= 100) {
            	g.drawImage(Heart2, 18, 18, 98, 30, null); // hp 이미지 생성
            } else if (hp >= 50) {
            	g.drawImage(Heart1, 18, 18, 98, 30, null); // hp 이미지 생성
            } else {
            	g.drawImage(Heart0, 18, 18, 98, 30, null); // hp 이미지 생성
            }
            // HP 값을 그래픽으로 표시
//            g.setColor(Color.WHITE);
//            g.drawString("HP: " + hp, 18, 60); // hp가 감소하는지 확인하기 위해 만듬.
	}

}
