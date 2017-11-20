package org.kvlt.core.packets.proxy;

import org.kvlt.core.bungee.CoreBungee;
import org.kvlt.core.packets.Packet;

public class ProxyPingDataPacket extends Packet {

    private String motd;

    public ProxyPingDataPacket(String motd) {
        this.motd = motd;
    }

    @Override
    protected void onCore() {

    }

    @Override
    protected void onServer() {

    }

    @Override
    protected void onProxy() {
        CoreBungee.get().getControlManager().getPingEventListener().setMotd(getMotd());
    }

    public String getMotd() {
        return motd;
    }

}
