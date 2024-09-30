package model.tiles.units.players;


import org.junit.Assert;
import org.junit.Before;


import org.junit.jupiter.api.Test;

import utils.callbacks.DeathCallback;
import utils.callbacks.MessageCallback;
import utils.generators.Generator;
import utils.generators.RandomGenerator;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {



    private final int initialHealth = 100;
    private final int initialAttack = 10;
    private final int initialDefense = 5;
    private Warrior warrior = new Warrior("Test Warrior", initialHealth, initialAttack, initialDefense, 3);
    private Mage mage = new Mage("TestMage", 100, 10, 20, 3, 5, 5, 5, 5);
    private Rogue Rogue= new Rogue("TestRogue", 100, 15, 15, 3);

    DeathCallback deathCallback;
    Generator generator;
    MessageCallback messageCallback;

    @Before
    public void setUp()  {
        warrior = new Warrior("Test Warrior", initialHealth, initialAttack, initialDefense, 3);
        mage = new Mage("TestMage", 100, 10, 20, 3, 5, 5, 5, 5);
        Rogue = new Rogue("TestRogue", 100, 15, 15, 3);
        deathCallback = () -> System.out.println("player died");
        this.generator = new RandomGenerator();
        this.messageCallback = s -> System.out.println(s);

    }



    @Test
    public void testInitialValues() {
        assertEquals(1, warrior.getLevel());
        assertEquals(0, warrior.getExperience());
        assertEquals(initialHealth, warrior.getHealth().getCurrent());
        assertEquals(initialAttack, warrior.getAttack());
        assertEquals(initialDefense, warrior.getDefense());
    }






    @Test
    void addExperience() {

        int expToAdd = 50 * warrior.getLevel();
        int exp=0;

        warrior.addExperience(expToAdd);
        assertEquals(2,warrior.getLevel());
        assertEquals(exp, warrior.getExperience());

    }

    @Test
    void levelUp() {
        warrior.levelUp();
        mage.levelUp();
        Rogue.levelUp();
        assertEquals(2, warrior.getLevel());
        assertEquals(initialHealth + Player.HEALTH_GAIN * warrior.getLevel() + 5* warrior.getLevel(), warrior.getHealth().getCapacity());
        assertEquals(initialAttack + Player.ATTACK_GAIN * warrior.getLevel() + 2* warrior.getLevel(), warrior.getAttack());
        assertEquals(initialDefense + Player.DEFENSE_GAIN * warrior.getLevel()+ warrior.getLevel(), warrior.getDefense());
        assertEquals(2, mage.getLevel());
        assertEquals(initialHealth + Player.HEALTH_GAIN * mage.getLevel() , mage.getHealth().getCapacity());
        assertEquals(initialAttack + Player.ATTACK_GAIN * mage.getLevel()  , mage.getAttack());
        assertEquals(20 + Player.DEFENSE_GAIN * mage.getLevel(), mage.getDefense());
        assertEquals(3+25* mage.getLevel(),mage.getManaPool());
        assertEquals((3+25* mage.getLevel())/4,mage.getCurrentMana());

        assertEquals(2, Rogue.getLevel());
        assertEquals(initialHealth + Player.HEALTH_GAIN * Rogue.getLevel() , Rogue.getHealth().getCapacity());
        assertEquals(15 + Player.ATTACK_GAIN * Rogue.getLevel() + 3* Rogue.getLevel(), Rogue.getAttack());
        assertEquals(15 + Player.DEFENSE_GAIN * Rogue.getLevel(), Rogue.getDefense());




    }
    @Test
    public void testDamageAndDeathWarrior() {
        warrior.takeDamage(10);
        assertEquals(initialHealth-10,warrior.getHealth().getCurrent());
        while (warrior.getHealth().getCurrent() > 0) {
            warrior.takeDamage(100);
        }
        Assert.assertTrue(!warrior.alive());

    }
    @Test
    public void testDamageAndDeathMage() {
        mage.takeDamage(10);
        assertEquals(initialHealth-10,mage.getHealth().getCurrent());
        while (mage.getHealth().getCurrent() > 0) {
            mage.takeDamage(100);
        }
        Assert.assertTrue(!mage.alive());

    }
    @Test
    public void testDamageAndDeathRogue() {
        Rogue.takeDamage(10);
        assertEquals(initialHealth-10,Rogue.getHealth().getCurrent());
        while (Rogue.getHealth().getCurrent() > 0) {
            Rogue.takeDamage(100);
        }
        Assert.assertTrue(!Rogue.alive());

    }

}