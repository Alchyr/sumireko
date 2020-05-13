package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.powers.ImprintPower;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Imprint extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Imprint",
            1,
            CardType.POWER,
            CardTarget.SELF,
            CardRarity.UNCOMMON);
    // power

    public static final String ID = makeID(cardInfo.cardName);

    private static final int UPG_COST = 0;


    public Imprint() {
        super(cardInfo, false);

        setCostUpgrade(UPG_COST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applySelf(new ImprintPower(p));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Imprint();
    }
}