package sumireko.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sumireko.abstracts.BasePower;

import static sumireko.SumirekoMod.makeID;

public class TruePentagramPower extends BasePower {
    public static final String NAME = "TruePentagram";
    public static final String POWER_ID = makeID(NAME);
    public static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public TruePentagramPower(final AbstractCreature owner)
    {
        super(NAME, TYPE, TURN_BASED, owner, null, -1);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}