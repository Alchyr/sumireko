package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.abstracts.LockingCard;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class InstantBarrier extends LockingCard {
    private final static CardInfo cardInfo = new CardInfo(
            "InstantBarrier",
            0,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.UNCOMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    private static final int BLOCK = 12;

    public InstantBarrier() {
        super(cardInfo, true);

        setBlock(BLOCK);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);

        block();
    }

    @Override
    public AbstractCard makeCopy() {
        return new InstantBarrier();
    }
}