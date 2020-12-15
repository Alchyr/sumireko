package sumireko.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sumireko.abstracts.BasePower;

import static sumireko.SumirekoMod.makeID;

public class RetainSpecificCardPower extends BasePower implements NonStackablePower {
    public static final String NAME = "RetainSpecificCard";
    public static final String POWER_ID = makeID(NAME);
    public static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = true;
    public AbstractCard card;

    public RetainSpecificCardPower(final AbstractCreature owner, AbstractCard c)
    {
        super(NAME, TYPE, TURN_BASED, owner, null, -1);

        this.card = c;
        updateDescription();
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (!c.isEthereal && c.uuid.equals(this.card.uuid)) {
                    c.retain = true;
                }
            }
        }
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (card.uuid.equals(this.card.uuid))
        {
            this.flash();
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    public void updateDescription() {
        if (card == null)
        {
            this.description = "";
        }
        else
        {
            this.description = DESCRIPTIONS[0] + FontHelper.colorString(card.name, "y") + DESCRIPTIONS[1];
        }
    }
}