package sumireko.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.SealSystem;
import sumireko.abstracts.SealCard;
import sumireko.util.CardInfo;
import sumireko.util.PretendMonster;
import sumireko.util.SealIntent;

import java.util.ArrayList;
import java.util.HashMap;

import static sumireko.SumirekoMod.makeID;

public class RepressionSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "RepressionSeal",
            0,
            CardType.SKILL,
            CardTarget.ENEMY,
            CardRarity.COMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);

    //private static final int DAMAGE = 4;
    private static final int DEBUFF = 1;
    private static final int UPG_DEBUFF = 1;
    private static final int SEAL = 3;
    private static final int UPG_SEAL = 2;

    public RepressionSeal() {
        super(cardInfo, false);

        //setDamage(DAMAGE);
        setMagic(DEBUFF, UPG_DEBUFF);
        setSeal(SEAL, UPG_SEAL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applySingle(m, getWeak(m, this.magicNumber));

        super.use(p, m);
    }

    @Override
    public void addAdjacentSealEffect(SealCard base, ArrayList<AbstractGameAction> actions, AbstractMonster target) {
        AbstractMonster selfTarget = SealSystem.getTarget(this);
        if (selfTarget != null && intendsAttack(selfTarget)) {
            actions.add(0, getDamageSingle(selfTarget, this.sealValue, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.POISON));
        }
    }

    @Override
    public void instantAdjacentEffect(SealCard primary, HashMap<AbstractMonster, PretendMonster> previewMonsters) {
        AbstractMonster target = SealSystem.getTarget(this);
        if (target != null && intendsAttack(target)) {
            PretendMonster pretendTarget = previewMonsters.get(target);
            if (pretendTarget != null) {
                if (this.sealValue > 0)
                    pretendTarget.sealDamage(new DamageInfo(AbstractDungeon.player, this.sealValue, DamageInfo.DamageType.THORNS), primary);
            }
        }
    }

    private static boolean intendsAttack(AbstractMonster m) {
        return (m.intent != AbstractMonster.Intent.STUN && m.intent != AbstractMonster.Intent.SLEEP && m.intent != AbstractMonster.Intent.ESCAPE &&
                (m.getIntentBaseDmg() > 0 ||
                m.intent == AbstractMonster.Intent.ATTACK ||
                m.intent == AbstractMonster.Intent.ATTACK_BUFF ||
                m.intent == AbstractMonster.Intent.ATTACK_DEBUFF ||
                m.intent == AbstractMonster.Intent.ATTACK_DEFEND));
    }

    @Override
    public void getIntent(SealIntent i) {
        AbstractMonster target = SealSystem.getTarget(this);
        if (target != null && intendsAttack(target)) {
            i.addIntent(SealIntent.BUFF);
            i.bonusEffect(String.valueOf(this.sealValue));
        }
    }
    @Override
    public void applyAdjacencyIntent(SealIntent sealIntent) {
        AbstractMonster target = SealSystem.getTarget(this);
        if (target != null && intendsAttack(target)) {
            sealIntent.addDamage(this.sealValue);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new RepressionSeal();
    }
}