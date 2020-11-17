package sumireko.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sumireko.abstracts.BasePower;

import static sumireko.SumirekoMod.makeID;

public class ProwessDownPower extends BasePower {
    public static final String NAME = "ProwessDownPower";
    public static final String POWER_ID = makeID(NAME);
    public static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = true;

    public ProwessDownPower(final AbstractCreature owner, int amount)
    {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        this.addToBot(new ApplyPowerAction(this.owner, this.owner, new ProwessPower(this.owner, -this.amount), -this.amount));
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    public void updateDescription() {
        this.description = descriptions()[0] + amount + descriptions()[1];
    }
}