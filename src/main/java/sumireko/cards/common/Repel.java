package sumireko.cards.common;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.actions.cards.RepelAction;
import sumireko.effects.DelayedEffect;
import sumireko.effects.PushEffect;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Repel extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Repel",
            0,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.COMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int DAMAGE = 2;
    private static final int UPG_DAMAGE = 1;

    public Repel() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null)
        {
            addToBot(new VFXAction(new DelayedEffect(new PushEffect(p.hb.cX, m.hb.cX, m.hb.cY), 0.25f)));
            addToBot(new VFXAction(new PushEffect(p.hb.cX, m.hb.cX, m.hb.cY), Settings.FAST_MODE ? 0.25f : 0.5f));
        }
        addToBot(new RepelAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        addToBot(new RepelAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Repel();
    }
}