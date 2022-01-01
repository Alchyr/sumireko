package sumireko.cards.uncommon;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import sumireko.abstracts.BaseCard;
import sumireko.actions.general.RandomOccultCardsAction;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Brainwave extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Brainwave",
            2,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY,
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int DAMAGE = 9;
    private static final int UPG_DAMAGE = 3;


    public Brainwave() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new ShockWaveEffect(p.hb.cX, p.hb.cY, new Color(MathUtils.random(1.0f), MathUtils.random(1.0f), MathUtils.random(1.0f), 1.0f), ShockWaveEffect.ShockWaveType.NORMAL)));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new ShockWaveEffect(p.hb.cX, p.hb.cY, new Color(MathUtils.random(1.0f), MathUtils.random(1.0f), MathUtils.random(1.0f), 1.0f), ShockWaveEffect.ShockWaveType.ADDITIVE)));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_PIERCING_WAIL", -0.5f, true));

        damageAll(AbstractGameAction.AttackEffect.NONE);
        addToBot(new RandomOccultCardsAction(1, (c)->c.type == CardType.ATTACK));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Brainwave();
    }
}