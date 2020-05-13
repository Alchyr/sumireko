package sumireko.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sumireko.abstracts.BasePower;

import static sumireko.SumirekoMod.makeID;

public class ImprintPower extends BasePower {
    public static final String NAME = "Imprint";
    public static final String POWER_ID = makeID(NAME);
    public static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public ImprintPower(final AbstractCreature owner)
    {
        super(NAME, TYPE, TURN_BASED, owner, null, 1);
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount > 5)
            this.amount = 5;
    }

    public void updateDescription() {
        if (this.amount == 1)
        {
            this.description = descriptions()[0];
        }
        else if (this.amount >= 5)
        {
            this.description = descriptions()[3];
        }
        else
        {
            this.description = descriptions()[1] + amount + descriptions()[2];
        }
    }
}