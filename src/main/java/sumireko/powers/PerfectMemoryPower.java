package sumireko.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sumireko.abstracts.BasePower;

import static sumireko.SumirekoMod.makeID;

public class PerfectMemoryPower extends BasePower {
    public static final String NAME = "PerfectMemory";
    public static final String POWER_ID = makeID(NAME);
    public static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = true;

    private boolean justApplied = true; //so it doesn't trigger on the card that applies it.

    public PerfectMemoryPower(final AbstractCreature owner)
    {
        super(NAME, TYPE, TURN_BASED, owner, null, 1);
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (this.justApplied) {
            this.justApplied = false;
        } else {
            if (card.type != AbstractCard.CardType.POWER) {
                this.flash();
                action.reboundCard = true;
            }

            this.addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new RecallPower(this.owner, card)));
        }
    }

    public void updateDescription() {
        if (this.amount > 1) {
            this.description = descriptions()[1] + this.amount + descriptions()[2];
        } else {
            this.description = descriptions()[0];
        }
    }
}