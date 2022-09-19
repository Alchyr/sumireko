package sumireko.cards.uncommon;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.SealCard;
import sumireko.actions.seals.MirrorAction;
import sumireko.actions.seals.SealAction;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;
import sumireko.util.HealthBarRender;
import sumireko.util.PretendMonster;
import sumireko.util.SealIntent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static sumireko.SumirekoMod.makeID;

public class MirrorSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "MirrorSeal",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    public SealCard copying;


    public MirrorSeal() {
        super(cardInfo, true);

        copying = null;

        this.tags.add(CustomCardTags.FRAGILE_SEAL);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.tags.remove(CustomCardTags.FRAGILE_SEAL);

        if (copying != null)
            copying.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        MirrorAction mirror = new MirrorAction(this);
        addToBot(mirror);
        addToBot(new SealAction(this, mirror));
    }

    @Override
    public void triggerOnExhaust() {
        copying = null;
        this.cardID = ID;
        this.target = cardInfo.cardTarget;
    }

    @Override
    public void onMoveToDiscard() {
        copying = null;
        this.cardID = ID;
        this.target = cardInfo.cardTarget;
    }

    @Override
    public boolean hasTag(CardTags tagToCheck) {
        if (copying != null)
        {
            return copying.hasTag(tagToCheck);
        }
        return super.hasTag(tagToCheck);
    }

    @Override
    public void resetSealValue() {
        super.resetSealValue();
        if (copying != null)
            copying.resetSealValue();
    }

    @Override
    public ArrayList<AbstractGameAction> triggerSealEffect(AbstractMonster target) {
        if (copying != null)
            return copying.triggerSealEffect(target);

        return new ArrayList<>();
    }
    @Override
    public void addAdjacentSealEffect(SealCard base, ArrayList<AbstractGameAction> actions, AbstractMonster target) {
        if (copying != null) {
            copying.addAdjacentSealEffect(base, actions, target);
        }
    }
    @Override
    public void modifyAdjacentSealEffect(SealCard base, ArrayList<AbstractGameAction> actions, AbstractMonster target) {
        if (copying != null) {
            copying.modifyAdjacentSealEffect(base, actions, target);
        }
    }

    @Override
    public HealthBarRender instantSealEffect(PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {
        if (copying != null)
            return copying.instantSealEffect(target, pretendMonsters);

        return null;
    }
    @Override
    public void instantAdjacentEffect(SealCard primary, HashMap<AbstractMonster, PretendMonster> previewMonsters) {
        if (copying != null) {
            copying.instantAdjacentEffect(primary, previewMonsters);
        }
    }
    @Override
    public void instantAdjacentEffectOnUnblockedDamage(SealCard base, PretendMonster pretendMonster, int damage) {
        if (copying != null) {
            copying.instantAdjacentEffectOnUnblockedDamage(base, pretendMonster, damage);
        }
    }

    @Override
    public void negateSeal() {
        super.negateSeal();
        if (copying != null)
            copying.negateSeal();
    }

    @Override
    public void modifySealValue(int amount) {
        super.modifySealValue(amount);
        if (copying != null)
            copying.modifySealValue(amount);
    }

    @Override
    public void multiplySealValue(int amount) {
        super.multiplySealValue(amount);
        if (copying != null)
            copying.multiplySealValue(amount);
    }

    @Override
    public void centerMultiplier() {
        super.centerMultiplier();
        if (copying != null)
            copying.centerMultiplier();
    }

    @Override
    public float modifyDamage(DamageInfo.DamageType type, float damage) {
        if (copying != null)
        {
            return copying.modifyDamage(type, damage);
        }
        return super.modifyDamage(type, damage);
    }

    @Override
    public void lockBaseValue() {
        super.lockBaseValue();
        if (copying != null)
            copying.lockBaseValue();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (copying != null)
            copying.applyPowers();
    }

    public void applyBaseAdjacencyEffect(SealCard c)
    {
        if (copying != null)
            copying.applyBaseAdjacencyEffect(c);
    }
    public void applyAdjacencyEffect(SealCard c)
    {
        if (copying != null)
            copying.applyAdjacencyEffect(c);
    }
    public void applyFinalBaseAdjacencyEffect(SealCard c)
    {
        if (copying != null)
            copying.applyFinalBaseAdjacencyEffect(c);
    }
    public void applyFinalAdjacencyEffect(SealCard c)
    {
        if (copying != null)
            copying.applyFinalAdjacencyEffect(c);
    }

    @Override
    public void getIntent(SealIntent i) {
        if (copying != null)
        {
            copying.getIntent(i);
        }
    }

    @Override
    public void applyAdjacencyIntent(SealIntent sealIntent) {
        if (copying != null)
        {
            copying.applyAdjacencyIntent(sealIntent);
        }
    }

    @Override
    public int getSealBlock() {
        if (copying != null) {
            return copying.getSealBlock();
        }
        return 0;
    }

    @Override
    public void hover() {
        if (copying != null)
        {
            copying.hover();
        }
        super.hover();
    }

    @Override
    public void render(SpriteBatch sb) {
        if (copying != null)
        {
            copying.current_x = this.current_x;
            copying.current_y = this.current_y;
            copying.drawScale = this.drawScale;
            copying.render(sb);
        }
        else
        {
            super.render(sb);
        }
    }

    //:) for interaction with Dreamcatcher
    @Override
    public AbstractCard makeStatEquivalentCopy() {
        if (copying != null)
            return copying.makeStatEquivalentCopy();
        return super.makeStatEquivalentCopy();
    }

    @Override
    public AbstractCard makeCopy() {
        if (copying != null)
            return copying.makeCopy();
        return new MirrorSeal();
    }
}