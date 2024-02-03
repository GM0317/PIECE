import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Attack {
	private State[] states;
	private int stateIdx = 0;
	private BufferedImage sprite;
	
    private int x; // 공격 x 좌표
    private int y; // 공격 y 좌표
    private int speed; // 공격 속도
    private int direction; // 공격 방향
    private Image attack; // 공격 이미지

    private boolean isAttacking = false; // 공격 중인지 여부
    private boolean isShooting = false;
    // 움직이는 상태
    private boolean left;
    private boolean right;
    private boolean up;
    //protected Image boom;
    
    private static final int MAX_ATTACK_COUNT = 2; // 최대 공격 횟수
    private int attackCount = MAX_ATTACK_COUNT; // 현재 공격 횟수
    private Image[] boom; // 공격 이미지 배열
    
    public Attack(int x, int y, int speed, int direction) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direction = direction;
        loadImage();
        states = new State[6];
        State state = new State();
        states[0] = state;
           
        state.width = 45;
        state.height = 105;
        state.index_x = 0;
        state.start_x = 0;
        state.start_y = 0;
        state.frame_size = 5;
        
        state = new State();
        states[1] = state;
        state.width = 45;
        state.height = 105;
        state.index_x = 1;
        state.start_x = 480;
        state.start_y = 0;
        state.frame_size = 3;
        state.stop = true;
        
        state = new State();
        states[2] = state;
        state.width = 45;
        state.height = 105;
        state.index_x = 2;
        state.start_x = 0;
        state.start_y = 0;
        state.frame_size = 6;
        
        state = new State();
        states[3] = state;
        state.width = 45;
        state.height = 105;
        state.index_x = 3;
        state.start_x = 540;
        state.start_y = 0;
        state.frame_size = 6;
        
        state = new State();
        states[4] = state;
        state.width = 45;
        state.height = 105;
        state.index_x = 4;
        state.start_x = 540;
        state.start_y = 0;
        state.frame_size = 6;
       
        state = new State();
        states[5] = state;
        state.width = 45;
        state.height = 105;
        state.index_x = 5;
        state.start_x = 0;
        state.start_y = 0;
        state.frame_size = 2;
        state.stop = true;
        
        boom = new Image[MAX_ATTACK_COUNT]; // 공격 이미지 배열 초기화
        for (int i = 1; i <= MAX_ATTACK_COUNT; i++) {
            try {
                boom[i - 1] = ImageIO.read(new File("attack/Bullet.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadImage() {
        try {
            //this.boom = ImageIO.read(new File("attack/Bullet.png"));
            this.sprite = ImageIO.read(new File("attack/7_1.png"));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private State getState() {
    	return states[stateIdx];
    }
    private void drawAttack(State state, Graphics g, GameCanvas screen) {
        g.drawImage(sprite, 
              x, y,  //위치 
              x + state.width, y + state.height, //크기 
              state.width*state.index_x + state.start_x, 
              state.height*state.index_y + state.start_y, 
              state.width*state.index_x + state.start_x + state.width, 
              state.height*state.index_y + state.start_y + state.height, 
              screen);
        
        if(screen.getCount() % 100 == 0)
        {
           if(state.index_x < state.frame_size-1)
           {
              state.index_x++;
           }
           else
           {
              if(!state.stop)
                 state.index_x = 0;
              else
                 state.index_x = state.frame_size-1;
           }
        }   
     }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move() {
        // 공격의 위치를 이동
        // 총알의 이동 로직을 직선으로 수정
        // x += speed;
        switch (direction) {
            case 1:
                // 위
                y -= speed;
                break;
            case 2:
                // 오른쪽
                x += speed;
                break;
            case 3:
                // 왼쪽
                x -= speed;
                break;
            case 4:
                // 아래
                y += speed;
                break;
        }
        // System.out.println("x: " + x + ", speed: " + speed + ", direction:" + direction);
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }
    
    public boolean canAttack() {
        return attackCount > 0; // 공격 가능한 상태인지 확인
    }

    public void performAttack() {
        if (attackCount > 0) {
            // 실제 공격 로직
            attackCount--; // 공격 횟수 감소
        }
    }
    public int getAttackCount() {
        return attackCount;
    }

    public void refillAttacks() {
        attackCount = MAX_ATTACK_COUNT;
    }
    public void draw(Graphics g, GameCanvas gameCanvas) {
    	
    	canAttack();
    	performAttack();
        //g.drawImage(boom, x, y, 50, 50, null); // 공격 이미지 생성
        drawAttack(getState(), g, gameCanvas);
    }
}
