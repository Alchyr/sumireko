package sumireko.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sumireko.abstracts.BasePower;

import static sumireko.SumirekoMod.makeID;

public class RecallPower extends BasePower implements NonStackablePower {
    public static final String NAME = "Recall";
    public static final String POWER_ID = makeID(NAME);
    public static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = true;
    public AbstractCard card;

    public RecallPower(final AbstractCreature owner, AbstractCard c)
    {
        super(NAME, TYPE, TURN_BASED, owner, null, -1);

        this.card = c;
        updateDescription();
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (card.uuid.equals(this.card.uuid))
        {
            this.flash();
            this.addToBot(new GainEnergyAction(1));
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    public void updateDescription() {
        if (card != null)
        {
            this.description = descriptions()[0] + FontHelper.colorString(card.name, "y") + descriptions()[1];
        }
        else
        {
            this.description = "";
        }
    }
}