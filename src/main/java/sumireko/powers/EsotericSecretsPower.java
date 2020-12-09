package sumireko.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
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

    private boolean useSavedValue = false;
    private int savedReduction = 0;

    public EsotericSecretsPower(final AbstractCreature owner, int amount)
    {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        return damage - getReduction();
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
        {
            m.applyPowers();
        }
    }

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        if (isPlayer)
        {
            useSavedValue = true;
        }
    }

    @Override
    public void atStartOfTurn() {
        useSavedValue = false;
    }

    private int getReduction()
    {
        if (useSavedValue)
            return savedReduction;

        savedReduction = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group)
        {
            if ((c instanceof LockingCardInterface && ((LockingCardInterface) c).isLocked()) ||
                    c.rawDescription.toLowerCase().contains(GameDictionary.UNPLAYABLE.NAMES[0]) ||
            c.hasTag(CustomCardTags.UNPLAYABLE))
            {
                savedReduction += this.amount;
            }
        }
        updateDescription();
        return savedReduction;
    }

    public void updateDescription() {
        this.description = descriptions()[0] + amount + descriptions()[1] + savedReduction + descriptions()[2];
    }
}