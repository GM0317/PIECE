public class Jump extends Thread {
    private Player player;
    private final int JUMP_HEIGHT = 100; // Jump height
    private final int JUMP_SPEED = 3; // Jump duration (ms)
    private final double GRAVITY = 1; // Gravity acceleration
    private final int HORIZONTAL_MOVE = 1; // Horizontal movement

    public Jump(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        int originalY = player.getY(); // 초기 y좌표
        int originalX = player.getX(); // 초기 x좌표

        // 점프 높이에 도달할 때까지의 위쪽 점프 부분
        for (int i = 0; i < JUMP_HEIGHT; i++) {
            player.setY(player.getY() - 1); // Move the player upwards

            // Move horizontally based on the player's direction (left or right)
            if (player.isFlip()) {
                player.setX(player.getX() - HORIZONTAL_MOVE); // Move left
            } else {
                player.setX(player.getX() + HORIZONTAL_MOVE); // Move right
            }

            try {
                Thread.sleep(JUMP_SPEED); // Adjust jump speed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 아래로 이동하는 움직임에 중력을 적용
        for (int i=0; i<JUMP_HEIGHT; i++) {
            player.setY((int) (player.getY() + GRAVITY)); // Apply gravity to move the player downwards

            // Move horizontally based on the player's direction (left or right)
            if (player.isFlip()) {
                player.setX(player.getX() - HORIZONTAL_MOVE); // Move left
            } else {
                player.setX(player.getX() + HORIZONTAL_MOVE); // Move right
            }

            try {
                Thread.sleep(JUMP_SPEED); // Adjust gravity speed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 점프가 완료되면 isJump를 false로 설정
        player.setIsJump(false);
    }
}
