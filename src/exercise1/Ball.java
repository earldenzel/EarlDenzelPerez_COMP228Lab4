package exercise1;

public class Ball {
    private int xPosition;
    private int yPosition;
    private int xVelocity;
    private int yVelocity;

    public Ball() {
        xVelocity = 4;
        yVelocity = 4;
        xPosition = PongFrame.WIDTH/2;
        yPosition = PongFrame.HEIGHT/2;

    }

    public void move(){
        xPosition += xVelocity;
        yPosition += yVelocity;
    }

    public void reverseX(){
        xVelocity = -xVelocity;
    }

    public void reverseY(){
        yVelocity = -yVelocity
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public int getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }

    public int getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(int yVelocity) {
        this.yVelocity = yVelocity;
    }
}
