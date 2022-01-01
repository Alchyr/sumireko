package sumireko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.RunicDome;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import sumireko.abstracts.SealCard;
import sumireko.actions.seals.*;
import sumireko.cards.uncommon.MirrorSeal;
import sumireko.effects.SealLineEffect;
import sumireko.effects.SealPreviewGlow;
import sumireko.enums.CustomCardTags;
import sumireko.interfaces.ModifySealPower;
import sumireko.patches.BlockPreview;
import sumireko.powers.ImprintPower;
import sumireko.util.HealthBarRender;
import sumireko.util.PretendMonster;
import sumireko.util.PreviewIntent;
import sumireko.util.SealIntent;

import java.util.*;

import static sumireko.SumirekoMod.*;
import static sumireko.util.MathHelper.dist;

public class SealSystem {
    public static boolean enabled = false;

    private static float x, y;

    public static SealCard[] aroundCards = new SealCard[4];
    //public static HashMap<String, Integer> cardCounts = new HashMap<>();
    public static SealCard centerCard = null;
    private static final HashMap<AbstractMonster, PretendMonster> previewMonsters = new HashMap<>();
    private static final Set<SealCard> ignorePreview = new HashSet<>();

    private static SealCard hoveredCard = null;

    private static final HashMap<SealCard, AbstractMonster> targets = new HashMap<>();
    public static AbstractMonster getTarget(SealCard card) {
        AbstractMonster t = targets.get(card);
        if (t != null) {
            return t;
        }

        for (Map.Entry<SealCard, AbstractMonster> targetEntry : targets.entrySet()) {
            if (targetEntry.getKey() instanceof MirrorSeal && card.equals(((MirrorSeal) targetEntry.getKey()).copying)) {
                return targetEntry.getValue();
            }
        }
        return null;
    }

    public static final SealIntent[] sealIntents = { new SealIntent(), new SealIntent(), new SealIntent(), new SealIntent(), new SealIntent() };
    private static final HashMap<AbstractMonster, PreviewIntent> previewIntents = new HashMap<>();
    public static final HashMap<AbstractCreature, HealthBarRender> healthBarRenders = new HashMap<>();

    public static int nextIndex = 0;

    private static float previewTime = 0.0f;
    private static float lineTime = 0.0f;

    private static boolean recalculate = false;
    private static boolean disableCalculation = false;

    private static final ArrayList<AbstractGameEffect> previewEffects = new ArrayList<>();
    private static final ArrayList<AbstractGameEffect> lineEffects = new ArrayList<>();


    // Consts
    private static final float PREVIEW_DELAY = 1.0f;
    private static final float LINE_DELAY = 0.2f;

    private static final float SMALL_SCALE = 0.2f;
    private static final float CENTER_SCALE = 0.25f;

    private static final float SMALL_PREVIEW_SCALE = 0.18f;
    private static final float CENTER_PREVIEW_SCALE = 0.22f;

    private static final float HOVER_SCALE = 0.66f;

    //all positions are relative to center of player hitbox.
    private static final float CENTER_X = 0;
    private static final float CENTER_Y = 130.0f * Settings.scale;

    private static final float TOP_POS_X = CENTER_X;
    private static final float TOP_POS_Y = CENTER_Y + 100.0f * Settings.scale;
    private static final float LEFT_POS_X = CENTER_X - 100.0f * Settings.scale;
    private static final float LEFT_POS_Y = CENTER_Y;
    private static final float RIGHT_POS_X = CENTER_X + 100.0f * Settings.scale;
    private static final float RIGHT_POS_Y = CENTER_Y;
    private static final float BOTTOM_POS_X = CENTER_X;
    private static final float BOTTOM_POS_Y = CENTER_Y - 100.0f * Settings.scale;

    private static final float INTENT_OFFSET = 50 * Settings.scale;

    private static final float HOVER_UNCENTERING_Y = 100.0f * Settings.scale;
    private static final float HOVER_UNCENTERING_X = 66.0f * Settings.scale;

    private static final float SHORT_DISTANCE = dist(TOP_POS_X, RIGHT_POS_X, TOP_POS_Y, RIGHT_POS_Y);
    private static final float LONG_DISTANCE = dist(TOP_POS_X, BOTTOM_POS_X, TOP_POS_Y, BOTTOM_POS_Y);



