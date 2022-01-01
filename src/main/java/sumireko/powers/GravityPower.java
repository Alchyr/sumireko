package sumireko.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.BurningBlood;
import sumireko.abstracts.BasePower;
import sumireko.enums.CustomCardTags;
import sumireko.interfaces.LockingCardInterface;
import sumireko.patches.occult.OccultPatch;
import sumireko.util.KeywordWithProper;

import java.util.ArrayList;

import static sumireko.SumirekoMod.makeID;

public class GravityPower extends BasePower implements CloneablePowerInterface {
    public static final String NAME = "Gravity";
    public static final String POWER_ID = makeID(NAME);
    public static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public GravityPower(final AbstractCreature owner, int amount)
    {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    @Override
    public void onCardDraw(AbstractCard c) {
        if ((c instanceof LockingCardInterface && ((LockingCardInterface) c).isLocked()) ||
                OccultPatch.isUnplayable(AbstractDungeon.player, c))
        {
            addToTop(new DrawCardAction(this.amount));
        }
    }

    /*public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            for (AbstractCard c : AbstractDungeon.player.hand.group)
            {
            }
        }
    }*/

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
        return new GravityPower(owner, amount);
    }
}