package com.elysian.client.command.commands;

import com.elysian.client.Elysian;
import com.elysian.client.command.Command;
import com.elysian.client.friend.Friend;

public final class RemoveFriend extends Command {

    public RemoveFriend() {
        super(new String[] {"remove", "unadd"}, "Remove (username)");
    }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        String username = args[0];
        if(!(Elysian.getInstance().getFriendManager().isFriend(username))) {
            Command.sendClientSideMessage(username + " is not on your friends list");
        } else {
            Friend friend = Elysian.getInstance().getFriendManager().getFriendByAliasOrLabel(username);
            Elysian.getInstance().getFriendManager().unregister(friend);
            Command.sendClientSideMessage("Removed " + username + " from your friends list");
        }

    }

}
