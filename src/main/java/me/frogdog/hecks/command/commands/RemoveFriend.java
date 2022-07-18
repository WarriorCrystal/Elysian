package me.frogdog.hecks.command.commands;

import me.frogdog.hecks.Hecks;
import me.frogdog.hecks.command.Command;
import me.frogdog.hecks.friend.Friend;

public final class RemoveFriend extends Command {

    public RemoveFriend() {
        super(new String[] {"remove", "unadd"}, "Remove (username)");
    }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        String username = args[0];
        if(!(Hecks.getInstance().getFriendManager().isFriend(username))) {
            Command.sendClientSideMessage(username + " is not on your friends list");
        } else {
            Friend friend = Hecks.getInstance().getFriendManager().getFriendByAliasOrLabel(username);
            Hecks.getInstance().getFriendManager().unregister(friend);
            Command.sendClientSideMessage("Removed " + username + " from your friends list");
        }

    }

}
