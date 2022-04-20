/*package sumireko.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import sumireko.cards.rare.MysterySeal;

public class DamageAllVariable extends DynamicVariable {
    @Override
    public String key()
    {
        return "SUMIREKO:D_ALL"; //!D_ALL!
    }

    @Override
    public boolean isModified(AbstractCard card)
    {
        if (card instanceof MysterySeal)
        {
            return ((MysterySeal) card).isAllDamageModified;
        }
        return false;
    }

    @Override
    public int value(AbstractCard card)
    {
        if (card instanceof MysterySeal)
        {
            return ((MysterySeal) card).isAllDamageModified ? ((MysterySeal) card).allDamage : ((MysterySeal) card).baseAllDamage;
        }
        return card.isDamageModified ? card.damage : card.baseDamage;
    }

    @Override
    public int baseValue(AbstractCard card)
    {
        if (card instanceof MysterySeal)
        {
            return ((MysterySeal) card).baseAllDamage;
        }
        return card.baseDamage;
    }

    @Override
    public boolean upgraded(AbstractCard card)
    {
        if (card instanceof MysterySeal)
            return ((MysterySeal) card).upgradedAllDamage;

        return false;
    }

    @Override
    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof MysterySeal)
            ((MysterySeal) card).isAllDamageModified = v;
    }
}*/