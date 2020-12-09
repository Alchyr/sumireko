package sumireko._R̥̼̳͙̣͈̽̔͗͐ͅÈC̜̫̤̪͔ͨO̻͖̱̰̬̝̺ͮ͂̑̍̽Ṉ͕̹͎̖͚̱ͬ͛Ṡ̢͈̥͈̘̜ͩͨͥͦ́Ṫ͍̈́ͬ̔͒̐̆͢R͙͕̯͓̥̺̜͂U͚͍̇̀͒̽ͦC̙̳͓͉̙̑͗T̳̖̃͒̈̈́̏̀;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.SumirekoMod;

import java.util.ArrayList;
import java.util.Iterator;

public class _IMPROVE extends AbstractGameAction {
    private static ArrayList<Texture> tempTextures = new ArrayList<>();

    public static void _clean() {
        for (Texture t : tempTextures) {
            t.dispose();
        }

        tempTextures.clear();
    }


    private AbstractMonster _materia;

    public static AbstractGameAction _readjust(AbstractMonster m, AbstractCreature source) {
        return new __GOODNIGHT._R̥̼̳͙̣͈̽̔͗͐ͅÈC̜̫̤̪͔ͨO̻͖̱̰̬̝̺ͮ͂̑̍̽Ṉ͕̹͎̖͚̱ͬ͛Ṡ̢͈̥͈̘̜ͩͨͥͦ́Ṫ͍̈́ͬ̔͒̐̆͢R͙͕̯͓̥̺̜͂U͚͍̇̀͒̽ͦC̙̳͓͉̙̑͗T̳̖̃͒̈̈́̏̀._IMPROVE(m, source);
    }

    private float strength;

    public _IMPROVE(AbstractMonster _target, AbstractCreature source, float strength) {
        this._materia = _target;// 32
        this.source = source;// 33
        this.actionType = ActionType.SPECIAL;

        this.strength = strength;
    }

    public void update() {
        try {
            TextureAtlas _form = (TextureAtlas) ReflectionHacks.getPrivate(this._materia, AbstractCreature.class, "atlas");// 40
            if (_form != null) {// 41
                ArrayList<Texture> regionTextures = new ArrayList<>();
                _form.getRegions();
                Iterator<TextureAtlas.AtlasRegion> var3 = _form.getRegions().iterator();

                TextureAtlas.AtlasRegion r;
                while(var3.hasNext()) {
                    r = var3.next();
                    regionTextures.add(r.getTexture());// 47
                }

                for(int i = 0; i < regionTextures.size(); ++i) {// 50
                    _form.getTextures().remove(regionTextures.get(i));// 52
                    Texture _reconstructed = _refactor(regionTextures.get(i), true);// 53
                    regionTextures.set(i, _reconstructed);// 54
                    _form.getTextures().add(_reconstructed);// 55
                }

                var3 = _form.getRegions().iterator();// 58

                while(var3.hasNext()) {
                    r = var3.next();
                    r.setTexture(regionTextures.remove(0));// 60
                }

            } else {
                Texture img = (Texture)ReflectionHacks.getPrivate(this._materia, AbstractMonster.class, "img");// 68
                if (img != null) {// 69
                    img = _refactor(img, false);// 71
                    tempTextures.add(img);// 72
                    ReflectionHacks.setPrivate(this._materia, AbstractMonster.class, "img", img);// 73
                } else {
                    SumirekoMod.logger.error("Materia has no data: " + this._materia.id);// 79
                }
            }
        } catch (Exception var5) {// 83
            SumirekoMod.logger.error("Failed to reconstruct materia: " + this._materia.id);// 85
        }

        this.isDone = true;// 87
    }// 88

    private static Texture _refactor(Texture t) {
        return _refactor(t, false);// 65
    }

    private static Texture _refactor(Texture t, boolean dispose) {
        try {
            if (!t.getTextureData().isPrepared()) {// 71
                t.getTextureData().prepare();// 72
            }

            Pixmap re = t.getTextureData().consumePixmap();// 73
            int _;
            int __;
            int[][] area;
            int startX;
            int startY;
            int initX;
            int i;
            int initY;
            int b;
            if (MathUtils.randomBoolean()) {// 76
                _ = 0;

                for(__ = MathUtils.random(11, 17); _ < __; ++_) {
                    area = new int[MathUtils.random(t.getWidth() / 8, t.getWidth() / 5)][MathUtils.random(t.getHeight() / 5, t.getHeight() / 3)];// 80
                    startX = MathUtils.random(0, re.getWidth() - area.length);// 82
                    startY = MathUtils.random(0, re.getHeight() - area[0].length);// 83
                    initX = startX;

                    for(i = 0; i < area.length; i = ++initX - startX) {// 84
                        initY = startY;

                        for(b = 0; b < area[0].length; b = ++initY - startY) {// 86
                            area[i][b] = re.getPixel(initX, initY);// 88
                        }
                    }

                    startY += MathUtils.random(t.getHeight() / 9, t.getHeight() / 5) * (MathUtils.randomBoolean() ? 1 : -1);// 92
                    initX = startX;

                    for(i = 0; i < area.length; i = ++initX - startX) {// 94
                        initY = startY;

                        for(b = 0; b < area[0].length; b = ++initY - startY) {// 96
                            if (initY >= 0 && initY <= re.getHeight()) {// 98
                                re.drawPixel(initX, initY, area[i][b]);// 99
                            }
                        }
                    }
                }
            } else {
                _ = 0;

                for(__ = MathUtils.random(11, 17); _ < __; ++_) {
                    area = new int[MathUtils.random(t.getWidth() / 5, t.getWidth() / 3)][MathUtils.random(t.getHeight() / 8, t.getHeight() / 5)];// 108
                    startX = MathUtils.random(0, re.getWidth() - area.length);
                    startY = MathUtils.random(0, re.getHeight() - area[0].length);
                    initX = startX;

                    for(i = 0; i < area.length; i = ++initX - startX) {// 112
                        initY = startY;

                        for(b = 0; b < area[0].length; b = ++initY - startY) {// 114
                            area[i][b] = re.getPixel(initX, initY);// 116
                        }
                    }

                    startX += MathUtils.random(t.getWidth() / 9, t.getWidth() / 5) * (MathUtils.randomBoolean() ? 1 : -1);// 120
                    initX = startX;

                    for(i = 0; i < area.length; i = ++initX - startX) {// 122
                        initY = startY;

                        for(b = 0; b < area[0].length; b = ++initY - startY) {// 124
                            if (initX >= 0 && initX <= re.getWidth()) {// 126
                                re.drawPixel(initX, initY, area[i][b]);// 127
                            }
                        }
                    }
                }
            }

            if (dispose) {// 133
                t.dispose();// 134
            }

            return new Texture(re);// 135
        } catch (Exception var12) {// 137
            return t;// 139
        }
    }
}