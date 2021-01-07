package sumireko.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sumireko.abstracts.BasePower;

import static sumireko.SumirekoMod.makeID;

public class SuspensePower extends BasePower implements NonStackablePower {
    public static final String NAME = "Suspense";
    public static final String POWER_ID = makeID(NAME);
    public static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = true;
    public AbstractCard card;

    public SuspensePower(final AbstractCreature owner, AbstractCard c, int amount)
    {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);

        this.card = c;
        updateDescription();
    }

    @Override
    public boolean isStackable(AbstractPower power) {
        return (power instanceof SuspensePower && ((SuspensePower) power).card.uuid.equals(this.card.uuid));
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

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.uuid.equals(this.card.uuid))
        {
            this.flash();
            this.addToTop(new GainBlockAction(this.owner, this.owner, this.amount));
            this.addToBot(new ReducePowerAction(this.owner, this.owner, this, this.amount));
        }
    }

    public void updateDescription() {
        if (card == null)
        {
            this.description = "";
        }
        else
        {
            this.description = DESCRIPTIONS[0] + FontHelper.colorString(card.name, "y") + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }
}