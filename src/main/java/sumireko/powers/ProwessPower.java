package sumireko.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import sumireko.abstracts.BasePower;
import sumireko.abstracts.SealCard;
import sumireko.interfaces.ModifySealPower;

import static sumireko.SumirekoMod.makeID;

public class ProwessPower extends BasePower implements ModifySealPower {
    public static final String NAME = "Prowess";
    public static final String POWER_ID = makeID(NAME);
    public static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public ProwessPower(final AbstractCreature owner, int amount)
    {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);

        canGoNegative = true;
        priority = -15;
    }

    public void updateDescription() {
        if (this.amount > 0) {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
            this.type = PowerType.BUFF;
        } else {
            int tmp = -this.amount;
            this.description = DESCRIPTIONS[1] + tmp + DESCRIPTIONS[2];
            this.type = PowerType.DEBUFF;
        }
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount == 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    public void reducePower(int reduceAmount) {
        this.fontScale = 8.0F;
        this.amount -= reduceAmount;
        if (this.amount == 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    public void doThing(float ___eliteSpawnChance) {
        ___eliteSpawnChance = ___eliteSpawnChance * 10;
    }

    @Override
    public void modifySeal(SealCard c) {
        c.sealValue += this.amount;
        if (c.sealValue < 0)
            c.sealValue = 0;
        if (c.sealValue != c.baseSealValue)
            c.isSealModified = true;
    }
}