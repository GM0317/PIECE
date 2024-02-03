import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class Player implements KeyListener{
	private GameScreen gameScreen;
	private Stage stage;
	 private Attack attack;
	private PlayerHp hp;
	private BufferedImage sprite;
	private BufferedImage standing;
	private BufferedImage jumping;
	private BufferedImage Crawling;
	private BufferedImage Crawling2;
	private BufferedImage attacking;
	private State []states;
	private State []standings;
	private State []jumpes;
	private State []Crawl;
	private State []Crawl2;
	private State []attacks;
	private int stateIdx = 0;
	private int state = 0;
	private int x;
	private int y;
	
	private int stamina = 100; // 스테미너 초기값
	private int maxStamina = 100; // 최대 스테미너
	private int recoveryRate = 7; // 스테미너 회복 속도
	private long lastUpdateTime = System.currentTimeMillis();
    
	private boolean isStanding;
	private boolean isCrawling;
	private boolean isAttacking = false; // 공격 중인지 여부
	private boolean isFlip = false; // 캐릭터가 반전 중인지 여부
	private boolean isJump = false; // 캐릭터가 점프 중인지 여부
	private boolean flip = false;
	
	private LinkedList<Attack> attackList = new LinkedList<>();
	public Player() {
		
		isStanding = true;
		loadImage();
		states = new State[6];
		State state = new State();
		states[0] = state;
		
		state.width = 36;
		state.height = 57;
		state.index_x = 0;
		state.start_x = 0;
		state.start_y = 0;
		state.frame_size = 6;
		state = new State();
		
		states[1] = state;
		state.width = 36;
		state.height = 57;
		state.index_x = 1;
		state.start_x = 121;
		state.start_y = 76;
		state.frame_size = 6;
		//state.stop = true;
		
		state = new State();
		states[2] = state;
		state.width = 36;
		state.height = 57;
		state.index_x = 2;
		state.start_x = 178;
		state.start_y = 76;
		state.frame_size = 6;
		
		state = new State();
		states[3] = state;
		state.width = 36;
		state.height = 57;
		state.index_x = 3;
		state.start_x = 233;
		state.start_y = 76;
		state.frame_size = 6;
		
		state = new State();
		states[4] = state;
		state.width = 36;
		state.height = 57;
		state.index_x = 4;
		state.start_x = 287;
		state.start_y = 76;
		state.frame_size = 6;
		//state.stop = true;
		
		state = new State();
		states[5] = state;
		state.width = 36;
		state.height = 57;
		state.index_x = 5;
		state.start_x = 337;
		state.start_y = 76;
		state.frame_size = 6;
		state.stop = true;
		
		standings = new State[1];
		State stand = new State();
		standings[0] = stand;
		stand.width = 34;
		stand.height = 64;
		stand.index_x = 0;
		stand.start_x = 84;
		stand.start_y = 76;
		
		jumpes = new State[6];
		State jump = new State();
		jumpes[0] = jump;
		jump.width = 34;
		jump.height = 64;
		jump.index_x = 0;
		jump.start_x = 0;
		jump.start_y = 0;

		jump = new State(); 
		jumpes[1] = jump;
		jump.width = 34;
		jump.height = 64;
		jump.index_x = 1;
		jump.start_x = 67;
		jump.start_y = 63;
		
		jump = new State();
		jumpes[2] = jump;
		jump.width=34;
		jump.height=64;
		jump.index_x=2;
		jump.start_x=164;
		jump.start_y=79;
		
		jump = new State(); 
		jumpes[3] = jump;
		jump.width=34;
		jump.height=64;
		jump.index_x=3;
		jump.start_x=216;
		jump.start_y=79;
		
		jump = new State(); 
		jumpes[4] = jump;
		jump.width=34;
		jump.height=64;
		jump.index_x=4;
		jump.start_x=216;
		jump.start_y=79;
		
		jump = new State(); 
		jumpes[5] = jump;
		jump.width=34;
		jump.height=64;
		jump.index_x=5;
		jump.start_x=216;
		jump.start_y=79;
		
		attacks = new State[1];
		State attack = new State();
		attacks[0] = attack;
		attack.width = 58;
		attack.height = 74;
		attack.index_x = 0;
		attack.start_x = 0;
		attack.start_y = 0;
		
		this.gameScreen = gameScreen;
		this.attack = new Attack(0, 0, 1, 2);
		this.hp = new PlayerHp();
		this.x = 30;
		this.y = 250;
		
	}
	public LinkedList<Attack> getAttackList() {
		return this.attackList;
	}
	private void loadImage() {
		try {
			this.sprite = ImageIO.read(new File("player/Step.png"));
			this.standing = ImageIO.read(new File("player/stending.png"));
			this.jumping = ImageIO.read(new File("player/JUMP.png"));
			this.attacking = ImageIO.read(new File("player/attack.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private State getState() {
		return states[stateIdx];
	}

	//private boolean flip = false;
	private void drawCharacter(State state, Graphics g, GameCanvas gameCanvas) {
		hp.draw(g);
	    BufferedImage bufferedImage = new BufferedImage(state.width, state.height, BufferedImage.TYPE_INT_ARGB);
	    Graphics gb = bufferedImage.getGraphics();
	    if (isJump) {
	    	gb.drawImage(jumping, 
					0, 0,  //위치 
					0 + state.width, 0 + state.height, //크기 
					state.width*state.index_x + state.start_x, 
					state.height*state.index_y + state.start_y, 
					state.width*state.index_x + state.width + state.start_x, 
					state.height*state.index_y + state.start_y + state.height, 
					gameCanvas);
	    }else if (isAttacking) {
	    	gb.drawImage(attacking,0, 0,  // 위치
	                0 + state.width, 0 + state.height, // 크기
	                state.start_x,
	                state.start_y,
	                state.width + state.start_x,
	                state.start_y + state.height,
	                gameCanvas);
	    }
	    else if(isStanding){
	    	gb.drawImage(standing,0, 0,  // 위치
	                0 + state.width, 0 + state.height, // 크기
	                state.start_x,
	                state.start_y,
	                state.width + state.start_x,
	                state.start_y + state.height,
	                gameCanvas);
	    }
	    else{
	    	gb.drawImage(sprite, 
					0, 0,  //위치 
					0 + state.width, 0 + state.height, //크기 
					state.width*state.index_x + state.start_x, 
					state.height*state.index_y + state.start_y, 
					state.width*state.index_x + state.width + state.start_x, 
					state.height*state.index_y + state.start_y + state.height, 
					gameCanvas);
	    }	      
	    gb.dispose();	    
	    if(this.flip) {
		    AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		    tx.translate(-state.width, 0);
		    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		    bufferedImage = op.filter(bufferedImage, null);
	    }
	    g.drawImage(bufferedImage, x, y,40, 65, null);
	    
	    
		if(gameCanvas.getCount() % 50 == 0)
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
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public void setY(int newY) {
        y = newY;
    }
    public void setX(int newX) {
        x = newX;
    }
    public boolean isFlip() {
        return flip;
    }
    public void setIsJump(boolean isJump) {
        this.isJump = isJump;
    }
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		isStanding = false;
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_LEFT:
            this.flip = true; // 왼쪽 키 눌렸을 때 flip을 true로 설정하여 이미지 반전
			x -= 6;
			System.out.println("왼쪽");
			break;
		case KeyEvent.VK_RIGHT:
            this.flip = false; // 오른쪽 키 눌렸을 때 flip을 false로 설정하여 이미지 반전 해제
			x += 6;
			System.out.println("오른쪽");
			break;
		case KeyEvent.VK_SPACE:
            if (!isJump && stamina >= 20) { // 스테미너가 충분하고 점프를 하지 않은 상태일 때
                isJump = true;
                Jump jumpAction = new Jump(this);
                jumpAction.start();
                stamina -= 20; // 점프에 필요한 스테미너 20 소모
            }
            break;
		case KeyEvent.VK_A:
		    if (!isAttacking && attack.canAttack()) {
		        isAttacking = true; // 현재 공격 중
		        if (flip) {
		            // 캐릭터가 반전된 상태이면, 오른쪽으로 공격
		            attackList.add(new Attack(x - 10, y + 12, 2, 3));
		        } else {
		            // 반전되지 않은 상태이면, 왼쪽으로 공격
		            attackList.add(new Attack(x + 55, y + 12, 2, 2));
		        }
		        attack.performAttack(); // 공격 가능한 경우에만 공격 수행
		        //attack.refillAttacks();
		    }
		    break;
        }
	}
	//Stamina//
	public void update() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastUpdateTime;

        // 일정 시간이 경과한 경우에만 스테미너를 회복시킴
        if (elapsedTime > 1000) { // 1초마다
            int recoveredStamina = (int) (elapsedTime / 1000 * recoveryRate);
            stamina += recoveredStamina;
            if (stamina > maxStamina) {
                stamina = maxStamina; // 최대 스테미너를 초과하지 않도록 설정
            }
            lastUpdateTime = currentTime;
        }
    }
	public void updateStamina() {
        // 일정 시간마다 스테미너 회복 (예: 매 프레임마다 1씩 회복)
        if (stamina < 100) {
            stamina++; // 스테미너 회복
        }
    }
	//Attack//
	private void clearAttack(GameCanvas gameCanvas) {
		LinkedList<Attack> removeList = new LinkedList<>();
		for(Attack attack : attackList) {
			if (attack.getX() > gameCanvas.getWidth()) {
	            isAttacking = false;
	            removeList.add(attack);
	        }
		}
		
		for(Attack revAttack : removeList) {
			attackList.remove(revAttack);
		}
	}
	//Player Hp//
	public PlayerHp getPlayerHp() {
		return this.hp;
	}
	public void setHp(PlayerHp hp) {
        this.hp = hp; // 플레이어 체력 객체 설정
    }
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		isStanding = true;
		this.stateIdx = 0;
		switch(e.getKeyCode()) {
        case KeyEvent.VK_A:
            isAttacking = false; // 공격 상태 해제
            break;

		}
		
	}
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	public void refillAttacks() {
        attack.refillAttacks(); // Refill player's attack count
    }
	public Rectangle getRect() {
		return new Rectangle(x, y, this.states[state].width, this.states[state].height);
	}
	public void draw(Graphics g, GameCanvas gameCanvas) {
		update();
		//updateStamina();
		// 스테미너 바 그리기
	    int staminaBarWidth = 100; // 스테미너 바 너비
	    int staminaBarHeight = 10; // 스테미너 바 높이
	    int xPosition = 15; // 스테미너 바 x 좌표
	    int yPosition = 55; // 스테미너 바 y 좌표

	    // 스테미너 바 테두리 그리기
	    g.setColor(Color.BLACK);
	    g.drawRect(xPosition, yPosition, staminaBarWidth, staminaBarHeight);

	    // 현재 스테미너에 따라 바 색상 설정
	    if (stamina >= 70) {
	        g.setColor(Color.GREEN);
	    } else if (stamina >= 30) {
	        g.setColor(Color.YELLOW);
	    } else {
	        g.setColor(Color.RED);
	    }

	    // 스테미너 바 색칠하기
	    int currentStaminaWidth = (int) ((double) stamina / 100 * staminaBarWidth);
	    g.fillRect(xPosition + 1, yPosition + 1, currentStaminaWidth - 1, staminaBarHeight - 1);
		for(Attack attack : attackList) {
			attack.move();
			attack.draw(g, gameCanvas);
		}
		clearAttack(gameCanvas);
		drawCharacter(getState(), g, gameCanvas);
	}

}
