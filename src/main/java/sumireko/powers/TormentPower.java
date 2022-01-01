package sumireko.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sumireko.abstracts.BasePower;
import sumireko.actions.seals.LoseStrengthThisTurnAction;

import static sumireko.SumirekoMod.makeID;

public class TormentPower extends BasePower {
    public static final String NAME = "Torment";
    public static final String POWER_ID = makeID(NAME);
    public static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = true;

    public TormentPower(final AbstractCreature owner, final AbstractCreature source, int amount)
    {
        super(NAME, TYPE, TURN_BASED, owner, source, amount);
    }

    public void atStartOfTurn() {
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        this.flash();
        this.addToBot(new LoseStrengthThisTurnAction(this.owner, this.source, this.amount));
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}