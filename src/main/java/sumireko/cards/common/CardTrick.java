package sumireko.cards.common;

import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.actions.common.PutOnDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.actions.cards.CardTrickAction;
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


    public CardTrick() {
        super(cardInfo, true);

        //setBlock(BLOCK, UPG_BLOCK);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //if (upgraded)
        //    drawCards(1);
        //this.addToBot(new PutOnDeckAction(p, p, 1, false));

        if (upgraded) {
            this.addToBot(new CardTrickAction());
        }
        else {
            this.addToBot(new BetterDiscardPileToHandAction(1));
        }
        //this.addToBot(new CardTrickAction(this.block));
    }

    @Override
    public AbstractCard makeCopy() {
        return new CardTrick();
    }
}