package sumireko.cards.common;

import com.megacrit.cardcrawl.actions.common.PutOnDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.actions.CardTrickAction;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class CardTrick extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "CardTrick",
            0,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.COMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    private static final int BLOCK = 6;
    private static final int UPG_BLOCK = 3;


    public CardTrick() {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new PutOnDeckAction(p, p, 1, false));
        this.addToBot(new CardTrickAction(this.block));
    }

    @Override
    public AbstractCard makeCopy() {
        return new CardTrick();
    }
}