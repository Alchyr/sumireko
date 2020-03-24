package sumireko.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
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
    }

    public void updateDescription() {
        this.description = descriptions()[0] + amount + descriptions()[1];
    }

    @Override
    public void modifySeal(SealCard c) {
        c.sealValue += this.amount;
        if (c.sealValue != c.baseSealValue)
            c.isSealModified = true;
    }
}