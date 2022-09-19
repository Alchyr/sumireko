package sumireko.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.actions.general.MultiGroupMoveAction;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class MaterialismTest extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "MaterialismTest",
            1,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY,
            CardRarity.COMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int DAMAGE = 9;
    private static final int UPG_DAMAGE = 3;


    public MaterialismTest() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        damageAll(AbstractGameAction.AttackEffect.SMASH);

        addToBot(new MultiGroupMoveAction(CardGroup.CardGroupType.DRAW_PILE, 5, true, CardGroup.CardGroupType.HAND, CardGroup.CardGroupType.DISCARD_PILE));
    }

    @Override
    public AbstractCard makeCopy() {
        return new MaterialismTest();
    }
}