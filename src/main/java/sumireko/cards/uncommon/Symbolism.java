package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.SealSystem;
import sumireko.abstracts.BaseCard;
import sumireko.abstracts.LockingCard;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Symbolism extends LockingCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Symbolism",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.UNCOMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);

    private static final int UPG_COST = 0;


    public Symbolism() {
        super(cardInfo, false);

        setCostUpgrade(UPG_COST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);

        gainEnergy(Math.min(5, SealSystem.nextIndex)); //nextIndex *shouldn't* be able to go above 5, but just in case.
    }

    @Override
    public AbstractCard makeCopy() {
        return new Symbolism();
    }
}