package sumireko.cards.common;

import com.megacrit.cardcrawl.actions.unique.DiscardPileToTopOfDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Dowsing extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Dowsing",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.COMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int BLOCK = 8;
    private static final int UPG_BLOCK = 3;


    public Dowsing() {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        block();
        addToBot(new DiscardPileToTopOfDeckAction(p));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Dowsing();
    }
}