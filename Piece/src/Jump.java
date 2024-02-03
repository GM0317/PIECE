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
        int originalY = player.getY(); // Store initial y-coordinate
        int originalX = player.getX(); // Store initial x-coordinate

        // Jumping upwards part until reaching the jump height
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

        // Apply gravity for the downward movement
        while (player.getY() < originalY) {
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

        // Set isJump to false once the jump is finished
        player.setIsJump(false);
    }
}
