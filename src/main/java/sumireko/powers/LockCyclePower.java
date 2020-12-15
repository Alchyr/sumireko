package sumireko.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import sumireko.abstracts.BasePower;

import static sumireko.SumirekoMod.makeID;

public class LockCyclePower extends BasePower {
    public static final String NAME = "LockCycle";
    public static final String POWER_ID = makeID(NAME);
    public static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public LockCyclePower(final AbstractCreature owner, int amount)
    {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    public void updateDescription() {
        if (this.amount == 1)
        {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        }
        else
        {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }


    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            this.flash();
            addToBot(new ApplyPowerAction(this.owner, this.owner, new DrawCardNextTurnPower(this.owner, AbstractDungeon.player.hand.group.size()), AbstractDungeon.player.hand.group.size()));
        }
    }
}