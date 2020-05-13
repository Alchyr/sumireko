package sumireko.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sumireko.abstracts.BasePower;
import sumireko.enums.CustomCardTags;
import sumireko.interfaces.LockingCardInterface;
import sumireko.util.KeywordWithProper;

import static sumireko.SumirekoMod.makeID;

public class EsotericSecretsPower extends BasePower {
    public static final String NAME = "EsotericSecrets";
    public static final String POWER_ID = makeID(NAME);
    public static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public EsotericSecretsPower(final AbstractCreature owner, int amount)
    {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        return damage - getReduction();
    }

    private int getReduction()
    {
        int amt = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group)
        {
            if ((c instanceof LockingCardInterface && ((LockingCardInterface) c).isLocked()) ||
                c.rawDescription.contains(KeywordWithProper.unplayable) && c.cost == -2 ||
            c.hasTag(CustomCardTags.UNPLAYABLE))
            {
                amt += this.amount;
            }
        }
        return amt;
    }

    public void updateDescription() {
        this.description = descriptions()[0] + amount + descriptions()[1];
    }
}