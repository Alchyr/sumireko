package sumireko.cards.rare;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.powers.ProwessPower;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class PsychicProwess extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "PsychicProwess",
            2,
            CardType.POWER,
            CardTarget.SELF,
            CardRarity.RARE);
    // power

    public static final String ID = makeID(cardInfo.cardName);


    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;


    public PsychicProwess() {
        super(cardInfo, false);

        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applySelf(new ProwessPower(p, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new PsychicProwess();
    }
}