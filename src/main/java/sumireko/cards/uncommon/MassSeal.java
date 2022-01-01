package sumireko.cards.uncommon;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.SealCard;
import sumireko.util.CardInfo;
import sumireko.util.HealthBarRender;
import sumireko.util.PretendMonster;
import sumireko.util.SealIntent;

import java.util.ArrayList;
import java.util.Map;

import static sumireko.SumirekoMod.makeID;

public class MassSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "MassSeal",
            1,
            CardType.SKILL,
            CardTarget.ALL_ENEMY,
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int SEAL = 2;

    public MassSeal() {
        super(cardInfo, true);

        setSeal(SEAL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded)
            drawCards(1);

        super.use(p, m);
    }

    @Override
    public ArrayList<AbstractGameAction> triggerSealEffect(AbstractMonster target) {
        ArrayList<AbstractGameAction> actions = new ArrayList<>();
        if (this.sealValue > 0) {
            int amt = AbstractDungeon.player.hand.size();
            for (int i = 0; i < amt; ++i) {
                actions.add(getDamageAll(this.sealValue, DamageInfo.DamageType.THORNS, MathUtils.randomBoolean() ? AbstractGameAction.AttackEffect.SLASH_HORIZONTAL : AbstractGameAction.AttackEffect.SLASH_VERTICAL, true));
            }
        }

        return actions;
    }

    @Override
    public void getIntent(SealIntent i) {
        i.baseDamage(this.sealValue, AbstractDungeon.player.hand.size());
    }

    @Override
    public HealthBarRender instantSealEffect(PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {
        if (this.sealValue > 0)
        {
            for (int i = 0; i < AbstractDungeon.player.hand.size(); ++i) {
                for (Map.Entry<AbstractMonster, PretendMonster> monster : pretendMonsters.entrySet())
                {
                    monster.getValue().sealDamage(new DamageInfo(AbstractDungeon.player, this.sealValue, DamageInfo.DamageType.THORNS), this);
                }
            }
        }
        return null;
    }

    @Override
    public AbstractCard makeCopy() {
        return new MassSeal();
    }
}