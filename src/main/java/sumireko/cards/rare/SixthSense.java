package sumireko.cards.rare;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.powers.SixthSensePower;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class SixthSense extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "SixthSense",
            1,
            CardType.POWER,
            CardTarget.NONE,
            CardRarity.RARE);

    public static final String ID = makeID(cardInfo.cardName);

    private static final int MAGIC = 2;

    public SixthSense() {
        super(cardInfo, true);

        setMagic(MAGIC);
        setInnate(false, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applySelf(new SixthSensePower(p, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new SixthSense();
    }
}