    public static void reset()
    {
        aroundCards = new SealCard[4];
        for (SealIntent i : sealIntents)
        {
            i.current = null;
            i.refresh();
        }
        //cardCounts.clear();
        centerCard = null;
        hoveredCard = null;
        nextIndex = 0;
        recalculate = false;
        disableCalculation = false;
        targets.clear();
        previewMonsters.clear();
        previewIntents.clear();
        previewEffects.clear();
        healthBarRenders.clear();
        ignorePreview.clear();
        lineEffects.clear();
        enabled = false;
        x = 0;
        y = 0;

        BlockPreview.amt = 0;
    }
    public static void refresh()
    {
        recalculate = false;
        disableCalculation = false;
        previewIntents.clear();
        healthBarRenders.clear();
        ignorePreview.clear();

        BlockPreview.amt = 0;
    }

    public static boolean addSeal(SealCard c, AbstractMonster target)
    {
        enabled = true;
        targets.put(c, target);
        if (nextIndex < 4)
        {
            c.targetDrawScale = SMALL_SCALE;
            aroundCards[nextIndex++] = c;
        }
        else if (nextIndex == 4)
        {
            c.targetDrawScale = CENTER_SCALE;
            centerCard = c;
            ++nextIndex;
        }
        else
        {
            return false;
        }
        updatePositions();
        return true;
    }

    public static void setPosition()
    {
        x = AbstractDungeon.player.hb.cX;
        y = AbstractDungeon.player.hb.y + AbstractDungeon.player.hb.height - INTENT_OFFSET;

        for (int i = 0; i < 5; ++i)
        {
            switch (i)
            {
                case 0:
                    sealIntents[i].x = x + TOP_POS_X;
                    sealIntents[i].y = y + TOP_POS_Y;
                    break;
                case 1:
                    sealIntents[i].x = x + BOTTOM_POS_X;
                    sealIntents[i].y = y + BOTTOM_POS_Y;
                    break;
                case 2:
                    sealIntents[i].x = x + LEFT_POS_X;
                    sealIntents[i].y = y + LEFT_POS_Y;
                    break;
                case 3:
                    sealIntents[i].x = x + RIGHT_POS_X;
                    sealIntents[i].y = y + RIGHT_POS_Y;
                    break;
                case 4:
                    sealIntents[i].x = x + CENTER_X;
                    sealIntents[i].y = y + CENTER_Y;
                    break;
            }
        }

        y += INTENT_OFFSET;
    }

    private static void updatePositions()
    {
        updatePositions(x, y);
    }

    private static void updatePositions(float x, float y)
    {
        //count = nextIndex
        switch (nextIndex)
        {
            case 5:
                centerCard.target_x = x + CENTER_X;
                centerCard.target_y = y + CENTER_Y;
            case 4:
                aroundCards[3].target_x = x + RIGHT_POS_X;
                aroundCards[3].target_y = y + RIGHT_POS_Y;
            case 3:
                aroundCards[2].target_x = x + LEFT_POS_X;
                aroundCards[2].target_y = y + LEFT_POS_Y;
            case 2:
                aroundCards[1].target_x = x + BOTTOM_POS_X;
                aroundCards[1].target_y = y + BOTTOM_POS_Y;
            case 1:
                aroundCards[0].target_x = x + TOP_POS_X;
                aroundCards[0].target_y = y + TOP_POS_Y;
                break;
            default:
                logger.info("Repositioning cards with 0 or somehow more than 5 cards?");
                break;
        }
    }

