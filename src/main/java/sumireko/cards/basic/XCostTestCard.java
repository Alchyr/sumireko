package sumireko.cards.basic;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.abstracts.XCostAction;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class XCostTestCard extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "XCostTestCard",
            -1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.BASIC);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int BLOCK = 7;
    private static final int UPG_BLOCK = 2;


    public XCostTestCard() {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new XCostAction(this, (effect, params)->{
            for(int i = 0; i < effect; ++i) {
                this.addToBot(new GainBlockAction(p, p, params[0]));
            }
            return true;
        }, this.block));
    }

    @Override
    public AbstractCard makeCopy() {
        return new XCostTestCard();
    }
}