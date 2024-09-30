package model.tiles.units.enemies;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EnemyTest {
    private Trap trap = new Trap('B', "Test Trap", 100, 10, 5, 50, 3, 3);
    private Monster monster = new Monster('s', "Test Monster", 100, 10, 5, 50, 3);
    private Boss boss = new Boss('K', "Test Boss", 100, 10, 5, 50, 3, 3);


    @Before
    public void setUp() {
        trap = new Trap('B', "Test Trap", 100, 10, 5, 50, 3, 3);
        monster = new Monster('s', "Test Monster", 100, 10, 5, 50, 3);
        boss = new Boss('K', "Test Boss", 100, 10, 5, 50, 3, 3);
    }
    @Test
    public void takeDamageTrap() {
        int damage = 10;
        int expected = trap.getHealth().getCurrent() - damage;
        trap.takeDamage(damage);
        assertEquals(expected, trap.getHealth().getCurrent());
    }

    @Test
    public void takeDamageMonster() {
        int damage = 10;
        int expected = monster.getHealth().getCurrent() - damage;
        monster.takeDamage(damage);
        assertEquals(expected, monster.getHealth().getCurrent());
    }
    @Test
    public void takeDamageBoss() {
        int damage = 10;
        int expected = boss.getHealth().getCurrent() - damage;
        boss.takeDamage(damage);
        assertEquals(expected, boss.getHealth().getCurrent());
    }
}