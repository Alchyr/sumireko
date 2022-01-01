package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.interfaces.SleevedCard;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class BottomlessSack extends BaseCard implements SleevedCard {
    private final static CardInfo cardInfo = new CardInfo(
            "BottomlessSack",
            0,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);


    public BottomlessSack() {
        super(cardInfo, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded)
            drawCards(1);
    }

    @Override
    public void sleevedTurnStart() {
        AbstractCard card = AbstractDungeon.getCard(CardRarity.COMMON, AbstractDungeon.cardRandomRng).makeCopy();
        /*if (card.cost > 0) {
            card.cost = 0;
            card.costForTurn = 0;
            card.isCostModified = true;
        }*/
        addToTop(new MakeTempCardInHandAction(card));
    }

    @Override
    public AbstractCard makeCopy() {
        return new BottomlessSack();
    }
}