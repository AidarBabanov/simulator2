package targetting;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.cards.Hero;
import entities.cards.Minion;
import entities.collections.Table;
import player.Player;

import java.util.LinkedList;
import java.util.List;

public enum TargetType {
    NONE {
        @Override
        public boolean needChoosing() {
            return false;
        }

        @Override
        public boolean canTarget(Player player, Target target) {
            return false;
        }

        @Override
        public boolean hasTargets(Player player) {
            return false;
        }

        @Override
        public List<Target> getTargets(Player player) {
            return new LinkedList<>();
        }
    },

    CHARACTER {
        @Override
        public boolean needChoosing() {
            return true;
        }

        @Override
        public boolean canTarget(Player player, Target target) {
            return target != null && target.isTargetable();
        }

        @Override
        public boolean hasTargets(Player player) {
            boolean result;
            result = player.getHero().isTargetable();
            result = result || player.getOpponent().getHero().isTargetable();
            for (Minion minion : player.getTable()) {
                result = result || minion.isTargetable();
            }
            for (Minion minion : player.getOpponent().getTable()) {
                result = result || minion.isTargetable();
            }
            return result;
        }

        @Override
        public List<Target> getTargets(Player player) {
            LinkedList<Target> list = new LinkedList<>();
//            System.out.println(this.hasTargets(player));
            if (this.hasTargets(player)) {
                Target target = getSingleTarget(player, this);
                if (this.canTarget(player, target)) list.add(target);
            }
            return list;
        }
    },
    MINION {
        @Override
        public boolean needChoosing() {
            return true;
        }

        @Override
        public boolean canTarget(Player player, Target target) {
            return target != null && target instanceof Minion && target.isTargetable();
        }

        @Override
        public boolean hasTargets(Player player) {
            boolean result = false;
            for (Minion minion : player.getTable()) {
                result = result || minion.isTargetable();
            }
            for (Minion minion : player.getOpponent().getTable()) {
                result = result || minion.isTargetable();
            }
            return result;
        }

        @Override
        public List<Target> getTargets(Player player) {
            LinkedList<Target> list = new LinkedList<>();
            if (this.hasTargets(player)) {
                Target target = getSingleTarget(player, this);
                if (this.canTarget(player, target)) list.add(target);
            }
            return list;
        }
    },
    HERO {
        @Override
        public boolean needChoosing() {
            return true;
        }

        @Override
        public boolean canTarget(Player player, Target target) {
            return target instanceof Hero && target.isTargetable();
        }

        @Override
        public boolean hasTargets(Player player) {
            boolean result;
            result = player.getHero().isTargetable();
            result = result || player.getOpponent().getHero().isTargetable();
            return result;
        }

        @Override
        public List<Target> getTargets(Player player) {
            LinkedList<Target> list = new LinkedList<>();
            if (this.hasTargets(player)) {
                Target target = getSingleTarget(player, this);
                if (this.canTarget(player, target)) list.add(target);
            }
            return list;
        }
    },

    ENEMY_CHARACTER {
        @Override
        public boolean needChoosing() {
            return true;
        }

        @Override
        public boolean canTarget(Player player, Target target) {
            if (target == null) return false;
            if (player.getOpponent().getHero().equals(target)) return target.isTargetable();
            for (Minion minion : player.getOpponent().getTable()) {
                if (minion.equals(target)) return target.isTargetable();
            }
            return false;
        }

        @Override
        public boolean hasTargets(Player player) {
            boolean result;
            result = player.getOpponent().getHero().isTargetable();

            for (Minion minion : player.getOpponent().getTable()) {
                result = result || minion.isTargetable();
            }
            return result;
        }

        @Override
        public List<Target> getTargets(Player player) {
            LinkedList<Target> list = new LinkedList<>();
            if (this.hasTargets(player)) {
                Target target = getSingleTarget(player, this);
                if (this.canTarget(player, target)) list.add(target);
            }
            return list;
        }
    },
    ENEMY_MINION {
        @Override
        public boolean needChoosing() {
            return true;
        }

        @Override
        public boolean canTarget(Player player, Target target) {
            if (target == null) return false;
            for (Minion minion : player.getOpponent().getTable()) {
                if (minion.equals(target)) return target.isTargetable();
            }
            return false;
        }

        @Override
        public boolean hasTargets(Player player) {
            boolean result = false;
            for (Minion minion : player.getOpponent().getTable()) {
                result = result || minion.isTargetable();
            }
            return result;
        }

        @Override
        public List<Target> getTargets(Player player) {
            LinkedList<Target> list = new LinkedList<>();
            if (this.hasTargets(player)) {
                Target target = getSingleTarget(player, this);
                if (this.canTarget(player, target)) list.add(target);
            }
            return list;
        }
    },
    ENEMY_HERO {
        @Override
        public boolean needChoosing() {
            return false;
        }

        @Override
        public boolean canTarget(Player player, Target target) {
            if (target == null) return false;
            return player.getOpponent().getHero().equals(target) && player.getOpponent().getHero().isTargetable();
        }

        @Override
        public boolean hasTargets(Player player) {
            return true;
        }

        @Override
        public List<Target> getTargets(Player player) {
            List<Target> targetList = new LinkedList<>();
            targetList.add(player.getOpponent().getHero());
            return targetList;
        }
    },

