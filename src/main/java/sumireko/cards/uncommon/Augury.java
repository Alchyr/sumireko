package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.actions.general.XCostAction;
import sumireko.powers.TribulationPower;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Augury extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Augury",
            -1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int BLOCK = 20;
    private static final int UPG_BLOCK = 5;


    public Augury() {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new XCostAction(this, (amt, params)->{
            for (int i = 0; i < amt; i++) {
                addToTop(getBlockAction(params[0]));
            }
            return true;
        }, this.block));

        applySelf(new TribulationPower(p, 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Augury();
    }
}