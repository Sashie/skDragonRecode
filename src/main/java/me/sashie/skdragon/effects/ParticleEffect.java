package me.sashie.skdragon.effects;

import me.sashie.skdragon.effects.simple.Circle;
import me.sashie.skdragon.effects.simple.Heart;
import me.sashie.skdragon.effects.simple.Sphere;
import me.sashie.skdragon.effects.simple.parametric.*;
import me.sashie.skdragon.effects.special.*;
import me.sashie.skdragon.effects.targets.Arc;
import me.sashie.skdragon.effects.targets.Line;
import me.sashie.skdragon.effects.targets.TargetLightning;

public enum ParticleEffect {

    //** Simple **//
    ASTROID {
        @Override
        public EffectData getEffectData() {
            return new Astroid2D();
        }
    },
    EPICYCLOID {
        @Override
        public EffectData getEffectData() {
            return new Epicycloid2D();
        }
    },
    LISSAJOUS {
        @Override
        public EffectData getEffectData() {
            return new Lissajous3D();
        }
    },
    SPIRAL {
        @Override
        public EffectData getEffectData() {
            return new Spiral3D();
        }
    },
    SPIROGRAPH {
        @Override
        public EffectData getEffectData() {
            return new Spirograph2D();
        }
    },
    CIRCLE {
        @Override
        public EffectData getEffectData() {
            return new Circle();
        }
    },
    HEART {
        @Override
        public EffectData getEffectData() {
            return new Heart();
        }
    },
    SPHERE {
        @Override
        public EffectData getEffectData() {
            return new Sphere();
        }
    },

    //Special
    ATOM {
        @Override
        public EffectData getEffectData() {
            return new Atom();
        }
    },
    BAND {
        @Override
        public EffectData getEffectData() {
            return new Band();
        }
    },
    BLACKHOLE {
        @Override
        public EffectData getEffectData() {
            return new Blackhole();
        }
    },
    LASERS {
        @Override
        public EffectData getEffectData() {
            return new Lasers();
        }
    },
    LIGHTNING {
        @Override
        public EffectData getEffectData() {
            return new Lightning();
        }
    },
    METEOR {
        @Override
        public EffectData getEffectData() {
            return new Meteor();
        }
    },
    RINGS {
        @Override
        public EffectData getEffectData() {
            return new Rings();
        }
    },

	/*MAGIC_PORTAL {
		@Override
		public EffectData getEffectData() {
			return new MagicalPortalEffect();
		}
	},

	TEXT {
		@Override
		public EffectData getEffectData() {
			return new Text();
		}
	},*/

    WINGS {
        @Override
        public EffectData getEffectData() {
            return new Wings();
        }
    },

    // Target Effects
    TARGET_ARC {
        @Override
        public EffectData getEffectData() {
            return new Arc();
        }
    },
    TARGET_LINE {
        @Override
        public EffectData getEffectData() {
            return new Line();
        }
    },
    TARGET_LIGHTNING {
        @Override
        public EffectData getEffectData() {
            return new TargetLightning();
        }
    };

    public abstract EffectData getEffectData();

    public static ParticleEffect getByName(String effectName) {
        try {
            return valueOf(effectName.toUpperCase().replace(" ", "_"));
        } catch (IllegalArgumentException ignore) {
            return null;
        }
    }

    public String getName() {
        return toString().toLowerCase().replace("_", " ");
    }

}
