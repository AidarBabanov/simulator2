package bot.commands;

import com.google.gson.JsonObject;

public class TablePositionCommand implements Command {
    private int tablePosition;

    public TablePositionCommand(int tablePosition){
        this.setTablePosition(tablePosition);
    }

    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "position");
        jsonObject.addProperty("tablePosition", this.getTablePosition());
        return jsonObject;
    }

    @Override
    public String toString() {
        return this.toJson().toString();
    }

    public int getTablePosition() {
        return tablePosition;
    }

    public void setTablePosition(int tablePosition) {
        this.tablePosition = tablePosition;
    }
}
