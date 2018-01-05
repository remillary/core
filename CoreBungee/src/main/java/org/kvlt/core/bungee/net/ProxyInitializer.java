package org.kvlt.core.bungee.net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.kvlt.core.bungee.CoreBungee;
import org.kvlt.core.bungee.packets.*;
import org.kvlt.core.protocol.PacketDecoder;
import org.kvlt.core.protocol.PacketEncoder;
import org.kvlt.core.protocol.PacketFramer;
import org.kvlt.core.protocol.PacketResolver;

public class ProxyInitializer extends ChannelInitializer<SocketChannel> {

    private static PacketResolver resolver;

    static {
        resolver = CoreBungee.get().getPacketResolver();

        resolver.registerPacket(new KickPacket());
        resolver.registerPacket(new MotdPacket());
        resolver.registerPacket(new MessagePacket());
        resolver.registerPacket(new AuthPacket());
        resolver.registerPacket(new IdPacket());
        resolver.registerPacket(new BroadcastPacket());
        resolver.registerPacket(new TransferPacket());
    }

    @Override
    public void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(
                new PacketFramer(),
                new PacketEncoder(),
                new PacketDecoder(resolver),
                new BungeeHandler()
        );
    }

}
