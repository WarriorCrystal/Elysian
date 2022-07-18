package me.frogdog.hecks.command.commands;

import me.frogdog.hecks.Hecks;
import me.frogdog.hecks.command.Command;
import me.frogdog.hecks.friend.Friend;

public final class AddFriend extends Command {

    public AddFriend() {
        super(new String[] {"add"}, "Add (username)");
    }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        String username = args[0];
        Hecks.getInstance().getFriendManager().register(new Friend(username, username));
        Command.sendClientSideMessage("Added " + username + " as a friend");
    }

}
