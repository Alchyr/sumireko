package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Fragment extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Fragment",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int DAMAGE = 16;


    public Fragment() {
        super(cardInfo, true);

        setDamage(DAMAGE);

        this.tags.add(CustomCardTags.UNPLAYABLE);
        this.cardsToPreview = new Fragment(false);
    }

    private Fragment(boolean h) //no preview constructor to avoid infinite recursion
    {
        super(cardInfo, true);

        setDamage(DAMAGE);

        this.tags.add(CustomCardTags.UNPLAYABLE);
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void upgrade() {
        this.selfRetain = true;
        super.upgrade();
        if (this.cardsToPreview != null && !this.cardsToPreview.upgraded)
            this.cardsToPreview.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        damageSingle(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        Fragment f = new Fragment();
        if (upgraded)
        {
            f.upgrade();
        }
        addToBot(new MakeTempCardInHandAction(f));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Fragment();
    }
}