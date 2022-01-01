package sumireko.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import sumireko.abstracts.BasePower;

import static sumireko.SumirekoMod.makeID;

public class MadnessPower extends BasePower {
    public static final String NAME = "Madness";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = false;

    public MadnessPower(final AbstractCreature owner, int amount)
    {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type != AbstractCard.CardType.POWER)
        {
            action.exhaustCard = true;
        }
        this.flash();

        addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
    }

    public void updateDescription() {
        if (this.amount == 1)
        {
            this.description = DESCRIPTIONS[0];
        }
        else
        {
            this.description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }
}