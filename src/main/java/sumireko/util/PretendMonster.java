package sumireko.util;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.mod.stslib.patches.tempHp.PlayerDamage;
import com.megacrit.cardcrawl.blights.Spear;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.AwakenedOne;
import com.megacrit.cardcrawl.monsters.beyond.Darkling;
import com.megacrit.cardcrawl.monsters.exordium.*;
import com.megacrit.cardcrawl.powers.*;
import sumireko.abstracts.SealCard;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;

import static sumireko.SumirekoMod.logger;

public class PretendMonster extends AbstractCreature {
    public static PretendMonster dummySource = new PretendMonster();
    public static ArrayList<AbstractPower> fakePlayerPowers;

    private static Field isMultiDmg, intentMultiAmt;
    private static Method applyBackAttack;

    static {
        try
        {
            isMultiDmg = AbstractMonster.class.getDeclaredField("isMultiDmg");
            isMultiDmg.setAccessible(true);

            intentMultiAmt = AbstractMonster.class.getDeclaredField("intentMultiAmt");
            intentMultiAmt.setAccessible(true);

            applyBackAttack = AbstractMonster.class.getDeclaredMethod("applyBackAttack");
            applyBackAttack.setAccessible(true);
        }
        catch (Exception e)
        {
            logger.error("REEEEEEEEEEEEEEE");
            e.printStackTrace();
        }
    }

    public int baseDamage;
    public int damage;
    public boolean renderMultiple;
    public int numHits;
    public boolean backAttack;
    public boolean split;
    public boolean canDie;

    public AbstractMonster.Intent intent;
    public boolean attacking;

    private PretendMonster()
    {
        super();
        maxHealth = 1;
        currentHealth = 1;
        currentBlock = 1;
    }

    public PretendMonster(AbstractMonster base)
    {
        super(); //the empty constructor at least is *relatively* lightweight
        try
        {
            maxHealth = base.maxHealth;
            currentHealth = base.currentHealth;
            currentBlock = base.currentBlock;

            TempHPField.tempHp.set(this, TempHPField.tempHp.get(base));

            baseDamage = base.getIntentBaseDmg();
            renderMultiple = (boolean) isMultiDmg.get(base);
            numHits = (int) intentMultiAmt.get(base);
            backAttack = (boolean) applyBackAttack.invoke(base);
            intent = base.intent;
            attacking = base.intent.name().contains("ATTACK"); //abstract monster does it for rendering intent damage so I can do it too :)

            canDie = true;

            //Special cases.
            if (AbstractDungeon.getCurrRoom().cannotLose) //pretend monsters are only created when real monsters exist, so the room is definitely not null
            {
                if (base instanceof AwakenedOne || base instanceof Darkling)
                {
                    canDie = false; //on death, render unknown intent instead of nothing
                }
            }
            split = (base instanceof SlimeBoss || //intent changes to ??? if hp falls below half
                    base instanceof SpikeSlime_L || base instanceof AcidSlime_L);

            //any other special cases that can change intent midway, screw off :)
            powers = getCopyList(dummySource, base.powers);
        }
        catch (Exception e)
        {
            logger.error("Failed to initialize PretendMonster.");
            e.printStackTrace();
        }
    }

    public void calculateDamage()
    {
        AbstractPlayer target = AbstractDungeon.player;
        float tmp = (float)baseDamage;

        if (Settings.isEndless && AbstractDungeon.player.hasBlight(Spear.ID)) {
            tmp *= AbstractDungeon.player.getBlight(Spear.ID).effectFloat();
        }

        for (AbstractPower p : powers)
        {
            tmp = p.atDamageGive(tmp, DamageInfo.DamageType.NORMAL);
        }
        for (AbstractPower p : target.powers)
        {
            tmp = p.atDamageReceive(tmp, DamageInfo.DamageType.NORMAL);
        }

        tmp = AbstractDungeon.player.stance.atDamageReceive(tmp, DamageInfo.DamageType.NORMAL);
        if (backAttack) {
            tmp = (float)((int)(tmp * 1.5F));
        }

        for (AbstractPower p : powers)
        {
            tmp = p.atDamageFinalGive(tmp, DamageInfo.DamageType.NORMAL);
        }
        for (AbstractPower p : target.powers)
        {
            tmp = p.atDamageFinalReceive(tmp, DamageInfo.DamageType.NORMAL);
        }

        damage = MathUtils.floor(tmp);
        if (damage < 0) {
            damage = 0;
        }
    }