    public static void render(SpriteBatch sb)
    {
        if (AbstractDungeon.player == null || !enabled || (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT))
            return;

        if (recalculate)
            calculateSeals();

        previewTime -= Gdx.graphics.getDeltaTime();
        lineTime -= Gdx.graphics.getDeltaTime();
        boolean lines = lineTime <= 0.0f;

        boolean previewSpecific = false;
        boolean newPreview = false;

        if (previewTime <= 0.0f)
        {
            previewSpecific = true;
            newPreview = true;
            previewTime = PREVIEW_DELAY;
        }


        int renderAmount = AbstractDungeon.player.hoveredCard instanceof SealCard ? nextIndex : nextIndex - 1;

        if (lines)
        {
            lineTime = LINE_DELAY;
            for (int i = 0; i <= renderAmount; ++i)
            {
                switch (i)
                {
                    case 0:
                        if (renderAmount >= 3)
                            lineEffects.add(new SealLineEffect(x + TOP_POS_X, y + TOP_POS_Y, x + RIGHT_POS_X, y + RIGHT_POS_Y, SHORT_DISTANCE, 135));

                        if (renderAmount >= 1 && renderAmount != 3)
                            lineEffects.add(new SealLineEffect(x + TOP_POS_X, y + TOP_POS_Y, x + BOTTOM_POS_X, y + BOTTOM_POS_Y, LONG_DISTANCE, 90));

                        if (renderAmount >= 2)
                            lineEffects.add(new SealLineEffect(x + TOP_POS_X, y + TOP_POS_Y, x + LEFT_POS_X, y + LEFT_POS_Y, SHORT_DISTANCE, 45));
                        break;
                    case 1:
                        if (renderAmount >= 3)
                            lineEffects.add(new SealLineEffect(x + BOTTOM_POS_X, y + BOTTOM_POS_Y, x + RIGHT_POS_X, y + RIGHT_POS_Y, SHORT_DISTANCE, 45));

                        if (renderAmount >= 2)
                            lineEffects.add(new SealLineEffect(x + BOTTOM_POS_X, y + BOTTOM_POS_Y, x + LEFT_POS_X, y + LEFT_POS_Y, SHORT_DISTANCE, 135));
                        break;
                    case 2:
                        if (renderAmount >= 4)
                            lineEffects.add(new SealLineEffect(x + LEFT_POS_X, y + LEFT_POS_Y, x + RIGHT_POS_X, y + RIGHT_POS_Y, LONG_DISTANCE, 0));
                        break;
                }
            }
        }

        for (AbstractGameEffect e : lineEffects)
        {
            e.update();
            e.render(sb);
        }
        for (AbstractGameEffect e : previewEffects)
        {
            e.update();
            e.render(sb);
        }
        lineEffects.removeIf((e)->e.isDone);
        previewEffects.removeIf((e)->e.isDone);


        if (hoveredCard != null)
        {
            hoveredCard.updateHoverLogic();
            if (AbstractDungeon.isScreenUp || AbstractDungeon.player.isDraggingCard)
            {
                hoveredCard.unhover();
            }
            hoveredCard.update();

            if (!hoveredCard.publicHovered)
            {
                if (hoveredCard == centerCard)
                {
                    hoveredCard.targetDrawScale = CENTER_SCALE;
                }
                else
                {
                    hoveredCard.targetDrawScale = SMALL_SCALE;
                }
                updatePositions(x, y);
                hoveredCard = null;
            }
        }


        boolean centerPreview = previewSpecific;
        for (int i = 0; i < 4; ++i)
        {
            if (aroundCards[i] == null) //empty slots are handled differently
            {
                //render next card position preview based on where it is null
                if (previewSpecific) {
                    switch(i)
                    {
                        case 0:
                            previewEffects.add(new SealPreviewGlow(x + TOP_POS_X, y + TOP_POS_Y, SMALL_PREVIEW_SCALE, SUMIREKO_COLOR_VERY_BRIGHT.cpy()));
                            break;
                        case 1:
                            previewEffects.add(new SealPreviewGlow(x + BOTTOM_POS_X, y + BOTTOM_POS_Y, SMALL_PREVIEW_SCALE, SUMIREKO_COLOR_VERY_BRIGHT.cpy()));
                            break;
                        case 2:
                            previewEffects.add(new SealPreviewGlow(x + LEFT_POS_X, y + LEFT_POS_Y, SMALL_PREVIEW_SCALE, SUMIREKO_COLOR_VERY_BRIGHT.cpy()));
                            break;
                        case 3:
                            previewEffects.add(new SealPreviewGlow(x + RIGHT_POS_X, y + RIGHT_POS_Y, SMALL_PREVIEW_SCALE, SUMIREKO_COLOR_VERY_BRIGHT.cpy()));
                            break;
                    }
                    previewSpecific = false;
                    centerPreview = false; //center is not the specific preview
                }
                else if (newPreview)
                {
                    switch(i)
                    {
                        case 0:
                            previewEffects.add(new SealPreviewGlow(x + TOP_POS_X, y + TOP_POS_Y, SMALL_PREVIEW_SCALE, SUMIREKO_COLOR_DIM));
                            break;
                        case 1:
                            previewEffects.add(new SealPreviewGlow(x + BOTTOM_POS_X, y + BOTTOM_POS_Y, SMALL_PREVIEW_SCALE, SUMIREKO_COLOR_DIM));
                            break;
                        case 2:
                            previewEffects.add(new SealPreviewGlow(x + LEFT_POS_X, y + LEFT_POS_Y, SMALL_PREVIEW_SCALE, SUMIREKO_COLOR_DIM));
                            break;
                        case 3:
                            previewEffects.add(new SealPreviewGlow(x + RIGHT_POS_X, y + RIGHT_POS_Y, SMALL_PREVIEW_SCALE, SUMIREKO_COLOR_DIM));
                            break;
                    }
                }
                continue; //don't go to the rest of the code for null slots.
            }

            if (!AbstractDungeon.isScreenUp && hoveredCard == null && !AbstractDungeon.player.isDraggingCard)
            {
                boolean isHovered = aroundCards[i].publicHovered;
                aroundCards[i].updateHoverLogic();
                if (aroundCards[i].publicHovered)
                {
                    aroundCards[i].drawScale = (SMALL_SCALE + HOVER_SCALE) / 2;
                    aroundCards[i].targetDrawScale = HOVER_SCALE;

                    if (!isHovered) //just hovered
                    {
                        hoveredCard = aroundCards[i];
                        switch (i)
                        {
                            case 0:
                                hoveredCard.target_y += HOVER_UNCENTERING_Y;
                                break;
                            case 1:
                                hoveredCard.target_y -= HOVER_UNCENTERING_Y;
                                break;
                            case 2:
                                hoveredCard.target_x -= HOVER_UNCENTERING_X;
                                break;
                            case 3:
                                hoveredCard.target_x += HOVER_UNCENTERING_X;
                                break;
                        }

                        aroundCards[i].update();
                        continue; //skip render, hovered card will be updated and rendered later so that it appears on top
                    }
                }
            }

            aroundCards[i].update();
            aroundCards[i].render(sb);
        }

        center: if (centerCard != null && centerCard != hoveredCard)
        {
            if (!AbstractDungeon.isScreenUp && hoveredCard == null && !AbstractDungeon.player.isDraggingCard)
            {
                centerCard.updateHoverLogic();
                if (centerCard.publicHovered)
                {
                    centerCard.drawScale = (CENTER_SCALE + HOVER_SCALE) / 2;
                    centerCard.targetDrawScale = HOVER_SCALE;

                    hoveredCard = centerCard;
                    centerCard.update();
                    break center;
                }
            }
            centerCard.update();
            centerCard.render(sb);
        }
        else if (centerPreview)
        {
            //render next card position preview in center
            previewTime = PREVIEW_DELAY;
            previewEffects.add(new SealPreviewGlow(x + CENTER_X, y + CENTER_Y, CENTER_PREVIEW_SCALE, SUMIREKO_COLOR_BRIGHT));
        }
        else if (newPreview)
        {
            previewTime = PREVIEW_DELAY;
            previewEffects.add(new SealPreviewGlow(x + CENTER_X, y + CENTER_Y, CENTER_PREVIEW_SCALE, SUMIREKO_COLOR_DIM));
        }

        for (SealIntent i : sealIntents)
        {
            i.update();
            i.render(sb);
        }

        Iterator<PreviewIntent> i = previewIntents.values().iterator();

        while (i.hasNext())
        {
            PreviewIntent h = i.next();

            h.update();
            if (h.shouldRender())
            {
                h.render(sb);
            }
            if (!h.isValid)
            {
                i.remove();
                recalculate = true;
            }
        }
    }

