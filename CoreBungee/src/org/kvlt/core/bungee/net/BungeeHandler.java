package org.kvlt.core.bungee.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.kvlt.core.bungee.CoreBungee;
import org.kvlt.core.packets.NewTestPacket;
import org.kvlt.core.packets.Packet;
import org.kvlt.core.packets.PacketOld;
import org.kvlt.core.packets.proxy.ProxyConnectionPacket;

public class BungeeHandler extends SimpleChannelInboundHandler<Packet> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        CoreBungee.get().setCoreServer(ctx.channel());
//        ctx.channel().writeAndFlush(new ProxyConnectionPacket(CoreBungee.get().getServerName()), ctx.voidPromise());
        ctx.channel().writeAndFlush(new NewTestPacket());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        ConnectionManager.get().setConnected(false);
        ConnectionManager.get().connect();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet packet) {
        packet.execute();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//        ConnectionManager.get().setConnected(false);
//        ConnectionManager.get().connect();
    }

}
