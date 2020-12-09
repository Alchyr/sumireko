package sumireko.cards.basic;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.LockingCard;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class PsychokineticBurst extends LockingCard {
    private final static CardInfo cardInfo = new CardInfo(
            "PsychokineticBurst",
            0,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.BASIC);
    // skill

    public static final String ID = makeID(cardInfo.cardName);

    private static final int MAGIC = 2;

    public PsychokineticBurst() {
        super(cardInfo, true);

        setMagic(MAGIC);
    }

    @Override
    public float getTitleFontSize() {
        return 18.0f;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);

        gainEnergy(this.upgraded ? 2 : 1);
        drawCards(this.magicNumber);
    }

    @Override
    public AbstractCard makeCopy() {
        return new PsychokineticBurst();
    }
}