package org.kvlt.core.bungee.net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.kvlt.core.bungee.CoreBungee;
import org.kvlt.core.bungee.packets.*;
import org.kvlt.core.protocol.*;

public class ProxyInitializer extends ChannelInitializer<SocketChannel> {

    private static PacketResolver resolver;

    static {
        resolver = CoreBungee.get().getPacketResolver();

        PacketIn[] packets = {
                new KickPacket(),
                new MotdPacket(),
                new MessagePacket(),
                new AuthPacket(),
                new IdPacket(),
                new BroadcastPacket(),
                new TransferPacket(),
        };

        resolver.registerPackets(packets);
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
