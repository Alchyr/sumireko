package sumireko.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sumireko.abstracts.BasePower;

import static sumireko.SumirekoMod.makeID;

public class TribulationPower extends BasePower {
    public static final String NAME = "Tribulation";
    public static final String POWER_ID = makeID(NAME);
    public static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = true;

    public boolean justApplied;

    public TribulationPower(AbstractCreature owner, int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);

        justApplied = true;
        this.updateDescription();
    }

    public void updateDescription() {
        if (justApplied) {
            if (this.amount == 1) {
                this.description = DESCRIPTIONS[4];
            }
            else {
                this.description = DESCRIPTIONS[5] + this.amount + DESCRIPTIONS[2];
            }
        }
        else {
            if (this.amount == 1) {
                this.description = DESCRIPTIONS[0];
            }
            else {
                this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
            }
        }
    }

    @Override
    public boolean canPlayCard(AbstractCard card) {
        if (!justApplied) {
            card.cantUseMessage = DESCRIPTIONS[3];
            return false;
        }
        return true;
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (justApplied) {
            justApplied = false;
        }
        else {
            this.addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
        }
    }
}