    YOUR_CHARACTER {
        @Override
        public boolean needChoosing() {
            return true;
        }

        @Override
        public boolean canTarget(Player player, Target target) {
            if (target == null) return false;
            if (player.getHero().equals(target)) return target.isTargetable();
            for (Minion minion : player.getTable()) {
                if (minion.equals(target)) return target.isTargetable();
            }
            return false;
        }

        @Override
        public boolean hasTargets(Player player) {
            boolean result;
            result = player.getHero().isTargetable();

            for (Minion minion : player.getTable()) {
                result = result || minion.isTargetable();
            }
            return result;
        }

        @Override
        public List<Target> getTargets(Player player) {
            LinkedList<Target> list = new LinkedList<>();
            if (this.hasTargets(player)) {
                Target target = getSingleTarget(player, this);
                if (this.canTarget(player, target)) list.add(target);
            }
            return list;
        }
    },
    YOUR_MINION {
        @Override
        public boolean needChoosing() {
            return true;
        }

        @Override
        public boolean canTarget(Player player, Target target) {
            if (target == null) return false;
            for (Minion minion : player.getTable()) {
                if (minion.equals(target)) return target.isTargetable();
            }
            return false;
        }

        @Override
        public boolean hasTargets(Player player) {
            boolean result = false;
            for (Minion minion : player.getTable()) {
                result = result || minion.isTargetable();
            }
            return result;
        }

        @Override
        public List<Target> getTargets(Player player) {
            LinkedList<Target> list = new LinkedList<>();
            if (this.hasTargets(player)) {
                Target target = getSingleTarget(player, this);
                if (this.canTarget(player, target)) list.add(target);
            }
            return list;
        }
    },
    YOUR_HERO {
        @Override
        public boolean needChoosing() {
            return false;
        }

        @Override
        public boolean canTarget(Player player, Target target) {
            if (target == null) return false;
            return player.getHero().equals(target) && player.getHero().isTargetable();
        }

        @Override
        public boolean hasTargets(Player player) {
            return true;
        }

        @Override
        public List<Target> getTargets(Player player) {
            List<Target> targetList = new LinkedList<>();
            targetList.add(player.getHero());
            return targetList;
        }
    },

    ALL_CHARACTERS {
        @Override
        public boolean needChoosing() {
            return false;
        }

        @Override
        public boolean canTarget(Player player, Target target) {
            return target != null && target.isTargetable();

        }

        @Override
        public boolean hasTargets(Player player) {
            return true;
        }

        @Override
        public List<Target> getTargets(Player player) {
            LinkedList<Target> list = new LinkedList<>();
            list.add(player.getHero());
            list.add(player.getOpponent().getHero());
            list.addAll(player.getTable());
            list.addAll(player.getOpponent().getTable());
            return list;
        }
    },
    ALL_MINIONS {
        @Override
        public boolean needChoosing() {
            return false;
        }

        @Override
        public boolean canTarget(Player player, Target target) {
            return target != null && target instanceof Minion && target.isTargetable();
        }

        @Override
        public boolean hasTargets(Player player) {
            return true;
        }

        @Override
        public List<Target> getTargets(Player player) {
            LinkedList<Target> list = new LinkedList<>();
            list.addAll(player.getTable());
            list.addAll(player.getOpponent().getTable());
            return list;
        }
    },

    BOTH_HEROES {
        @Override
        public boolean needChoosing() {
            return false;
        }

        @Override
        public boolean canTarget(Player player, Target target) {
            return target != null && target instanceof Hero && player.getHero().isTargetable();
        }

        @Override
        public boolean hasTargets(Player player) {
            return true;
        }

        @Override
        public List<Target> getTargets(Player player) {
            List<Target> targetList = new LinkedList<>();
            targetList.add(player.getHero());
            targetList.add(player.getOpponent().getHero());
            return targetList;
        }
    },

    ENEMY_CHARACTERS {
        @Override
        public boolean needChoosing() {
            return true;
        }

        @Override
        public boolean canTarget(Player player, Target target) {
            if (target == null) return false;
            if (player.getOpponent().getHero().equals(target)) return target.isTargetable();
            for (Minion minion : player.getOpponent().getTable()) {
                if (minion.equals(target)) return target.isTargetable();
            }
            return false;
        }

        @Override
        public boolean hasTargets(Player player) {
            return true;
        }

        @Override
        public List<Target> getTargets(Player player) {
            LinkedList<Target> list = new LinkedList<>();
            list.add(player.getOpponent().getHero());
            list.addAll(player.getOpponent().getTable());
            return list;
        }
    },
    ENEMY_MINIONS {
        @Override
        public boolean needChoosing() {
            return true;
        }

        @Override
        public boolean canTarget(Player player, Target target) {
            if (target == null) return false;
            for (Minion minion : player.getOpponent().getTable()) {
                if (minion.equals(target)) return target.isTargetable();
            }
            return false;
        }

        @Override
        public boolean hasTargets(Player player) {
            return true;
        }

        @Override
        public List<Target> getTargets(Player player) {
            LinkedList<Target> list = new LinkedList<>();
            list.addAll(player.getOpponent().getTable());
            return list;
        }
    },

