package sumireko.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sumireko.abstracts.BasePower;
import sumireko.enums.CustomCardTags;
import sumireko.interfaces.LockingCardInterface;
import sumireko.patches.occult.OccultFields;
import sumireko.patches.occult.OccultPatch;

import static sumireko.SumirekoMod.makeID;

public class ComprehensionPower extends BasePower implements CloneablePowerInterface {
    public static final String NAME = "Comprehension";
    public static final String POWER_ID = makeID(NAME);
    public static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    public static final boolean TURN_BASED = true;

    public ComprehensionPower(final AbstractCreature owner, int amount)
    {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    @Override
    public void onCardDraw(AbstractCard c) {
        if (this.amount > 0 && !c.hasTag(CustomCardTags.FINAL) && !OccultFields.isOccult.get(c) &&
                (c instanceof LockingCardInterface || OccultPatch.isUnplayable(AbstractDungeon.player, c)))
        {
            --this.amount;
            if (this.amount == 0)
            {
                addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            }
            else {
                updateDescription();
            }
            this.flash();
            addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    OccultFields.isOccult.set(c, true);
                    c.initializeDescription();
                    c.superFlash(Color.VIOLET.cpy());
                    this.isDone = true;
                }
            });
        }
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

    @Override
    public AbstractPower makeCopy() {
        return new ComprehensionPower(owner, amount);
    }
}