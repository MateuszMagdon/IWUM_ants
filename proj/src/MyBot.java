import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyBot extends Bot {

    private static final double LIVING_REWARD = -1;
    private static final double EXPLORATION_REWARD = 16;

    private static final double FOOD_EATEN_REWARD = 19;
    private static final double WAS_KILLED_REWARD = -4;
    private static final double ENEMY_KILL_CONTRIBUTION_REWARD = 8;
    private static final double ENEMY_HILL_RAZED = 50;
    private static final double FRIENDLY_HILL_BLOCKED = -20;

    private static final double ALPHA_DIVIDER = 5;
    private static final double DISCOUNT = 0.6;

    private Set<Tile> _visitedTiles;

    public MyBot()
    {
        _visitedTiles = new HashSet<Tile>();
    }

    public static void main(String[] args) throws IOException
    {
        new MyBot().readSystemInput();
    }

    @Override
    public void doTurn()
    {
        avoidCollisions();
    }

    private double getReward(Ant ant)
    {
        double reward = LIVING_REWARD;
        double exploreBonus = 0;

        if (_visitedTiles.contains(antPosition))
        {
            exploreBonus = 1/100; //TODO change to 1/(100*Tile.timesVisited)
        }
        else
        {
            exploreBonus = 1;
        }

        reward *= exploreBonus * EXPLORATION_REWARD;

        reward += ant.foodEaten * FOOD_EATEN_REWARD;
        reward += ant.wasKilled * WAS_KILLED_REWARD;
        reward += ant.enemyKillContribution * ENEMY_KILL_CONTRIBUTION_REWARD;
        reward += ant.enemyHillRazed * ENEMY_HILL_RAZED;
        reward += ant.friendlyHillBloced * FRIENDLY_HILL_BLOCKED;

        return reward;
    }

    private void avoidCollisions()
    {
        List<Tile> nextLocations = new ArrayList<Tile>();

        Ants ants = getAnts();
        for (Tile myAnt : ants.getMyAnts())
        {
            for (Aim direction : Aim.values())
            {
                Tile nextLocation = new Tile(myAnt.getRow() + direction.getRowDelta(), myAnt.getCol() + direction.getColDelta);

                if (ants.getIlk(myAnt, direction).isPassable() && !nextLocations.contains(nextLocation))
                {
                    ants.issueOrder(myAnt, direction);
                    nextLocations.add(nextLocation);
                    break;
                }
            }
        }
    }
}
