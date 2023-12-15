import objectdraw.Drawable2DInterface;

public interface Shootable {
    public void kill();
    public boolean checkAlive();
    public Drawable2DInterface getBody();
}
