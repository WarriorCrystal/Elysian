package me.frogdog.hecks.friend;

import com.google.gson.*;
import me.frogdog.hecks.Hecks;
import me.frogdog.hecks.config.Config;
import me.frogdog.hecks.util.Registry;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public final class FriendManager extends Registry<Friend> {

    public FriendManager() {
        this.registry = new ArrayList<Friend>();
        new Config("friends.json"){

            @Override
            public void load(Object... source) {
                JsonElement root;
                try {
                    if (!this.getFile().exists()) {
                        this.getFile().createNewFile();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                if (!this.getFile().exists()) {
                    return;
                }
                try (FileReader reader = new FileReader(this.getFile());){
                    root = new JsonParser().parse((Reader)reader);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                if (!(root instanceof JsonArray)) {
                    return;
                }
                JsonArray friends = (JsonArray)root;
                friends.forEach(node -> {
                    if (!(node instanceof JsonObject)) {
                        return;
                    }
                    try {
                        JsonObject friendNode = (JsonObject)node;
                        Hecks.getInstance().getFriendManager().getRegistry().add(new Friend(friendNode.get("friend-label").getAsString(), friendNode.get("friend-alias").getAsString()));
                    }
                    catch (Throwable e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void save(Object... destination) {
                if (this.getFile().exists()) {
                    this.getFile().delete();
                }
                if (Hecks.getInstance().getFriendManager().getRegistry().isEmpty()) {
                    return;
                }
                JsonArray friends = new JsonArray();
                Hecks.getInstance().getFriendManager().getRegistry().forEach(friend -> {
                    try {
                        @SuppressWarnings("unused")
                        JsonObject friendObject;
                        JsonObject properties = friendObject = new JsonObject();
                        properties.addProperty("friend-label", friend.getLabel());
                        properties.addProperty("friend-alias", friend.getAlias());
                        friends.add((JsonElement)properties);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                try (FileWriter writer = new FileWriter(this.getFile());){
                    writer.write(new GsonBuilder().setPrettyPrinting().create().toJson((JsonElement)friends));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public Friend getFriendByAliasOrLabel(String aliasOrLabel) {
        for (Friend friend : registry) {
            if (!aliasOrLabel.equalsIgnoreCase(friend.getLabel()) && !aliasOrLabel.equalsIgnoreCase(friend.getAlias())) continue;
            return friend;
        }
        return null;
    }

    public boolean isFriend(String aliasOrLabel) {
        for (Friend friend : registry) {
            if (!aliasOrLabel.equalsIgnoreCase(friend.getLabel()) && !aliasOrLabel.equalsIgnoreCase(friend.getAlias())) continue;
            return true;
        }
        return false;
    }
}
