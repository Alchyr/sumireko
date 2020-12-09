package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.powers.GravityPower;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Gravity extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Gravity",
            1,
            CardType.POWER,
            CardTarget.SELF,
            CardRarity.UNCOMMON);
    // power

    public static final String ID = makeID(cardInfo.cardName);


    private static final int MAGIC = 1;


    public Gravity() {
        super(cardInfo, true);

        setMagic(MAGIC);
        setInnate(false, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applySelf(new GravityPower(p, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Gravity();
    }
}