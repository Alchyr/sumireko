package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.actions.MentalImagingAction;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class MentalImaging extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "MentalImaging",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    public MentalImaging() {
        super(cardInfo, false);

        tags.add(CustomCardTags.FINAL);
        setExhaust(true);
    }

    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MentalImagingAction());
    }

    @Override
    public AbstractCard makeCopy() {
        return new MentalImaging();
    }
}