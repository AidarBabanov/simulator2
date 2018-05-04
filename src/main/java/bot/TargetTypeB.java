package bot;

import com.google.gson.JsonObject;

public enum TargetTypeB {
    NONE{
        @Override
        public boolean hitsEnemyHero() {
            return false;
        }

        @Override
        public boolean isAOE() {
            return false;
        }
    },
    CHARACTER {
        @Override
        public boolean hitsEnemyHero() {
            return true;
        }

        @Override
        public boolean isAOE() {
            return false;
        }
    },
    MINION {
        @Override
        public boolean hitsEnemyHero() {
            return false;
        }

        @Override
        public boolean isAOE() {
            return false;
        }
    },
    HERO {
        @Override
        public boolean hitsEnemyHero() {
            return true;
        }

        @Override
        public boolean isAOE() {
            return false;
        }
    },

    ENEMY_CHARACTER {
        @Override
        public boolean hitsEnemyHero() {
            return true;
        }

        @Override
        public boolean isAOE() {
            return false;
        }
    },
    ENEMY_MINION {
        @Override
        public boolean hitsEnemyHero() {
            return false;
        }

        @Override
        public boolean isAOE() {
            return false;
        }
    },
    ENEMY_HERO {
        @Override
        public boolean hitsEnemyHero() {
            return true;
        }

        @Override
        public boolean isAOE() {
            return false;
        }
    },

    YOUR_CHARACTER {
        @Override
        public boolean hitsEnemyHero() {
            return false;
        }

        @Override
        public boolean isAOE() {
            return false;
        }
    },
    YOUR_MINION {
        @Override
        public boolean hitsEnemyHero() {
            return false;
        }

        @Override
        public boolean isAOE() {
            return false;
        }
    },
    YOUR_HERO {
        @Override
        public boolean hitsEnemyHero() {
            return false;
        }

        @Override
        public boolean isAOE() {
            return false;
        }
    },

    ALL_CHARACTERS {
        @Override
        public boolean hitsEnemyHero() {
            return true;
        }

        @Override
        public boolean isAOE() {
            return true;
        }
    },
    ALL_MINIONS {
        @Override
        public boolean hitsEnemyHero() {
            return false;
        }

        @Override
        public boolean isAOE() {
            return true;
        }
    },

    BOTH_HEROES {
        @Override
        public boolean hitsEnemyHero() {
            return true;
        }

        @Override
        public boolean isAOE() {
            return false;
        }
    },

    ENEMY_CHARACTERS {
        @Override
        public boolean hitsEnemyHero() {
            return true;
        }

        @Override
        public boolean isAOE() {
            return true;
        }
    },
    ENEMY_MINIONS {
        @Override
        public boolean hitsEnemyHero() {
            return false;
        }

        @Override
        public boolean isAOE() {
            return true;
        }
    },

    YOUR_CHARACTERS {
        @Override
        public boolean hitsEnemyHero() {
            return false;
        }

        @Override
        public boolean isAOE() {
            return false;
        }
    },
    YOUR_MINIONS {
        @Override
        public boolean hitsEnemyHero() {
            return false;
        }

        @Override
        public boolean isAOE() {
            return false;
        }
    };

    public abstract boolean hitsEnemyHero();
    public abstract boolean isAOE();

    public static TargetTypeB defineTarget(JsonObject jsonEffect) {
        TargetTypeB targetType = null;
        switch (jsonEffect.get("target").getAsString()) {
            case ("CHARACTER"):
                targetType = TargetTypeB.CHARACTER;
                break;
            case ("MINION"):
                targetType = TargetTypeB.MINION;
                break;
            case ("HERO"):
                targetType = TargetTypeB.HERO;
                break;
            case ("ENEMY_CHARACTER"):
                targetType = TargetTypeB.ENEMY_CHARACTER;
                break;
            case ("ENEMY_MINION"):
                targetType = TargetTypeB.ENEMY_MINION;
                break;
            case ("ENEMY_HERO"):
                targetType = TargetTypeB.ENEMY_HERO;
                break;
            case ("YOUR_CHARACTER"):
                targetType = TargetTypeB.YOUR_CHARACTER;
                break;
            case ("YOUR_MINION"):
                targetType = TargetTypeB.YOUR_MINION;
                break;
            case ("YOUR_HERO"):
                targetType = TargetTypeB.YOUR_HERO;
                break;
            case ("ALL_CHARACTERS"):
                targetType = TargetTypeB.ALL_CHARACTERS;
                break;
            case ("ALL_MINIONS"):
                targetType = TargetTypeB.ALL_MINIONS;
                break;
            case ("BOTH_HEROES"):
                targetType = TargetTypeB.BOTH_HEROES;
                break;
            case ("ENEMY_CHARACTERS"):
                targetType = TargetTypeB.ENEMY_CHARACTERS;
                break;
            case ("ENEMY_MINIONS"):
                targetType = TargetTypeB.ENEMY_MINIONS;
                break;
            case ("YOUR_CHARACTERS"):
                targetType = TargetTypeB.YOUR_CHARACTERS;
                break;
            case ("YOUR_MINIONS"):
                targetType = TargetTypeB.YOUR_MINIONS;
                break;
        }
        return targetType;
    }
}
