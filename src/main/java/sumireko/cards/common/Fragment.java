package sumireko.cards.common;

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
            0,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.COMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int DAMAGE = 12;
    private static final int UPG_DAMAGE = 4;


    public Fragment() {
        super(cardInfo, true);

        setDamage(DAMAGE, UPG_DAMAGE);
        this.selfRetain = true;

        this.tags.add(CustomCardTags.UNPLAYABLE);
        this.cardsToPreview = new Fragment(false);
    }

    private Fragment(boolean h)
    {
        super(cardInfo, true);

        setDamage(DAMAGE, UPG_DAMAGE);
        this.selfRetain = true;

        this.tags.add(CustomCardTags.UNPLAYABLE);
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        if (this.cardsToPreview != null)
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