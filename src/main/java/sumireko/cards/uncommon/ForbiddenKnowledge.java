package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.actions.cards.ForbiddenKnowledgeAction;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class ForbiddenKnowledge extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "ForbiddenKnowledge",
            0,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int MAGIC = 5;
    private static final int UPG_MAGIC = 3;


    public ForbiddenKnowledge() {
        super(cardInfo, false);

        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public float getTitleFontSize() {
        return 18.0f;
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else {
            if (p.drawPile.size() >= this.magicNumber)
            {
                canUse = false;
                this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            }

            return canUse;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ForbiddenKnowledgeAction());
    }

    @Override
    public AbstractCard makeCopy() {
        return new ForbiddenKnowledge();
    }
}