
public class Ant {
    private final int row;

    private final int col;

    public boolean foodEaten;
    public boolean wasKilled;
    public double enemyKillContribution;
    public double enemyHillDistance;
    public boolean enemyHillRazed;
    public boolean friendlyHillBloced;


    public Ant(Tile position, Aim direction) {
        row = tile.getRow();
        col = tile.getCol();
        this.direction = direction.getSymbol();
    }



}
