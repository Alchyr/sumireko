/*package sumireko.cards.rare;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.actions.cards.MentalImagingAction;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Doppelganger extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Doppelganger",
            0,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.RARE);
    // skill

    public static final String ID = makeID(cardInfo.cardName);



    public Doppelganger() {
        super(cardInfo, true);

        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MentalImagingAction(this.upgraded));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Doppelganger();
    }
}*/