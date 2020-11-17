package sumireko.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.SealCard;
import sumireko.util.CardInfo;
import sumireko.util.PretendMonster;
import sumireko.util.SealIntent;

import java.util.Map;

import static sumireko.SumirekoMod.makeID;

public class EchoSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "EchoSeal",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.COMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 2;

    private static final int SEAL = 5;
    private static final int UPG_SEAL = 2;

    public EchoSeal() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        setSeal(SEAL, UPG_SEAL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        damageSingle(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        super.use(p, m);
    }

    @Override
    public void triggerSealEffect(AbstractMonster target) {
        if (this.sealValue > 0)
            damageSingle(target, this.sealValue, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    @Override
    public void instantSealEffect(PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {
        if (target != null)
            if (this.sealValue > 0)
                target.damage(new DamageInfo(AbstractDungeon.player, this.sealValue, DamageInfo.DamageType.THORNS));
    }

    @Override
    public void getIntent(SealIntent i) {
        i.intent = AbstractMonster.Intent.ATTACK;
    }

    @Override
    public AbstractCard makeCopy() {
        return new EchoSeal();
    }
}