    YOUR_CHARACTERS {
        @Override
        public boolean needChoosing() {
            return true;
        }

        @Override
        public boolean canTarget(Player player, Target target) {
            if (target == null) return false;
            if (player.getHero().equals(target)) return target.isTargetable();
            for (Minion minion : player.getTable()) {
                if (minion.equals(target)) return target.isTargetable();
            }
            return false;
        }

        @Override
        public boolean hasTargets(Player player) {
            return true;
        }

        @Override
        public List<Target> getTargets(Player player) {
            LinkedList<Target> list = new LinkedList<>();
            list.add(player.getHero());
            list.addAll(player.getTable());
            return list;
        }
    },
    YOUR_MINIONS {
        @Override
        public boolean needChoosing() {
            return true;
        }

        @Override
        public boolean canTarget(Player player, Target target) {
            if (target == null) return false;
            for (Minion minion : player.getTable()) {
                if (minion.equals(target)) return target.isTargetable();
            }
            return false;
        }

        @Override
        public boolean hasTargets(Player player) {
            return true;
        }

        @Override
        public List<Target> getTargets(Player player) {
            LinkedList<Target> list = new LinkedList<>();
            list.addAll(player.getTable());
            return list;
        }
    };


    public abstract boolean needChoosing();

    public abstract boolean canTarget(Player player, Target target);

    public abstract boolean hasTargets(Player player);

    public abstract List<Target> getTargets(Player player);

    private static Target getSingleTarget(Player player, TargetType targetType) {
        Target target = null;
        while (!targetType.canTarget(player, target)) {
            String notification = "{\"message\": \"choose target\"}";
            player.write(notification);
            String arguments = player.read();
            JsonObject jsonObject = fromStringToJsonOBJ(arguments);
            String targetClass = jsonObject.get("targetType").getAsString();
            if (targetClass.equals("hero")) {
                String heroOwner = jsonObject.get("owner").getAsString();
                if (heroOwner.equals("me")) target = player.getHero();
                if (heroOwner.equals("opponent")) target = player.getOpponent().getHero();
            }
            if (targetClass.equals("minion")) {
                String minionOwner = jsonObject.get("owner").getAsString();
                Table table = null;
                if (minionOwner.equals("me")) table = player.getTable();
                if (minionOwner.equals("opponent")) table = player.getOpponent().getTable();
                int tablePosition = jsonObject.get("tablePosition").getAsInt();
                assert table != null;
                if (tablePosition >= table.size()) {
                    player.write("{\"message\": \"wrong target\"}");
                    target = null;}
                else target = table.get(tablePosition);
            }
        }
        return target;
    }

    private static JsonObject fromStringToJsonOBJ(String message) {
        return new JsonParser().parse(message).getAsJsonObject();
    }

    public static TargetType defineTarget(JsonObject jsonObject) {
        TargetType targetType = null;
        switch (jsonObject.get("target").getAsString()) {
            case ("CHARACTER"):
                targetType = TargetType.CHARACTER;
                break;
            case ("MINION"):
                targetType = TargetType.MINION;
                break;
            case ("HERO"):
                targetType = TargetType.HERO;
                break;
            case ("ENEMY_CHARACTER"):
                targetType = TargetType.ENEMY_CHARACTER;
                break;
            case ("ENEMY_MINION"):
                targetType = TargetType.ENEMY_MINION;
                break;
            case ("ENEMY_HERO"):
                targetType = TargetType.ENEMY_HERO;
                break;
            case ("YOUR_CHARACTER"):
                targetType = TargetType.YOUR_CHARACTER;
                break;
            case ("YOUR_MINION"):
                targetType = TargetType.YOUR_MINION;
                break;
            case ("YOUR_HERO"):
                targetType = TargetType.YOUR_HERO;
                break;
            case ("ALL_CHARACTERS"):
                targetType = TargetType.ALL_CHARACTERS;
                break;
            case ("ALL_MINIONS"):
                targetType = TargetType.ALL_MINIONS;
                break;
            case ("BOTH_HEROES"):
                targetType = TargetType.BOTH_HEROES;
                break;
            case ("ENEMY_CHARACTERS"):
                targetType = TargetType.ENEMY_CHARACTERS;
                break;
            case ("ENEMY_MINIONS"):
                targetType = TargetType.ENEMY_MINIONS;
                break;
            case ("YOUR_CHARACTERS"):
                targetType = TargetType.YOUR_CHARACTERS;
                break;
            case ("YOUR_MINIONS"):
                targetType = TargetType.YOUR_MINIONS;
                break;
        }
        return targetType;
    }
}
