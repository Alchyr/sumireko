package sumireko.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Crush extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Crush",
            2,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY,
            CardRarity.COMMON);
    // attack

    public static final String ID = makeID(cardInfo.cardName);


    private static final int DAMAGE = 7;

    public Crush() {
        super(cardInfo, false);

        setDamage(DAMAGE);

        tags.add(CustomCardTags.FINAL);
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        damageAll(AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        damageAll(AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Crush();
    }
}