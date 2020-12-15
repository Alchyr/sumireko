package sumireko.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sumireko.abstracts.BasePower;
import sumireko.patches.occult.OccultFields;

import static sumireko.SumirekoMod.makeID;

public class NaturalJinxPower extends BasePower {
    public static final String NAME = "NaturalJinx";
    public static final String POWER_ID = makeID(NAME);
    public static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = false;

    public NaturalJinxPower(final AbstractCreature owner, int amount)
    {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (OccultFields.isOccult.get(card))
        {
            this.flash();
            this.addToBot(new LoseHPAction(this.owner, AbstractDungeon.player, this.amount, AbstractGameAction.AttackEffect.POISON));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}