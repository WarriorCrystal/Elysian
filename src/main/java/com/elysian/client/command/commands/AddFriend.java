package com.elysian.client.command.commands;

import com.elysian.client.Elysian;
import com.elysian.client.command.Command;
import com.elysian.client.friend.Friend;

public final class AddFriend extends Command {

    public AddFriend() {
        super(new String[] {"add"}, "Add (username)");
    }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        String username = args[0];
        Elysian.getInstance().getFriendManager().register(new Friend(username, username));
        Command.sendClientSideMessage("Added " + username + " as a friend");
    }

}
