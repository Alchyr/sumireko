package sumireko.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
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

    public PerfectMemoryPower(final AbstractCreature owner, int amount)
    {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        this.flash();
        //if (card.type != AbstractCard.CardType.POWER) {
        //    action.reboundCard = true;
        //}

        this.addToBot(new MakeTempCardInDrawPileAction(card.makeStatEquivalentCopy(), this.amount, false, true, false));
        this.addToBot(new ReducePowerAction(this.owner, this.owner, this, this.amount));


        //this.addToBot(new ApplyPowerAction(this.owner, this.owner, new RecallPower(this.owner, card)));
    }

    public void updateDescription() {
        if (this.amount > 1) {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0];
        }
    }
}