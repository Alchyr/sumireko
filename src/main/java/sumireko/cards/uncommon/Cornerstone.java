package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.powers.CornerstonePower;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Cornerstone extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Cornerstone",
            1,
            CardType.POWER,
            CardTarget.SELF,
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int MAGIC = 7;
    private static final int UPG_MAGIC = 4;


    public Cornerstone() {
        super(cardInfo, false);

        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applySelf(new CornerstonePower(p, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Cornerstone();
    }
}