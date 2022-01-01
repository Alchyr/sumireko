package sumireko.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sumireko.abstracts.BasePower;

import static sumireko.SumirekoMod.makeID;

public class FatiguePower extends BasePower implements CloneablePowerInterface {
    public static final String NAME = "Fatigue";
    public static final String POWER_ID = makeID(NAME);
    public static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = true;

    public FatiguePower(final AbstractCreature owner, int amount)
    {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    public void onEnergyRecharge() {
        this.flash();
        AbstractDungeon.player.loseEnergy(this.amount);
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
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

    @Override
    public AbstractPower makeCopy() {
        return new FatiguePower(owner, amount);
    }
}