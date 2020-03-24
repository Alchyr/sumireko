package sumireko.variables;

import basemod.abstracts.DynamicVariable;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sumireko.abstracts.SealCard;

public class SealVariable extends DynamicVariable {
    private static final Color INCREASED_COLOR = new Color(0.0f, 200.0f / 255.0f, 1.0f, 1.0f);

    @Override
    public String key()
    {
        return "SEAL"; //!SEAL!
    }

    @Override
    public boolean isModified(AbstractCard card)
    {
        if (AbstractDungeon.player != null)
        {
            if (card instanceof SealCard)
            {
                return ((SealCard) card).isSealModified;
            }
        }
        return false;
    }

    @Override
    public int value(AbstractCard card)
    {
        return getSealValue(card);
    }

    public static int getSealValue(AbstractCard card)
    {
        if (card instanceof SealCard)
        {
            if (((SealCard) card).isSealModified)
                return ((SealCard) card).sealValue;
            return ((SealCard) card).baseSealValue;
        }
        return -1;
    }

    @Override
    public int baseValue(AbstractCard card)
    {
        if (card instanceof SealCard)
        {
            return ((SealCard) card).baseSealValue;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card)
    {
        if (card instanceof SealCard)
            return ((SealCard) card).upgradedSeal;

        return false;
    }

    @Override
    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof SealCard)
        {
            ((SealCard) card).isSealModified = v;
        }
    }

    /*@Override
    public Color getIncreasedValueColor() {
        return INCREASED_COLOR;
    }*/
}