    @Override
    public void damage(DamageInfo info) {
        ArrayList<AbstractPower> enemyPowers;

        if (info.owner == AbstractDungeon.player)
        {
            enemyPowers = fakePlayerPowers;
            info.owner = dummySource;
        }
        else if (info.owner != null)
        {
            enemyPowers = getCopyList(dummySource, info.owner.powers);
            info.owner = dummySource; //in case powers check the owner of the damage info
        }
        else
            enemyPowers = new ArrayList<>();

        if (info.output > 0 && this.hasPower(IntangiblePlayerPower.POWER_ID)) {
            info.output = 1;
        }

        int damageAmount = info.output;

        if (damageAmount < 0) {
            damageAmount = 0;
        }

        damageAmount = this.decrementBlock(info, damageAmount);
        int[] temp = new int[] { damageAmount };

        PlayerDamage.Insert(this, info, temp, new boolean[] { false });

        damageAmount = temp[0];

        //on attack hooks are too risky.
        for (AbstractPower p : enemyPowers)
        {
            if (p.ID.equals(IntangiblePower.POWER_ID) && damageAmount > 1)
                damageAmount = 1;
        }

        if (hasPower(ShiftingPower.POWER_ID))
        {
            SealCard.pretendApplyPower(this, new StrengthPower(this, -damageAmount), -damageAmount);
            SealCard.pretendApplyPower(this, new GainStrengthPower(this, damageAmount), damageAmount);
        }

        lastDamageTaken = Math.min(damageAmount, currentHealth);

        if (damageAmount > 0) {
            this.currentHealth -= damageAmount;
            if (this.currentHealth < 0)
                this.currentHealth = 0;
        }

        if (this.currentHealth <= 0) {
            this.currentBlock = 0;
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
    }


    private final static HashSet<Class<? extends AbstractPower>> noSourcePowers = new HashSet<>();
    private static ArrayList<AbstractPower> getCopyList(AbstractCreature fakeOwner, ArrayList<AbstractPower> powers)
    {
        ArrayList<AbstractPower> copyList = new ArrayList<>();

        for (AbstractPower p : powers)
        {
            if (p instanceof CloneablePowerInterface)
            {
                AbstractPower copy = ((CloneablePowerInterface) p).makeCopy();
                copy.owner = fakeOwner;

                if (!noSourcePowers.contains(copy.getClass()))
                {
                    try
                    {
                        Field f = copy.getClass().getDeclaredField("source");
                        f.setAccessible(true);
                        f.set(copy, dummySource);
                    }
                    catch (Exception e)
                    {
                        noSourcePowers.add(copy.getClass());
                        //haha this is gonna cause so many exceptions but otherwise this could fuck with so much stuff
                        //not everyone will even use "source" for powers that need one, but might as well try.
                    }
                }
                copyList.add(copy);
            }
            else
            {
                copyList.add(p);
                logger.info("UNCLONEABLE POWER: " + p.getClass().getName());
            }
        }

        return copyList;
    }

    protected int decrementBlock(DamageInfo info, int damageAmount)
    {
        if (info.type != DamageInfo.DamageType.HP_LOSS && this.currentBlock > 0) {
            if (damageAmount >= this.currentBlock) {
                damageAmount -= this.currentBlock;
                this.currentBlock = 0;
            }
            else {
                this.currentBlock -= damageAmount;
                damageAmount = 0;
            }
        }

        return damageAmount;
    }

    public static void prepareFakePlayerPowers()
    {
        fakePlayerPowers = getCopyList(dummySource, AbstractDungeon.player.powers);
    }
}
