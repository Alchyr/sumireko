package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.abstracts.LockingCard;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class BeyondReality extends LockingCard {
    private final static CardInfo cardInfo = new CardInfo(
            "BeyondReality",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    public BeyondReality() {
        super(cardInfo, true);

        setMagic(1, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);

        this.addToBot(new BetterDrawPileToHandAction(upgraded ? this.magicNumber : 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new BeyondReality();
    }
}