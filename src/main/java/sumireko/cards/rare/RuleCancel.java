package sumireko.cards.rare;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.actions.cards.MakeCardInHandOccultAction;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class RuleCancel extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "RuleCancel",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.RARE);
    // skill

    public static final String ID = makeID(cardInfo.cardName);

    private static final int UPG_COST = 0;


    public RuleCancel() {
        super(cardInfo, false);

        tags.add(CustomCardTags.FINAL);
        setExhaust(true);
        //setCostUpgrade(UPG_COST);
    }

    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeCardInHandOccultAction(false));
    }

    @Override
    public AbstractCard makeCopy() {
        return new RuleCancel();
    }
}