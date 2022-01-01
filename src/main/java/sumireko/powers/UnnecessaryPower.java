package sumireko.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sumireko.abstracts.BasePower;

import java.util.ArrayList;

import static sumireko.SumirekoMod.makeID;

public class UnnecessaryPower extends BasePower implements NonStackablePower {
    public static final String NAME = "Unnecessary";
    public static final String POWER_ID = makeID(NAME);
    public static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = true;
    public ArrayList<AbstractCard> cards;

    public UnnecessaryPower(final AbstractCreature owner, ArrayList<AbstractCard> c)
    {
        super(NAME, TYPE, TURN_BASED, owner, null, -1);

        this.cards = c;
        updateDescription();
    }

    @Override
    public boolean canPlayCard(AbstractCard card) {
        return !cards.contains(card);
    }

    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    public void updateDescription() {
        if (cards == null)
        {
            this.description = "";
        }
        else if (cards.isEmpty())
        {
            this.description = DESCRIPTIONS[0];
        }
        else
        {
            getLocalizedDescription();
        }
    }

    private void getLocalizedDescription() {
        switch (Settings.language) {
            default:
                if (cards.size() == 1)
                {
                    this.description = DESCRIPTIONS[1] + getCardText(0) + DESCRIPTIONS[5];
                }
                else if (cards.size() == 2)
                {
                    this.description = DESCRIPTIONS[1] + getCardText(0) + DESCRIPTIONS[4] + getCardText(1) + DESCRIPTIONS[5];
                }
                else
                {
                    StringBuilder sb = new StringBuilder(DESCRIPTIONS[1] + getCardText(0));
                    for (int i = 1; i < cards.size() - 1; ++i)
                        sb.append(DESCRIPTIONS[2]).append(getCardText(i));

                    sb.append(DESCRIPTIONS[3]).append(getCardText(cards.size() - 1)).append(DESCRIPTIONS[5]);

                    this.description = sb.toString();
                }
                break;
        }
    }

    private String getCardText(int index) {
        return FontHelper.colorString(cards.get(index).name, "y");
    }
}