package sumireko.cards.rare;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.actions.SelfRestraintAction;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class SelfRestraint extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "SelfRestraint",
            3,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.RARE);

    public static final String ID = makeID(cardInfo.cardName);


    public SelfRestraint() {
        super(cardInfo, true);

        tags.add(CustomCardTags.UNPLAYABLE);
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void upgrade() {
        selfRetain = true;
        super.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelfRestraintAction());
    }

    @Override
    public AbstractCard makeCopy() {
        return new SelfRestraint();
    }
}