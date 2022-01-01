package sumireko.cards.rare;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.powers.ComprehensionPower;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Comprehension extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Comprehension",
            2,
            CardType.POWER,
            CardTarget.SELF,
            CardRarity.RARE);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int UPG_COST = 1;
    private static final int MAGIC = 3;


    public Comprehension() {
        super(cardInfo, false);

        setCostUpgrade(UPG_COST);
        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applySelf(new ComprehensionPower(p, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Comprehension();
    }
}