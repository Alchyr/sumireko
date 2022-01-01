package sumireko.cards.uncommon;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import sumireko.abstracts.BaseCard;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.SUMIREKO_COLOR_BRIGHT;
import static sumireko.SumirekoMod.makeID;

public class Paralysis extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Paralysis",
            5,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 15;

    public Paralysis() {
        super(cardInfo, true);

        isEthereal = true;
        setDamage(DAMAGE);
        setExhaust(true);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        isEthereal = false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new ShockWaveEffect(m.hb.cX, m.hb.cY, SUMIREKO_COLOR_BRIGHT.cpy(), ShockWaveEffect.ShockWaveType.CHAOTIC)));
        damageSingle(m, AbstractGameAction.AttackEffect.LIGHTNING);
        addToBot(new StunMonsterAction(m, p));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Paralysis();
    }
}