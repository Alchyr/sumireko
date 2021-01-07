package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class PocketBattery extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "PocketBattery",
            0,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);


    public PocketBattery() {
        super(cardInfo, true);

        selfRetain = true;
        tags.add(CustomCardTags.UNPLAYABLE);
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void triggerWhenDrawn() {
        addToBot(new GainEnergyAction(1));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainEnergyAction(1));

        if (upgraded)
        {
            drawCards(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new PocketBattery();
    }
}