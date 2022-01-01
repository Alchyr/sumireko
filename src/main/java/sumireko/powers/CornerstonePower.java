package sumireko.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sumireko.SealSystem;
import sumireko.abstracts.BasePower;
import sumireko.abstracts.SealCard;
import sumireko.interfaces.ModifySealPower;

import static sumireko.SumirekoMod.makeID;

public class CornerstonePower extends BasePower implements ModifySealPower, CloneablePowerInterface {
    public static final String NAME = "Cornerstone";
    public static final String POWER_ID = makeID(NAME);
    public static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public CornerstonePower(final AbstractCreature owner, int amount)
    {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);

        canGoNegative = true;
        priority = -14;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void modifySeal(SealCard c) {
        if (c.equals(SealSystem.centerCard))
        {
            c.sealValue += this.amount;
            if (c.sealValue < 0)
                c.sealValue = 0;
            if (c.sealValue != c.baseSealValue)
                c.isSealModified = true;
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new CornerstonePower(owner, amount);
    }
}