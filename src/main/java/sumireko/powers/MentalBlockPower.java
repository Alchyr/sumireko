package sumireko.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import sumireko.abstracts.BasePower;

import static sumireko.SumirekoMod.makeID;

public class MentalBlockPower extends BasePower {
    public static final String NAME = "MentalBlock";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = true;

    private boolean nextTurn = true;

    public MentalBlockPower(final AbstractCreature owner)
    {
        super(NAME, TYPE, TURN_BASED, owner, null, -1);
    }
    @Override
    public void stackPower(int stackAmount) {

    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {

    }

    @Override
    public void atStartOfTurn() {
        if (nextTurn) {
            nextTurn = false;
            updateDescription();
        }
        else {
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
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
        if (nextTurn) {
            this.description = DESCRIPTIONS[0];
        }
        else {
            this.description = DESCRIPTIONS[1];
        }
    }
}