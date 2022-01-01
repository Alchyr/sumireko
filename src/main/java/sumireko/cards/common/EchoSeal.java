package sumireko.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.SealCard;
import sumireko.actions.cards.EchoSealAction;
import sumireko.util.CardInfo;
import sumireko.util.HealthBarRender;
import sumireko.util.PretendMonster;
import sumireko.util.SealIntent;

import java.util.ArrayList;
import java.util.Map;

import static sumireko.SumirekoMod.makeID;

public class EchoSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "EchoSeal",
            1,
            CardType.SKILL,
            CardTarget.ENEMY,
            CardRarity.COMMON);

    public static final String ID = makeID(cardInfo.cardName);


    /*private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 2;*/

    private static final int SEAL = 7;
    private static final int UPG_SEAL = 3;

    public EchoSeal() {
        super(cardInfo, false);

        //setDamage(DAMAGE, UPG_DAMAGE);
        setSeal(SEAL, UPG_SEAL);
    }

    public void triggerOnGlowCheck() {
        if (!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty() && (AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1)) instanceof SealCard) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            super.triggerOnGlowCheck();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //damageSingle(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);

        addToBot(new EchoSealAction());

        super.use(p, m);
    }

    @Override
    public ArrayList<AbstractGameAction> triggerSealEffect(AbstractMonster target) {
        ArrayList<AbstractGameAction> actions = new ArrayList<>();
        if (this.sealValue > 0)
            actions.add(getDamageSingle(target, this.sealValue, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        return actions;
    }

    @Override
    public HealthBarRender instantSealEffect(PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {
        if (target != null)
            if (this.sealValue > 0)
                target.sealDamage(new DamageInfo(AbstractDungeon.player, this.sealValue, DamageInfo.DamageType.THORNS), this);

        return null;
    }

    @Override
    public void getIntent(SealIntent i) {
        i.baseDamage(this.sealValue);
    }

    @Override
    public AbstractCard makeCopy() {
        return new EchoSeal();
    }
}