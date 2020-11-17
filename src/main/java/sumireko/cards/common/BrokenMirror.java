package sumireko.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class BrokenMirror extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "BrokenMirror",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.COMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int DAMAGE = 9;
    private static final int UPG_DAMAGE = 3;


    public BrokenMirror() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        damageSingle(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        if (p.drawPile.size() > 1)
        {
            addToBot(new MakeTempCardInHandAction(p.drawPile.getBottomCard()));
        }
        else if (p.drawPile.size() > 0)
        {
            addToBot(new MakeTempCardInHandAction(p.drawPile.getRandomCard(AbstractDungeon.cardRandomRng)));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BrokenMirror();
    }
}