    public static void renderHovered(SpriteBatch sb)
    {
        if (hoveredCard != null)
        {
            //render reticle of targets
            AbstractMonster m = targets.get(hoveredCard);
            if (m != null)
            {
                m.renderReticle(sb);
            }

            switch (hoveredCard.target)
            {
                case SELF:
                case SELF_AND_ENEMY:
                    AbstractDungeon.player.renderReticle(sb);
                    break;
                case ALL:
                    AbstractDungeon.player.renderReticle(sb);
                case ALL_ENEMY:
                    for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters)
                    {
                        if (mo != m && !mo.isDeadOrEscaped())
                        {
                            mo.renderReticle(sb);
                        }
                    }
                    break;
            }

            hoveredCard.render(sb);
        }
    }

    public static void calculateSeals()
    {
        int i = 0;
        boolean calculateIntentPreview = aroundCards[0] != null;

        if (!disableCalculation) //skip the calculation. Values can no longer change.
        {
            recalculate = false;

            //reset
            for (; i < 4; ++i) {
                if (aroundCards[i] == null)
                    break;

                aroundCards[i].resetSealValue();
            }
            if (centerCard != null)
                centerCard.resetSealValue();

            //flat bonuses
            for (i = 0; i < 4; ++i)
            {
                if (aroundCards[i] == null)
                    break;

                for (AbstractPower p : AbstractDungeon.player.powers)
                {
                    if (p instanceof ModifySealPower)
                        ((ModifySealPower) p).modifySeal(aroundCards[i]);
                }

                aroundCards[i].lockBaseValue(); //This saves the current value as the value to be used for base adjacency bonuses.
            }
            if (centerCard != null) {
                for (AbstractPower p : AbstractDungeon.player.powers) {
                    if (p instanceof ModifySealPower)
                        ((ModifySealPower) p).modifySeal(centerCard);
                }

                centerCard.lockBaseValue();
            }

            //adjacency and power bonuses.

            //First step: All buff seals are modified by the surrounding seals using base (pre-buff seal, post-flat bonus) values
            for (i = 0; i < 4; ++i) //addition/subtraction modifiers
            {
                if (aroundCards[i] == null)
                    break;
                if (!aroundCards[i].hasTag(CustomCardTags.BUFF_SEAL))
                    continue; //Not a buff seal, move on.

                for (SealCard c : getAdjacentSeals(i)) {
                    c.applyBaseAdjacencyEffect(aroundCards[i]);
                }
            }
            if (centerCard != null && centerCard.hasTag(CustomCardTags.BUFF_SEAL))
            {
                for (i = 0; i < 4; ++i)
                {
                    if (aroundCards[i] == null)
                        break;
                    aroundCards[i].applyBaseAdjacencyEffect(centerCard);
                }
            }
            for (i = 0; i < 4; ++i) //multiplication modifiers
            {
                if (aroundCards[i] == null)
                    break;
                if (!aroundCards[i].hasTag(CustomCardTags.BUFF_SEAL))
                    continue;

                for (SealCard c : getAdjacentSeals(i)) {
                    c.applyFinalBaseAdjacencyEffect(aroundCards[i]);
                }
            }
            if (centerCard != null && centerCard.hasTag(CustomCardTags.BUFF_SEAL))
            {
                for (i = 0; i < 4; ++i)
                {
                    if (aroundCards[i] == null)
                        break;

                    aroundCards[i].applyFinalBaseAdjacencyEffect(centerCard);
                }

                centerCard.centerMultiplier();
            }

            //Do the same for non-buff seals. Buff seals buff each other first, then buff non-buff seals.
            for (i = 0; i < 4; ++i) //add/subtract
            {
                if (aroundCards[i] == null)
                    break;

                if (aroundCards[i].hasTag(CustomCardTags.BUFF_SEAL)) //buffs already done
                    continue;

                for (SealCard c : getAdjacentSeals(i)) {
                    c.applyAdjacencyEffect(aroundCards[i]); //now using non-base value: post-buff
                }
            }
            if (centerCard != null && !centerCard.hasTag(CustomCardTags.BUFF_SEAL))
            {
                for (i = 0; i < 4; ++i)
                {
                    if (aroundCards[i] == null)
                        break;

                    aroundCards[i].applyAdjacencyEffect(centerCard);
                }
            }
            for (i = 0; i < 4; ++i) //multiplication
            {
                if (aroundCards[i] == null)
                    break;
                if (aroundCards[i].hasTag(CustomCardTags.BUFF_SEAL))
                    continue;

                for (SealCard c : getAdjacentSeals(i)) {
                    c.applyFinalAdjacencyEffect(aroundCards[i]); //now using non-base value: post-buff
                }
            }
            if (centerCard != null && !centerCard.hasTag(CustomCardTags.BUFF_SEAL))
            {
                for (i = 0; i < 4; ++i)
                {
                    if (aroundCards[i] == null) //what the Fuck how is there a card in the center wit an empty surrounding card
                        continue;

                    aroundCards[i].applyFinalAdjacencyEffect(centerCard);
                }

                centerCard.centerMultiplier();
            }


            //- SEAL INTENTS -
            //First, set base intent from seal. Then, modify with adjacent seals.
            for (i = 0; i < 4; ++i)
            {
                if (aroundCards[i] == null)
                    break;

                sealIntents[i].setSeal(aroundCards[i]);

                for (SealCard adjacent : getAdjacentSeals(i)) {
                    adjacent.applyAdjacencyIntent(sealIntents[i]);
                }
            }
            if (centerCard != null)
            {
                sealIntents[4].setSeal(centerCard);

                for (i = 0; i < 4; ++i)
                {
                    if (aroundCards[i] == null)
                        break;

                    aroundCards[i].applyAdjacencyIntent(sealIntents[4]);
                }
            }
        }

        // Calculate preview result
        healthBarRenders.clear();

        if (calculateIntentPreview) {
            //Recalculate previews
            if (!disableCalculation) {
                previewMonsters.clear();

                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
                    if (!m.halfDead && !m.isDying && !m.isEscaping)
                        previewMonsters.put(m, new PretendMonster(m));
                    else
                        previewIntents.remove(m); //remove those that are no longer alive.

                PretendMonster.prepareFakePlayerPowers();

                if (centerCard != null)
                {
                    HealthBarRender h = centerCard.instantSealEffect(previewMonsters.get(SealSystem.targets.get(centerCard)), previewMonsters);

                    for (i = 0; i < 4; ++i) {
                        if (aroundCards[i] == null)
                            break;

                        aroundCards[i].instantAdjacentEffect(centerCard, previewMonsters);
                    }

                    if (h != null)
                    {
                        if (!healthBarRenders.containsKey(h.target))
                            healthBarRenders.put(h.target, h);
                        else
                            healthBarRenders.get(h.target).combine(h);
                    }
                }

                for (i = 3; i >= 0; --i)
                {
                    if (aroundCards[i] == null)
                        continue;

                    HealthBarRender h = aroundCards[i].instantSealEffect(previewMonsters.get(SealSystem.targets.get(aroundCards[i])), previewMonsters);

                    for (SealCard c : getAdjacentSeals(i)) {
                        c.instantAdjacentEffect(aroundCards[i], previewMonsters);
                    }

                    if (h != null)
                    {
                        if (!healthBarRenders.containsKey(h.target))
                            healthBarRenders.put(h.target, h);
                        else
                            healthBarRenders.get(h.target).combine(h);
                    }
                }
            }

            for (Map.Entry<AbstractMonster, PretendMonster> m : previewMonsters.entrySet())
            {
                int difference = m.getKey().currentHealth - m.getValue().currentHealth;
                if (difference > 0)
                {
                    HealthBarRender h = new HealthBarRender(m.getKey(), difference);

                    if (!healthBarRenders.containsKey(h.target))
                        healthBarRenders.put(h.target, h);
                    else
                        healthBarRenders.get(h.target).combine(h);
                }

                if (!hideIntents()) {
                    //TODO:
                    //Special monster intents (multi-intent, card intents)

                    m.getValue().calculateDamage();

                    PreviewIntent newIntent = new PreviewIntent(m.getKey(), m.getValue());

                    if (previewIntents.containsKey(m.getKey())) {
                        if (!previewIntents.get(m.getKey()).equals(newIntent))
                            previewIntents.put(m.getKey(), newIntent);
                    } else {
                        previewIntents.put(m.getKey(), newIntent);
                    }
                }
            }
        }
        else
        {
            previewIntents.clear();
        }

        BlockPreview.amt = SealSystem.getBlock();
    }

    private static boolean hideIntents() //TODO: Add compatibility for other mod stuff that hides intents in separate files.
    {
        return AbstractDungeon.player.hasRelic(RunicDome.ID);
    }

    public static int getAdjacentCount(SealCard c) {
        int count = 0;
        int index = 0;
        for (; index < 4; ++index) {
            if (aroundCards[index] == null) {
                break;
            }
            else if (aroundCards[index].equals(c)) {
                switch (index) {
                    case 0:
                        if (aroundCards[3] != null)
                        {
                            ++count;
                        }
                        else if (aroundCards[1] != null)
                        {
                            ++count;
                        }
                        if (aroundCards[2] != null)
                            ++count;
                        break;
                    case 1:
                        if (aroundCards[3] != null)
                        {
                            ++count;
                        }
                        else if (centerCard == null)
                        {
                            ++count;
                        }
                        if (aroundCards[2] != null)
                            ++count;
                        break;
                    case 2:
                    case 3:
                        ++count;
                        if (aroundCards[1] != null)
                            ++count;
                        break;
                    case 4: //center seal
                        for (int i = 0; i < 4; ++i)
                            if (aroundCards[i] != null)
                                ++count;
                        return count;
                }

                if (centerCard != null)
                    ++count;

                return count;
            }
        }

        if (index == 4 && centerCard != null && centerCard.equals(c))
            return 4;

        return 0;
    }
    public static ArrayList<SealCard> getAdjacentSeals(SealCard c) {
        int index = 0;
        for (; index < 4; ++index) {
            if (aroundCards[index] == null)
                break;
            else if (aroundCards[index].equals(c))
                return getAdjacentSeals(index);
        }

        if (index == 4 && centerCard != null && centerCard.equals(c))
            return getAdjacentSeals(4);

        return new ArrayList<>();
    }
    public static ArrayList<SealCard> getAdjacentSeals(int index) {
        ArrayList<SealCard> seals = new ArrayList<>();

        switch (index) {
            case 0:
                if (aroundCards[3] != null)
                {
                    seals.add(aroundCards[3]);
                }
                else if (aroundCards[1] != null)
                {
                    seals.add(aroundCards[1]);
                }
                if (aroundCards[2] != null)
                    seals.add(aroundCards[2]);
                break;
            case 1:
                if (aroundCards[3] != null)
                {
                    seals.add(aroundCards[3]);
                }
                else if (centerCard == null && aroundCards[0] != null)
                {
                    seals.add(aroundCards[0]);
                }
                if (aroundCards[2] != null)
                    seals.add(aroundCards[2]);
                break;
            case 2:
            case 3:
                if (aroundCards[0] != null)
                    seals.add(aroundCards[0]);

                if (aroundCards[1] != null)
                    seals.add(aroundCards[1]);
                break;
            case 4: //center seal
                for (int i = 0; i < 4; ++i)
                    if (aroundCards[i] != null)
                        seals.add(aroundCards[i]);
                return seals;
        }

        if (centerCard != null)
        {
            seals.add(centerCard);
        }


        return seals;
    }

    public static void triggerEndOfTurnEffects() {
        previewIntents.clear();

        disableCalculation = true;

        if (centerCard != null)
        {
            final SealCard t = centerCard;
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                @Override
                public void update() {
                    ignorePreview.add(t);
                    this.isDone = true;
                }
            });
            centerCard.superFlash(Color.PURPLE.cpy());
            ArrayList<AbstractGameAction> actions = centerCard.triggerSealEffect(SealSystem.targets.get(centerCard));

            for (SealCard adj : getAdjacentSeals(4)) {
                adj.addAdjacentSealEffect(centerCard, actions, SealSystem.targets.get(centerCard));
            }
            for (SealCard adj : getAdjacentSeals(4)) {
                adj.modifyAdjacentSealEffect(centerCard, actions, SealSystem.targets.get(centerCard));
            }

            for (AbstractGameAction action : actions)
                AbstractDungeon.actionManager.addToBottom(action);
        }

        for (int i = 3; i >= 0; --i)
        {
            if (aroundCards[i] == null)
                continue;

            final SealCard t = aroundCards[i];
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                @Override
                public void update() {
                    ignorePreview.add(t);
                    recalculate = true;
                    this.isDone = true;
                }
            });

            aroundCards[i].superFlash(Color.PURPLE.cpy());
            ArrayList<AbstractGameAction> actions = aroundCards[i].triggerSealEffect(SealSystem.targets.get(aroundCards[i]));

            for (SealCard adj : getAdjacentSeals(i)) {
                adj.addAdjacentSealEffect(aroundCards[i], actions, SealSystem.targets.get(centerCard));
            }
            for (SealCard adj : getAdjacentSeals(i)) {
                adj.modifyAdjacentSealEffect(aroundCards[i], actions, SealSystem.targets.get(centerCard));
            }

            for (AbstractGameAction action : actions)
                AbstractDungeon.actionManager.addToBottom(action);
        }
    }
    public static void triggerEndOfTurnMovement() {
        int keepAmount = 0;
        AbstractPower imprint = AbstractDungeon.player.getPower(ImprintPower.POWER_ID);
        if (imprint != null) {
            keepAmount = imprint.amount;
            imprint.flash();
        }

        if (centerCard != null)
        {
            if (keepAmount < 5) {
                if (centerCard.hasTag(CustomCardTags.RETURNING_SEAL))
                    AbstractDungeon.actionManager.addToBottom(new ReturnSealAction(centerCard, true));
                else if (centerCard.hasTag(CustomCardTags.ULTRA_FRAGILE_SEAL))
                    AbstractDungeon.actionManager.addToBottom(new YeetSealAction(centerCard, true));
                else if (centerCard.hasTag(CustomCardTags.FRAGILE_SEAL))
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSealAction(centerCard, true));
                else
                    AbstractDungeon.actionManager.addToBottom(new DiscardSealAction(centerCard, true));
            }
            else {
                if (SealSystem.targets.get(centerCard) != null && SealSystem.targets.get(centerCard).isDeadOrEscaped()) {
                    //seal targets and the target is f

                    AbstractMonster m = AbstractDungeon.getRandomMonster();
                    if (m != null) {
                        targets.put(centerCard, m);
                    }
                }
            }
        }

        for (int i = 3; i >= 0; --i)
        {
            if (aroundCards[i] == null)
                continue;

            if (keepAmount < i + 1) {
                if (aroundCards[i].hasTag(CustomCardTags.RETURNING_SEAL))
                    AbstractDungeon.actionManager.addToBottom(new ReturnSealAction(aroundCards[i], i));
                else if (aroundCards[i].hasTag(CustomCardTags.ULTRA_FRAGILE_SEAL))
                    AbstractDungeon.actionManager.addToBottom(new YeetSealAction(aroundCards[i], i));
                else if (aroundCards[i].hasTag(CustomCardTags.FRAGILE_SEAL))
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSealAction(aroundCards[i], i));
                else
                    AbstractDungeon.actionManager.addToBottom(new DiscardSealAction(aroundCards[i], i));
            }
            else { //seal is kept.
                if (SealSystem.targets.get(aroundCards[i]) != null && SealSystem.targets.get(aroundCards[i]).isDeadOrEscaped()) {
                    //seal targets and the target is f

                    AbstractMonster m = AbstractDungeon.getRandomMonster();
                    if (m != null) {
                        targets.put(aroundCards[i], m);
                    }
                }
            }
        }

        AbstractDungeon.actionManager.addToBottom(new RefreshSealSystemAction());
    }

    private static int getBlock() {
        int amt = 0;
        if (centerCard != null && !ignorePreview.contains(centerCard)) {
            amt += centerCard.getSealBlock();
        }

        for (int i = 0; i < 4; ++i) {
            if (aroundCards[i] != null && !ignorePreview.contains(aroundCards[i])) {
                amt += aroundCards[i].getSealBlock();
            }
        }

        return amt;
    }
}